package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.components.animations.AnimatedGradientProgressBar
import com.ch3x.chatlyzer.ui.components.animations.CountUpFloatText

@Composable
fun ScoreCard(
    modifier: Modifier = Modifier,
    title: String,
    score: Float,
    maxScore: Float,
    description: String,
    isPercentage: Boolean = false
) {
    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (isPercentage) {
                    CountUpFloatText(
                        targetValue = score,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = com.ch3x.chatlyzer.ui.theme.GoldenYellow,
                        suffix = "%",
                        decimalPlaces = 0
                    )
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        CountUpFloatText(
                            targetValue = score,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = com.ch3x.chatlyzer.ui.theme.GoldenYellow,
                            decimalPlaces = 1
                        )
                        Text(
                            text = "/${maxScore.toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = com.ch3x.chatlyzer.ui.theme.GoldenYellow
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            AnimatedGradientProgressBar(
                targetProgress = score / maxScore,
                modifier = Modifier.fillMaxWidth(),
                gradientColors = listOf(
                    com.ch3x.chatlyzer.ui.theme.GoldenYellow,
                    com.ch3x.chatlyzer.ui.theme.WarmAmber
                ),
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                height = 8.dp,
                cornerRadius = 4.dp,
                durationMillis = 1000,
                delayMillis = 200
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: String
) {
    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = icon,
                fontSize = 32.sp
            )
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = com.ch3x.chatlyzer.ui.theme.GoldenYellow,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = com.ch3x.chatlyzer.ui.theme.TextGray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}