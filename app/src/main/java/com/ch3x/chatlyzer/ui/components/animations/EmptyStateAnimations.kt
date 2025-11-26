package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Animated empty state with floating icon
 */
@Composable
fun AnimatedEmptyState(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    emoji: String = "📭",
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val infiniteTransition = rememberInfiniteTransition(label = "empty_state")
    
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_offset"
    )
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Floating emoji
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer {
                    translationY = offsetY
                    rotationZ = rotation
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 80.sp
            )
        }
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Animated dashed circle for empty states
 */
@Composable
fun AnimatedDashedCircle(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
    size: androidx.compose.ui.unit.Dp = 150.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dashed_circle")
    
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "dash_phase"
    )

    Canvas(modifier = modifier.size(size)) {
        val radius = size.toPx() / 2
        
        drawCircle(
            color = color,
            radius = radius,
            style = Stroke(
                width = 3.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(20f, 20f),
                    phase = phase
                )
            )
        )
    }
}

/**
 * Loading dots animation
 */
@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    color: Color = com.ch3x.chatlyzer.ui.theme.PrimaryPink,
    dotCount: Int = 3
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading_dots")
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600,
                        delayMillis = index * 200,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot_$index"
            )
            
            Canvas(modifier = Modifier.size(12.dp)) {
                drawCircle(
                    color = color,
                    radius = 6.dp.toPx() * scale
                )
            }
        }
    }
}

/**
 * Shimmer card with subtle pulse
 */
@Composable
fun PulsingShimmerCard(
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 100.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_pulse")
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_pulse"
    )
    
    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = modifier.height(height),
        glassOpacity = alpha * 0.2f
    ) {
        // Empty content
    }
}
