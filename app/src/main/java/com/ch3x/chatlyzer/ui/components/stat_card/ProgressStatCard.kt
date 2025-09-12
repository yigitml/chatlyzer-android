package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    progressColor: Color = MaterialTheme.colorScheme.primary,
    isPercentage: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
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
                fontWeight = FontWeight.Bold
            )
            
            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val progress = if (isPercentage) {
                (value / 100f).coerceIn(0f, 1f)
            } else {
                (value / maxValue).coerceIn(0f, 1f)
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                trackColor = progressColor.copy(alpha = 0.2f)
            )
        }
    }
} 