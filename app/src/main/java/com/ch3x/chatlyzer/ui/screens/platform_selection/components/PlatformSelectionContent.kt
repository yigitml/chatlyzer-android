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

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement

@Composable
fun PlatformSelectionContent(
    onNavigateToTutorial: (Platform) -> Unit,
    modifier: Modifier = Modifier
) {
    val platforms = PlatformData.getPlatforms()

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val screenHeight = maxHeight
        val isCompact = screenHeight < 600.dp
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isCompact) Arrangement.Top else Arrangement.SpaceBetween
        ) {
            val topSpacerHeight = if (isCompact) 24.dp else 48.dp
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(topSpacerHeight))
                PlatformSelectionHeader()
                Spacer(modifier = Modifier.height(if (isCompact) 32.dp else 64.dp))
            }

            PlatformButtonsList(
                platforms = platforms,
                onNavigateToTutorial = onNavigateToTutorial
            )

            if (!isCompact) {
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Spacer(modifier = Modifier.height(32.dp))
            }

            FeatureCard()
        }
    }
} 