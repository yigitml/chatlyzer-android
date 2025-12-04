package com.ch3x.chatlyzer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.ch3x.chatlyzer.data.repository.SharedDataRepository
import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.ui.navigation.AppNavigation
import com.ch3x.chatlyzer.ui.navigation.Screen
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedDataRepository: SharedDataRepository

    @Inject
    lateinit var preferencesRepositoryImpl: AppPreferencesRepository

    @Inject
    lateinit var sessionManager: com.ch3x.chatlyzer.domain.session.SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleShareIntent(intent)

        enableEdgeToEdge()
        setContent {
            ChatlyzerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = androidx.navigation.compose.rememberNavController()

                    LaunchedEffect(Unit) {
                        sessionManager.logoutEvent.collect {
                            navController.navigate(Screen.SignIn.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }

                    // Determine start destination once to avoid race conditions
                    val startDestinationState = produceState<String?>(initialValue = null) {
                        val launchCount = preferencesRepositoryImpl.getAppLaunchCount().first()
                        val onBoardingCompleted = preferencesRepositoryImpl.isOnboardingCompleted().first()
                        val hasPendingSharedFile = sharedDataRepository.hasPendingSharedFile()
                        val isUserLoggedIn = preferencesRepositoryImpl.isUserLoggedIn().first()

                        value = when {
                            !isUserLoggedIn -> Screen.SignIn.route // Force sign-in if not logged in
                            hasPendingSharedFile -> Screen.ChatCreate.route
                            launchCount == 0 -> Screen.Introduction.route // Or Landing, depending on preference
                            // !onBoardingCompleted -> Screen.PlatformSelection.route // Removed as per plan
                            else -> Screen.Chats.route
                        }

                        // Increment launch count after determining destination
                        preferencesRepositoryImpl.incrementAppLaunchCount()
                    }

                    if (startDestinationState.value != null) {
                        val launchCount = preferencesRepositoryImpl.getAppLaunchCount()
                            .collectAsStateWithLifecycle(initialValue = 0)

                        val onBoardingCompleted = preferencesRepositoryImpl.isOnboardingCompleted()
                            .collectAsStateWithLifecycle(initialValue = false)

                        AppNavigation(
                            context = this@MainActivity,
                            navController = navController,
                            startDestination = startDestinationState.value!!,
                            launchCount = launchCount.value,
                            onBoardingCompleted = onBoardingCompleted.value,
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleShareIntent(intent)
        // TODO
        // If we get a new share intent while app is running,
        // you might want to navigate to ChatCreate screen
        lifecycleScope.launch {
            if (sharedDataRepository.hasPendingSharedFile()) {
                // You could use a callback or event bus to notify the navigation
                // For now, we'll handle this in the ViewModel
            }
        }
    }

    private fun handleShareIntent(intent: Intent?) {
        if (intent == null) return

        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            val fileUri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri
            val type = intent.type

            if (fileUri != null) {
                lifecycleScope.launch {
                    sharedDataRepository.setSharedFile(fileUri.toString(), type ?: "")
                }
            }
        }
    }
}