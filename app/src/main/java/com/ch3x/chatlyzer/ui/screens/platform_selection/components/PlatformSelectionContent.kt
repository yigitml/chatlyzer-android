package com.ch3x.chatlyzer.ui.screens.platform_selection.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.tutorial.components.Platform

@Composable
fun PlatformSelectionContent(
    onNavigateToTutorial: (Platform) -> Unit,
    modifier: Modifier = Modifier
) {
    val platforms = PlatformData.getPlatforms()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        PlatformSelectionHeader()

        Spacer(modifier = Modifier.height(64.dp))

        PlatformButtonsList(
            platforms = platforms,
            onNavigateToTutorial = onNavigateToTutorial
        )

        Spacer(modifier = Modifier.weight(1f))

        FeatureCard()
    }
} 