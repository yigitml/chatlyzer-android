package com.ch3x.chatlyzer.ui.screens.platform_selection.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform

@Composable
fun PlatformButtonsList(
    platforms: List<PlatformData>,
    onNavigateToTutorial: (Platform) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        platforms.forEach { platformData ->
            PlatformButtonItem(
                platformData = platformData,
                onNavigateToTutorial = onNavigateToTutorial
            )
        }
    }
} 