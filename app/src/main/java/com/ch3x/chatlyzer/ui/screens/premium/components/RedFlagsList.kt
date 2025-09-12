package com.ch3x.chatlyzer.ui.screens.premium.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.RedFlagItem
import com.ch3x.chatlyzer.ui.theme.Pink

@Composable
fun RedFlagsList(modifier: Modifier = Modifier) {
    val redFlags = listOf(
        RedFlagData(
            emoji = "🍆",
            text = "Only invites you to his place",
            backgroundColor = MaterialTheme.colorScheme.surface
        ),
        RedFlagData(
            emoji = "👸",
            text = "Only complimented you 12 times (every time it was about your body)",
            backgroundColor = Pink.copy(alpha = 0.2f)
        ),
        RedFlagData(
            emoji = "💤",
            text = "Only texted you after midnight",
            backgroundColor = Pink.copy(alpha = 0.2f)
        )
    )

    redFlags.forEachIndexed { index, redFlag ->
        RedFlagItem(
            emoji = redFlag.emoji,
            text = redFlag.text,
            backgroundColor = redFlag.backgroundColor
        )
        
        if (index < redFlags.size - 1) {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

private data class RedFlagData(
    val emoji: String,
    val text: String,
    val backgroundColor: androidx.compose.ui.graphics.Color
) 