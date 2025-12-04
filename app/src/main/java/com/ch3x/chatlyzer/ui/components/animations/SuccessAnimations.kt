package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.theme.SuccessGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Premium Success Checkmark with scaling circle background and drawing path
 */
@Composable
fun SuccessCheckmark(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 100.dp,
    circleColor: Color = SuccessGreen,
    checkColor: Color = Color.White,
    onAnimationEnd: () -> Unit = {}
) {
    val scale = remember { Animatable(0f) }
    val checkProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // 1. Scale up the circle with overshoot
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing
            )
        )
        
        // 2. Draw the checkmark
        checkProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
        
        // 3. Small pop at the end
        scale.animateTo(
            targetValue = 1.1f,
            animationSpec = tween(100)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        
        delay(500)
        onAnimationEnd()
    }

    Box(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val radius = size.toPx() / 2

            // Draw Circle Background
            drawCircle(
                color = circleColor,
                radius = radius,
                center = center
            )

            // Draw Checkmark
            val checkPath = Path().apply {
                val w = size.toPx()
                val h = size.toPx()
                
                // Start (Left)
                moveTo(w * 0.28f, h * 0.52f)
                // Middle (Bottom)
                lineTo(w * 0.42f, h * 0.68f)
                // End (Right)
                lineTo(w * 0.72f, h * 0.35f)
            }

            // We use a path measure equivalent by drawing a partial path or using clip
            // For simplicity in Compose Canvas, we can use the path effect trick or just draw partial lines
            // But here, let's use the simple dash path effect trick for "drawing" animation
            
            // Calculate total length (approximate)
            val pathLength = size.toPx() // Rough estimate is enough for visual
            
            drawPath(
                path = checkPath,
                color = checkColor,
                style = Stroke(
                    width = 6.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                ),
                alpha = if (checkProgress.value > 0) 1f else 0f
            )
            
            // To make it actually "draw", we would need PathMeasure. 
            // Since we want to keep it simple and robust without complex PathMeasure logic in a single file:
            // We can just use a clipping rect or similar. 
            // OR, simpler: Just let it appear. 
            // But the user wants "better". Let's try to implement the "drawing" effect properly if possible.
            // Actually, the previous implementation used DashPathEffect which works well. Let's reuse that logic but cleaner.
            
             drawPath(
                path = checkPath,
                color = checkColor,
                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        intervals = floatArrayOf(pathLength * 2, pathLength * 2), // Make interval huge
                        phase = pathLength * 2 * (1f - checkProgress.value) // Animate phase
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
    var rotationSpeed: Float,
    var scale: Float = 1f
)

/**
 * Enhanced Celebration confetti effect
 */
@Composable
fun CelebrationConfetti(
    modifier: Modifier = Modifier,
    particleCount: Int = 70,
    durationMillis: Long = 2500,
    onComplete: () -> Unit = {}
) {
    val confettiColors = listOf(
        Color(0xFFFFD700), // Gold
        Color(0xFFFF6B6B), // Red
        Color(0xFF4ECDC4), // Teal
        Color(0xFF45B7D1), // Blue
        Color(0xFF96CEB4)  // Green
    )
    
    val particles = remember {
        List(particleCount) {
            val angle = Random.nextFloat() * 360f
            val speed = Random.nextFloat() * 15f + 10f // Faster explosion
            Confetti(
                x = 0.5f, // Start from center
                y = 0.5f,
                vx = cos(Math.toRadians(angle.toDouble())).toFloat() * speed * 0.05f,
                vy = sin(Math.toRadians(angle.toDouble())).toFloat() * speed * 0.05f - 0.5f, // Initial upward burst
                color = confettiColors.random(),
                rotation = Random.nextFloat() * 360f,
                rotationSpeed = Random.nextFloat() * 10f - 5f,
                scale = Random.nextFloat() * 0.5f + 0.5f
            )
        }.toMutableList()
    }
    
    var time by remember { mutableStateOf(0L) }
    
    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis()
        while (time < durationMillis) {
            val currentTime = System.currentTimeMillis()
            val dt = 16f // approx 60fps
            time = currentTime - startTime
            
            particles.forEach { p ->
                p.x += p.vx
                p.y += p.vy
                p.vy += 0.02f // Gravity
                p.rotation += p.rotationSpeed
                // Air resistance
                p.vx *= 0.98f
                p.vy *= 0.98f
            }
            
            delay(16)
        }
        onComplete()
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            val x = p.x * size.width
            val y = p.y * size.height
            
            if (x in 0f..size.width && y in 0f..size.height) {
                drawRect(
                    color = p.color,
                    topLeft = Offset(x, y),
                    size = Size(12f * p.scale, 24f * p.scale),
                    alpha = (1f - time.toFloat() / durationMillis).coerceIn(0f, 1f)
                )
            }
        }
    }
}

/**
 * Success overlay with premium animation
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
        enter = fadeIn(tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = tween(300, easing = FastOutSlowInEasing)),
        exit = fadeOut(tween(300)) + scaleOut(targetScale = 0.8f, animationSpec = tween(300))
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f)) // Slight transparency
                .windowInsetsPadding(WindowInsets.systemBars),
            contentAlignment = Alignment.Center
        ) {
            if (withConfetti) {
                CelebrationConfetti(onComplete = {})
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SuccessCheckmark(
                    size = 120.dp,
                    onAnimationEnd = {
                        scope.launch {
                            delay(800)
                            onComplete()
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Chat Created!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.animateEnterExit(
                        enter = fadeIn(tween(500, delayMillis = 200)) + slideInVertically(tween(500, delayMillis = 200)) { 40 },
                        exit = fadeOut()
                    )
                )
            }
        }
    }
}
