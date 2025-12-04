package com.ch3x.chatlyzer.ui.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Parallax effect for scrolling content
 */
@Composable
fun ParallaxBox(
    modifier: Modifier = Modifier,
    parallaxFactor: Float = 0.5f,
    content: @Composable BoxScope.(scrollOffset: Float) -> Unit
) {
    var scrollOffset by remember { mutableStateOf(0f) }
    
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()
                    scrollOffset += dragAmount * parallaxFactor
                }
            }
    ) {
        content(scrollOffset)
    }
}

/**
 * Pull to refresh with custom animation
 */
@Composable
fun PullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    refreshThreshold: Float = 150f,
    content: @Composable () -> Unit
) {
    var pullOffset by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()
    
    val animatedOffset by animateFloatAsState(
        targetValue = if (isRefreshing) refreshThreshold else pullOffset.coerceIn(0f, refreshThreshold),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "pull_offset"
    )
    
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        // Refresh indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(refreshThreshold.dp)
                .offset { IntOffset(0, (animatedOffset - refreshThreshold).roundToInt()) },
            contentAlignment = Alignment.Center
        ) {
            if (isRefreshing) {
                LoadingDots()
            } else {
                val progress = (animatedOffset / refreshThreshold).coerceIn(0f, 1f)
                
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.size(40.dp)
                ) {
                    val sweepAngle = 360f * progress
                    drawArc(
                        color = com.ch3x.chatlyzer.ui.theme.GoldenYellow,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 4.dp.toPx(),
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    )
                }
            }
        }
        
        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, animatedOffset.roundToInt()) }
                .pointerInput(isRefreshing) {
                    if (!isRefreshing) {
                        detectVerticalDragGestures(
                            onDragEnd = {
                                if (pullOffset >= refreshThreshold) {
                                    onRefresh()
                                }
                                coroutineScope.launch {
                                    pullOffset = 0f
                                }
                            },
                            onDragCancel = {
                                coroutineScope.launch {
                                    pullOffset = 0f
                                }
                            },
                            onVerticalDrag = { change, dragAmount ->
                                if (dragAmount > 0 || pullOffset > 0) {
                                    change.consume()
                                    pullOffset = (pullOffset + dragAmount).coerceIn(0f, refreshThreshold * 1.5f)
                                }
                            }
                        )
                    }
                }
        ) {
            content()
        }
    }
}

/**
 * Swipe to dismiss/action
 */
@Composable
fun SwipeToDismiss(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    dismissThreshold: Float = 0.5f,
    enabled: Boolean = true,
    content: @Composable (swipeProgress: Float) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var containerWidth by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(enabled) {
                if (enabled) {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            val progress = kotlin.math.abs(offsetX) / containerWidth
                            if (progress >= dismissThreshold) {
                                onDismiss()
                            } else {
                                coroutineScope.launch {
                                    offsetX = 0f
                                }
                            }
                        },
                        onDragCancel = {
                            coroutineScope.launch {
                                offsetX = 0f
                            }
                        },
                        onVerticalDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount
                        }
                    )
                }
            }
            .graphicsLayer {
                translationX = offsetX
                val progress = (kotlin.math.abs(offsetX) / containerWidth).coerceIn(0f, 1f)
                alpha = 1f - progress
            }
    ) {
        content(kotlin.math.abs(offsetX) / containerWidth)
    }
}
