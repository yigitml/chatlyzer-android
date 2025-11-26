package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ch3x.chatlyzer.ui.components.GradientButton

@Composable
fun GetStartedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GradientButton(
        text = "Analyze My Chats",
        onClick = onClick,
        modifier = modifier
    )
} 