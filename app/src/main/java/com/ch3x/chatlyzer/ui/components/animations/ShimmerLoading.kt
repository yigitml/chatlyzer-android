package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Beautiful shimmer loading effect
 */
@Composable
fun ShimmerLoading(
    modifier: Modifier = Modifier,
    height: Dp = 20.dp,
    cornerRadius: Dp = 8.dp
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f),
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.15f),
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f)
    )

    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(translateAnim - 200f, 0f),
                    end = Offset(translateAnim, 0f)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
    )
}

/**
 * Shimmer for card-like components
 */
@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    height: Dp = 100.dp
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShimmerLoading(height = 24.dp, cornerRadius = 12.dp)
        ShimmerLoading(height = 16.dp, cornerRadius = 8.dp, modifier = Modifier.fillMaxWidth(0.7f))
        ShimmerLoading(height = 16.dp, cornerRadius = 8.dp, modifier = Modifier.fillMaxWidth(0.5f))
    }
}

/**
 * Shimmer for list items
 */
@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        ShimmerLoading(
            modifier = Modifier.size(48.dp),
            height = 48.dp,
            cornerRadius = 24.dp
        )
        
        // Content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ShimmerLoading(height = 16.dp, modifier = Modifier.fillMaxWidth(0.6f))
            ShimmerLoading(height = 14.dp, modifier = Modifier.fillMaxWidth(0.4f))
        }
    }
}
