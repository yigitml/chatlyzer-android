package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Bouncy button with scale animation on press
 */
@Composable
fun BouncyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    bounciness: Float = 0.92f,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) bounciness else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bouncy_scale"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        },
                        onTap = { onClick() }
                    )
                }
            }
    ) {
        content()
    }
}

/**
 * Button with rotation animation on press
 */
@Composable
fun RotatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    rotationDegrees: Float = 15f,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) rotationDegrees else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                rotationZ = rotation
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        },
                        onTap = { onClick() }
                    )
                }
            }
    ) {
        content()
    }
}

/**
 * Icon with shake animation
 */
@Composable
fun ShakeIcon(
    trigger: Boolean,
    modifier: Modifier = Modifier,
    shakeDegrees: Float = 15f,
    content: @Composable () -> Unit
) {
    var shouldShake by remember { mutableStateOf(false) }
    
    LaunchedEffect(trigger) {
        if (trigger) {
            shouldShake = true
            kotlinx.coroutines.delay(500)
            shouldShake = false
        }
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "shake")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = -shakeDegrees,
        targetValue = shakeDegrees,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 100,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake_rotation"
    )

    Box(
        modifier = modifier.graphicsLayer {
            rotationZ = if (shouldShake) rotation else 0f
        }
    ) {
        content()
    }
}

/**
 * Icon with pop animation (scale bounce)
 */
@Composable
fun PopIcon(
    trigger: Boolean,
    modifier: Modifier = Modifier,
    maxScale: Float = 1.3f,
    content: @Composable () -> Unit
) {
    var shouldPop by remember { mutableStateOf(false) }
    
    LaunchedEffect(trigger) {
        if (trigger) {
            shouldPop = true
            kotlinx.coroutines.delay(300)
            shouldPop = false
        }
    }
    
    val scale by animateFloatAsState(
        targetValue = if (shouldPop) maxScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "pop_scale"
    )

    Box(
        modifier = modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
    ) {
        content()
    }
}
