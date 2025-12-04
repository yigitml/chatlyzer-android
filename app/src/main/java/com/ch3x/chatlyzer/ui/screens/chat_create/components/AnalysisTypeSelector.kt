package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateEvent
import com.ch3x.chatlyzer.ui.theme.PrimaryGradientColors
import com.ch3x.chatlyzer.ui.theme.SurfaceLight

@Composable
fun AnalysisTypeSelector(
    analysisType: AnalysisType,
    onEvent: (ChatCreateEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Analysis Type",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AnalysisTypeCard(
                selected = analysisType == AnalysisType.NORMAL,
                icon = Icons.Filled.Assessment,
                label = "Standard",
                modifier = Modifier.weight(1f),
                onClick = { onEvent(ChatCreateEvent.ChangeAnalysisType(AnalysisType.NORMAL)) }
            )
            AnalysisTypeCard(
                selected = analysisType == AnalysisType.PRIVACY,
                icon = Icons.Filled.Lock,
                label = "Privacy",
                modifier = Modifier.weight(1f),
                onClick = { onEvent(ChatCreateEvent.ChangeAnalysisType(AnalysisType.PRIVACY)) }
            )
            AnalysisTypeCard(
                selected = analysisType == AnalysisType.GHOST,
                icon = Icons.Filled.VisibilityOff,
                label = "Ghost",
                modifier = Modifier.weight(1f),
                onClick = { onEvent(ChatCreateEvent.ChangeAnalysisType(AnalysisType.GHOST)) }
            )
        }

        // Integrated description with a cleaner look
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = when (analysisType) {
                        AnalysisType.NORMAL -> "Standard Analysis"
                        AnalysisType.PRIVACY -> "Privacy Focused"
                        AnalysisType.GHOST -> "Ghost Mode"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (analysisType) {
                        AnalysisType.NORMAL -> "Full analysis with all insights. Data is stored securely."
                        AnalysisType.PRIVACY -> "Messages are analyzed but NOT stored. Only results are saved."
                        AnalysisType.GHOST -> "Nothing is saved. Results are shown once and then vanish forever."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AnalysisTypeCard(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val containerColor = if (selected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val iconColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "iconColor"
    )

    Box(
        modifier = modifier
            .height(100.dp) // Fixed height for consistency
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .border(
                width = if (selected) 2.dp else 1.dp,
                brush = if (selected) Brush.linearGradient(PrimaryGradientColors) else SolidColor(MaterialTheme.colorScheme.outlineVariant),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = iconColor,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}