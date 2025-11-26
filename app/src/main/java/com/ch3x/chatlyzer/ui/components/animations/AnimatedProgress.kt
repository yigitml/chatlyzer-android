package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Animated progress bar that smoothly animates to target value
 */
@Composable
fun AnimatedProgressBar(
    targetProgress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    trackColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
    height: Dp = 8.dp,
    cornerRadius: Dp = 4.dp,
    durationMillis: Int = 800,
    delayMillis: Int = 0
) {
    var currentProgress by remember { mutableStateOf(0f) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(targetProgress) {
        if (!hasAnimated) {
            kotlinx.coroutines.delay(delayMillis.toLong())
            hasAnimated = true
            
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durationMillis
            
            while (System.currentTimeMillis() < endTime) {
                val progress = (System.currentTimeMillis() - startTime).toFloat() / durationMillis
                val easedProgress = FastOutSlowInEasing.transform(progress)
                currentProgress = targetProgress * easedProgress
                kotlinx.coroutines.delay(16)
            }
            currentProgress = targetProgress
        }
    }

    LinearProgressIndicator(
        progress = { currentProgress.coerceIn(0f, 1f) },
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(cornerRadius)),
        color = color,
        trackColor = trackColor,
    )
}

/**
 * Gradient animated progress bar
 */
@Composable
fun AnimatedGradientProgressBar(
    targetProgress: Float,
    modifier: Modifier = Modifier,
    gradientColors: List<Color>,
    trackColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
    height: Dp = 8.dp,
    cornerRadius: Dp = 4.dp,
    durationMillis: Int = 800,
    delayMillis: Int = 0
) {
    var currentProgress by remember { mutableStateOf(0f) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(targetProgress) {
        if (!hasAnimated) {
            kotlinx.coroutines.delay(delayMillis.toLong())
            hasAnimated = true
            
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durationMillis
            
            while (System.currentTimeMillis() < endTime) {
                val progress = (System.currentTimeMillis() - startTime).toFloat() / durationMillis
                val easedProgress = FastOutSlowInEasing.transform(progress)
                currentProgress = targetProgress * easedProgress
                kotlinx.coroutines.delay(16)
            }
            currentProgress = targetProgress
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
    ) {
        // Track
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = trackColor)
        }
        
        // Progress
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(currentProgress.coerceIn(0f, 1f))
        ) {
            drawRect(
                brush = Brush.horizontalGradient(gradientColors)
            )
        }
    }
}

/**
 * Circular progress indicator with animation
 */
@Composable
fun AnimatedCircularProgress(
    targetProgress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    strokeWidth: Dp = 8.dp,
    size: Dp = 100.dp,
    durationMillis: Int = 1000,
    delayMillis: Int = 0
) {
    var currentProgress by remember { mutableStateOf(0f) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(targetProgress) {
        if (!hasAnimated) {
            kotlinx.coroutines.delay(delayMillis.toLong())
            hasAnimated = true
            
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durationMillis
            
            while (System.currentTimeMillis() < endTime) {
                val progress = (System.currentTimeMillis() - startTime).toFloat() / durationMillis
                val easedProgress = FastOutSlowInEasing.transform(progress)
                currentProgress = targetProgress * easedProgress
                kotlinx.coroutines.delay(16)
            }
            currentProgress = targetProgress
        }
    }

    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val radius = (size.toPx() - strokeWidthPx) / 2
        
        // Background circle
        drawCircle(
            color = color.copy(alpha = 0.1f),
            radius = radius,
            style = Stroke(width = strokeWidthPx)
        )
        
        // Progress arc
        val sweepAngle = 360f * currentProgress.coerceIn(0f, 1f)
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )
    }
}
