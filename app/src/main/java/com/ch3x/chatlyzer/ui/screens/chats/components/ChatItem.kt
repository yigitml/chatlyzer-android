package com.ch3x.chatlyzer.ui.screens.chats.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.ui.components.GlassCard
import com.ch3x.chatlyzer.ui.components.animations.AnimatedListItem
import com.ch3x.chatlyzer.ui.components.animations.ScaleOnPress
import com.ch3x.chatlyzer.ui.components.animations.SwipeToDismiss

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatItem(
    chat: Chat,
    index: Int = 0,
    onChatClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    
    // Track if we've triggered haptic for the current swipe to avoid spamming
    var hasTriggeredHaptic by remember { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete(chat.id)
                true
            } else {
                false
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.7f }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                    else -> Color.Transparent
                },
                label = "SwipeBackground"
            )
            
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val widthPx = constraints.maxWidth.toFloat()
                val thresholdPx = widthPx * 0.7f
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = color,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    val offset = try {
                        kotlin.math.abs(dismissState.requireOffset())
                    } catch (e: IllegalStateException) {
                        0f
                    }
                    
                    val progress = (offset / thresholdPx).coerceIn(0f, 1f)
                    val isReadyToDelete = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart

                    // Haptic feedback logic
                    LaunchedEffect(isReadyToDelete) {
                        if (isReadyToDelete && !hasTriggeredHaptic) {
                            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                            hasTriggeredHaptic = true
                        } else if (!isReadyToDelete) {
                            hasTriggeredHaptic = false
                        }
                    }

                    val scale by animateFloatAsState(
                        if (isReadyToDelete) 1.3f else 1f,
                        label = "SwipeIconScale",
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                    )
                    
                    val iconColor = MaterialTheme.colorScheme.onError

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.graphicsLayer {
                            alpha = (offset / 100f).coerceIn(0f, 1f)
                        }
                    ) {
                        if (isReadyToDelete) {
                            Text(
                                text = "Release to delete",
                                style = MaterialTheme.typography.labelMedium,
                                color = iconColor
                            )
                        }
                        
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = iconColor,
                            modifier = Modifier.graphicsLayer(
                                scaleX = scale, 
                                scaleY = scale
                            )
                        )
                    }
                }
            }
        },
        content = {
            AnimatedListItem(
                index = index,
                modifier = Modifier
            ) {
                ScaleOnPress(
                    pressed = isPressed,
                    targetScale = 0.97f
                ) {
                    GlassCard(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        isPressed = true
                                        tryAwaitRelease()
                                        isPressed = false
                                    },
                                    onTap = {
                                        onChatClick(chat.id)
                                    }
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                ChatHeader(
                                    chat = chat
                                )
                            }
                        }
                    }
                }
            }
        }
    )
} 