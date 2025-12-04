package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressStatCard(
    title: String,
    value: Float,
    maxValue: Float = 10f,
    subtitle: String = "",
    icon: String,
    progressColor: Color = com.ch3x.chatlyzer.ui.theme.GoldenYellow,
    isPercentage: Boolean = false
) {
    var isVisible by remember { mutableStateOf(false) }
    
    val targetProgress = if (isPercentage) {
        (value / 100f).coerceIn(0f, 1f)
    } else {
        (value / maxValue).coerceIn(0f, 1f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isVisible) targetProgress else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "ProgressAnimation"
    )

    LaunchedEffect(Unit) {
        isVisible = true
    }

    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisLayoutDirectives.CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(
                text = icon,
                fontSize = 32.sp
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            
            val displayValue = if (isPercentage) {
                "%.1f%%".format(value)
            } else {
                "%.1f/%.0f".format(value, maxValue)
            }
            
            Text(
                text = displayValue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = com.ch3x.chatlyzer.ui.theme.TextGray
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Custom Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(CircleShape)
                    .background(progressColor.copy(alpha = 0.15f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    progressColor.copy(alpha = 0.7f),
                                    progressColor
                                )
                            )
                        )
                )
            }
        }
    }
} 