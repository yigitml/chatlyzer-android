package com.ch3x.chatlyzer.ui.screens.platform_selection.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.PlatformButton
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform

@Composable
fun PlatformButtonItem(
    platformData: PlatformData,
    onNavigateToTutorial: (Platform) -> Unit,
    modifier: Modifier = Modifier
) {
    PlatformButton(
        modifier = modifier.height(64.dp),
        name = platformData.name,
        iconContent = { platformData.IconContent() },
        backgroundColor = platformData.backgroundGradient,
        onClick = { onNavigateToTutorial(platformData.platform) }
    )
} 