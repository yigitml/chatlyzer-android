package com.ch3x.chatlyzer

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.LiveFolders.INTENT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ch3x.chatlyzer.data.repository.AppPreferencesRepositoryImpl
import com.ch3x.chatlyzer.data.repository.SharedDataRepository
import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.ui.navigation.AppNavigation
import com.ch3x.chatlyzer.ui.navigation.Screen
import com.ch3x.chatlyzer.ui.screens.chats.ChatsScreen
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedDataRepository: SharedDataRepository

    @Inject
    lateinit var preferencesRepositoryImpl: AppPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleShareIntent(intent)

        enableEdgeToEdge()
        setContent {
            ChatlyzerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val launchCount = preferencesRepositoryImpl.getAppLaunchCount()
                        .collectAsStateWithLifecycle(initialValue = 0)

                    val onBoardingCompleted = preferencesRepositoryImpl.isOnboardingCompleted()
                        .collectAsStateWithLifecycle(initialValue = false)

                    LaunchedEffect(Unit) {
                        preferencesRepositoryImpl.incrementAppLaunchCount()
                    }

                    // Determine start destination based on app state
                    val startDestination = when {
                        sharedDataRepository.hasPendingSharedFile() -> Screen.ChatCreate.route
                        launchCount.value == 0 -> Screen.Landing.route
                        !onBoardingCompleted.value -> Screen.PlatformSelection.route
                        else -> Screen.Chats.route
                    }

                    AppNavigation(
                        context = this@MainActivity,
                        startDestination = startDestination,
                        launchCount = launchCount.value,
                        onBoardingCompleted = onBoardingCompleted.value
                    )
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
        if (sharedDataRepository.hasPendingSharedFile()) {
            // You could use a callback or event bus to notify the navigation
            // For now, we'll handle this in the ViewModel
        }
    }

    private fun handleShareIntent(intent: Intent?) {
        if (intent == null) return

        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            val fileUri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri
            val type = intent.type

            if (fileUri != null) {
                sharedDataRepository.setSharedFile(fileUri.toString(), type ?: "")
            }
        }
    }
}