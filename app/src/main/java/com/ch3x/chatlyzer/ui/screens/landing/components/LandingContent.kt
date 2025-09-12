package com.ch3x.chatlyzer.ui.screens.landing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LandingContent(
    onNavigateToIntroduction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        LandingLogo()

        Spacer(modifier = Modifier.height(48.dp))

        LandingHeader()

        Spacer(modifier = Modifier.weight(1f))

        LandingActions(onNavigateToIntroduction = onNavigateToIntroduction)

        Spacer(modifier = Modifier.height(16.dp))
    }
} 