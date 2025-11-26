package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.ch3x.chatlyzer.ui.theme.PrimaryGradientColors
import com.ch3x.chatlyzer.ui.components.animations.LoadingDots

@Composable
fun CreateChatFab(
    enabled: Boolean,
    isCreating: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .shadow(8.dp, CircleShape)
            .clip(CircleShape)
            .background(
                brush = if (enabled) Brush.linearGradient(PrimaryGradientColors) else Brush.linearGradient(
                    listOf(Color.Gray, Color.Gray)
                )
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isCreating) {
            LoadingDots(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            Icon(
                Icons.Default.Send,
                contentDescription = "Create Chat",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
} 