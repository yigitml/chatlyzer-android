package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Animated gradient that slowly shifts colors
 */
@Composable
fun AnimatedGradientBackground(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    durationMillis: Int = 8000,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_offset"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(offset * 1000f, offset * 1000f),
                    end = Offset((1f - offset) * 1000f, (1f - offset) * 1000f)
                )
            )
    ) {
        content()
    }
}

/**
 * Animated vertical gradient
 */
@Composable
fun AnimatedVerticalGradient(
    modifier: Modifier = Modifier,
    topColor: Color,
    bottomColor: Color,
    animateIntensity: Boolean = true,
    durationMillis: Int = 5000,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "vertical_gradient")
    
    val colorShift by infiniteTransition.animateFloat(
        initialValue = if (animateIntensity) 0.9f else 1f,
        targetValue = if (animateIntensity) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color_shift"
    )

    val adjustedTopColor = if (animateIntensity) {
        topColor.copy(alpha = topColor.alpha * colorShift)
    } else {
        topColor
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(adjustedTopColor, bottomColor)
                )
            )
    ) {
        content()
    }
}
