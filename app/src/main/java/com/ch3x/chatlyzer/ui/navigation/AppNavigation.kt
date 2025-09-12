package com.ch3x.chatlyzer.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ch3x.chatlyzer.ui.components.Header
import com.ch3x.chatlyzer.ui.screens.analysis_detail.AnalysisDetailScreen
import com.ch3x.chatlyzer.ui.screens.analyzes.AnalyzesScreen
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateScreen
import com.ch3x.chatlyzer.ui.screens.chats.ChatsScreen
import com.ch3x.chatlyzer.ui.screens.introduction.IntroductionScreen
import com.ch3x.chatlyzer.ui.screens.landing.LandingScreen
import com.ch3x.chatlyzer.ui.screens.platform_selection.PlatformSelectionScreen
import com.ch3x.chatlyzer.ui.screens.premium.PremiumScreen
import com.ch3x.chatlyzer.ui.screens.sign_in.SignInScreen
import com.ch3x.chatlyzer.ui.screens.tutorial.TutorialScreen
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform
import com.ch3x.chatlyzer.ui.screens.tutorial.components.PlatformType

sealed class Screen(val route: String) {

    object Landing : Screen("landing")
    object Introduction : Screen("introduction")

    object SignIn : Screen("signIn")

    object PlatformSelection : Screen("platform_selection")
    object Tutorial : Screen("tutorial/{platform}") {
        fun createRoute(platform: Platform) = "tutorial/${platform.name}"
    }

    object Premium : Screen("premium")

    object Chats : Screen("chats")
    object ChatCreate : Screen("chat_create")

    object Analyzes : Screen("analyzes/{chatId}") {
        fun createRoute(chatId: String) = "analyzes/${chatId}"
    }
    object AnalysisDetail : Screen("analysis_detail/{id}") {
        fun createRoute(id: String) = "analysis_detail/${id}"
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.SignIn.route,
    launchCount: Int = 0,
    onBoardingCompleted: Boolean = false
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Simplified back button logic - show back button except for main entry points
    val entryPointRoutes = setOf(
        Screen.Landing.route,
        Screen.Introduction.route,
        Screen.SignIn.route,
        Screen.Chats.route
    )

    val showBackButton = currentRoute !in entryPointRoutes && navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            Column {
                Spacer(Modifier.padding(top = 32.dp))

                Header(
                    showBackButton = showBackButton,
                    onBackClick = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    }
                )

                //Divider()
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(contentPadding)
        ) {

            composable(Screen.Landing.route) {
                LandingScreen {
                    navController.navigate(Screen.Introduction.route)
                }
            }

            composable(Screen.Introduction.route) {
                IntroductionScreen {
                    navController.navigate(Screen.SignIn.route)
                }
            }

            composable(Screen.SignIn.route) {
                SignInScreen(context = context) {
                    val destination = if (onBoardingCompleted) Screen.Chats.route else Screen.PlatformSelection.route
                    navController.navigate(destination) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            }

            composable(Screen.PlatformSelection.route) {
                PlatformSelectionScreen { platform ->
                    navController.navigate(Screen.Tutorial.createRoute(platform))
                }
            }

            composable(
                Screen.Tutorial.route, arguments = listOf(
                    navArgument(
                        "platform",
                        builder = { type = PlatformType }
                    ))) { backStackEntry ->
                val platformString = backStackEntry.arguments?.getString("platform")
                val platform = try {
                    Platform.valueOf(platformString ?: "")
                } catch (e: IllegalArgumentException) {
                    // Handle invalid platform - maybe navigate back or use default
                    Platform.WHATSAPP // or whatever your default is
                }
                TutorialScreen(
                    onNavigateAnalysis = {
                        navController.navigate(Screen.Chats.route)
                    },
                    onNavigatePlatformSelection = {
                        navController.popBackStack()
                    },
                    selectedPlatform = platform
                )
            }

            composable(Screen.Premium.route) {
                PremiumScreen()
            }

            composable(Screen.Chats.route) {
                ChatsScreen(
                    onNavigateAnalysis = {
                        navController.navigate(Screen.Analyzes.createRoute(it))
                    },
                    onCreateChat = {
                        navController.navigate(Screen.ChatCreate.route)
                    }
                )
            }

            composable(
                Screen.ChatCreate.route
            ) {
                ChatCreateScreen {
                    navController.navigate(Screen.Analyzes.createRoute(it)) {
                        popUpTo(Screen.Chats.route) { inclusive = false }
                    }
                }
            }

            composable(
                Screen.Analyzes.route, arguments = listOf(
                    navArgument(
                        "chatId",
                        builder = { type = NavType.StringType }
                    ))) { backStackEntry ->
                val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
                AnalyzesScreen(chatId, onNavigateToAnalysisDetail = {
                    navController.navigate(Screen.AnalysisDetail.createRoute(it))
                })
            }

            composable(
                Screen.AnalysisDetail.route, arguments = listOf(
                    navArgument(
                        "id",
                        builder = { type = NavType.StringType }
                    ))) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                AnalysisDetailScreen(id)
            }
        }
    }
}