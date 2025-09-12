package com.ch3x.chatlyzer.ui.screens.platform_selection.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun PlatformSelectionHeader(modifier: Modifier = Modifier) {
    Text(
        text = "Choose a platform to analyze your messages",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
} 