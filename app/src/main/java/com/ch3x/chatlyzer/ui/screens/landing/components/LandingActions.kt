package com.ch3x.chatlyzer.ui.screens.landing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.GradientButton

@Composable
fun LandingActions(
    onNavigateToIntroduction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GradientButton(
            text = "Analyze My Chats",
            onClick = onNavigateToIntroduction
        )

        Spacer(modifier = Modifier.height(16.dp))

        LegalText()
    }
} 