package com.ch3x.chatlyzer.ui.screens.landing

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ch3x.chatlyzer.ui.screens.landing.components.LandingContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun LandingScreen(onNavigateToIntroduction: () -> Unit) {
    LandingContent(onNavigateToIntroduction = onNavigateToIntroduction)
}

@Preview
@Composable
private fun PreviewLandingScreen() {
    ChatlyzerTheme {
        LandingScreen {}
    }
}