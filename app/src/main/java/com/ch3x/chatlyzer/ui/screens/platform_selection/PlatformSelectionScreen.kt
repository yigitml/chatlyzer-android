package com.ch3x.chatlyzer.ui.screens.platform_selection

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ch3x.chatlyzer.ui.screens.platform_selection.components.PlatformSelectionContent
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun PlatformSelectionScreen(onNavigateToTutorial: (Platform) -> Unit) {
    PlatformSelectionContent(onNavigateToTutorial = onNavigateToTutorial)
}

@Preview
@Composable
private fun PreviewPlatformSelectionScreen() {
    ChatlyzerTheme {
        PlatformSelectionScreen(onNavigateToTutorial = { platform ->
            // Handle platform navigation here
        })
    }
}