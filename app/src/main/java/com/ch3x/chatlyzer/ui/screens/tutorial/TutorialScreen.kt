package com.ch3x.chatlyzer.ui.screens.tutorial

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform
import com.ch3x.chatlyzer.ui.screens.tutorial.components.PlatformType
import com.ch3x.chatlyzer.ui.screens.tutorial.components.TutorialContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun TutorialScreen(
    onNavigateAnalysis: () -> Unit,
    onNavigatePlatformSelection: () -> Unit,
    selectedPlatform: Platform
) {
    TutorialContent(
        selectedPlatform = selectedPlatform,
        onNavigateAnalysis = onNavigateAnalysis,
        onNavigatePlatformSelection = onNavigatePlatformSelection
    )
}

@Preview
@Composable
private fun PreviewTutorialScreen() {
    ChatlyzerTheme {
        TutorialScreen(
            onNavigateAnalysis = {},
            onNavigatePlatformSelection = {},
            selectedPlatform = Platform.WHATSAPP
        )
    }
}