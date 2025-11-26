package com.ch3x.chatlyzer.ui.screens.chats.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.ch3x.chatlyzer.ui.components.GlassCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.domain.model.Chat

import androidx.compose.runtime.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import com.ch3x.chatlyzer.ui.components.animations.AnimatedListItem
import com.ch3x.chatlyzer.ui.components.animations.ScaleOnPress

@Composable
fun ChatItem(
    chat: Chat,
    index: Int = 0,
    onChatClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    
    AnimatedListItem(
        index = index,
        modifier = modifier
    ) {
        ScaleOnPress(
            pressed = isPressed,
            targetScale = 0.97f
        ) {
            GlassCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
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
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ChatHeader(
                        chat = chat
                    )
                }
            }
        }
    }
} 