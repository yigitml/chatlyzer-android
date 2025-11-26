package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlinx.coroutines.launch

/**
 * Success checkmark with animated draw and scale
 */
@Composable
fun SuccessCheckmark(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 80.dp,
    color: Color = com.ch3x.chatlyzer.ui.theme.SuccessGreen,
    onAnimationEnd: () -> Unit = {}
) {
    var progress by remember { mutableStateOf(0f) }
    var shouldScale by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Draw animation
        val startTime = System.currentTimeMillis()
        val drawDuration = 600L
        
        while (progress < 1f) {
            val elapsed = System.currentTimeMillis() - startTime
            progress = (elapsed.toFloat() / drawDuration).coerceIn(0f, 1f)
            kotlinx.coroutines.delay(16)
        }
        
        // Scale pop
        shouldScale = true
        kotlinx.coroutines.delay(300)
        shouldScale = false
        onAnimationEnd()
    }

    val scale by animateFloatAsState(
        targetValue = if (shouldScale) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "checkmark_scale"
    )

    Box(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val checkPath = Path().apply {
                val w = size.toPx()
                val h = size.toPx()
                
                // Left part of check
                moveTo(w * 0.2f, h * 0.5f)
                lineTo(w * 0.4f, h * 0.7f)
                
                // Right part of check
                lineTo(w * 0.8f, h * 0.3f)
            }
            
            // Draw with progress
            drawPath(
                path = checkPath,
                color = color,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 8.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        intervals = floatArrayOf(1000f, 1000f),
                        phase = 1000f * (1f - progress)
                    )
                )
            )
        }
    }
}

/**
 * Confetti particle data class
 */
private data class Confetti(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val color: Color,
    var rotation: Float,
    var rotationSpeed: Float
)

/**
 * Celebration confetti effect
 */
@Composable
fun CelebrationConfetti(
    modifier: Modifier = Modifier,
    particleCount: Int = 50,
    durationMillis: Long = 3000,
    onComplete: () -> Unit = {}
) {
    val confettiColors = listOf(
        com.ch3x.chatlyzer.ui.theme.PrimaryPink,
        com.ch3x.chatlyzer.ui.theme.PrimaryPurple,
        com.ch3x.chatlyzer.ui.theme.SecondaryOrange,
        com.ch3x.chatlyzer.ui.theme.SuccessGreen,
        MaterialTheme.colorScheme.onBackground
    )
    
    val particles = remember {
        List(particleCount) { i ->
            val angle = Random.nextFloat() * 360f
            val speed = Random.nextFloat() * 5f + 2f
            Confetti(
                x = Random.nextFloat(),
                y = 0f,
                vx = cos(Math.toRadians(angle.toDouble())).toFloat() * speed,
                vy = sin(Math.toRadians(angle.toDouble())).toFloat() * speed - 10f,
                color = confettiColors[i % confettiColors.size],
                rotation = Random.nextFloat() * 360f,
                rotationSpeed = Random.nextFloat() * 10f - 5f
            )
        }.toMutableList()
    }
    
    var time by remember { mutableStateOf(0L) }
    
    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        while (time < durationMillis) {
            time = System.currentTimeMillis() - startTime
            
            particles.forEach { p ->
                p.x += p.vx * 0.016f
                p.y += p.vy * 0.016f
                p.vy += 0.5f // Gravity
                p.rotation += p.rotationSpeed
            }
            
            kotlinx.coroutines.delay(16)
        }
        onComplete()
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            drawConfettiParticle(p, size.width, size.height)
        }
    }
}

private fun DrawScope.drawConfettiParticle(confetti: Confetti, width: Float, height: Float) {
    val x = confetti.x * width
    val y = confetti.y * height
    
    // Only draw if in bounds
    if (y < height + 100) {
        drawRect(
            color = confetti.color,
            topLeft = Offset(x - 4, y - 8),
            size = androidx.compose.ui.geometry.Size(8f, 16f)
        )
    }
}

/**
 * Success overlay with checkmark and optional confetti
 */
@Composable
fun SuccessAnimation(
    show: Boolean,
    modifier: Modifier = Modifier,
    withConfetti: Boolean = true,
    onComplete: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    
    AnimatedVisibility(
        visible = show,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (withConfetti) {
                CelebrationConfetti(onComplete = {})
            }
            
            SuccessCheckmark(
                size = 120.dp,
                onAnimationEnd = {
                    scope.launch {
                        kotlinx.coroutines.delay(1000)
                        onComplete()
                    }
                }
            )
        }
    }
}
