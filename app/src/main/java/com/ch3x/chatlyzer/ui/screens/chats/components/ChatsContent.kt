package com.ch3x.chatlyzer.ui.screens.chats.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.components.CreateFab
import com.ch3x.chatlyzer.ui.components.EmptyState
import com.ch3x.chatlyzer.ui.components.ErrorState
import com.ch3x.chatlyzer.ui.components.LoadingState
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.ui.screens.chats.ChatsState

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Brush
import com.ch3x.chatlyzer.ui.theme.BackgroundDark
import com.ch3x.chatlyzer.ui.theme.SurfaceDark
import com.ch3x.chatlyzer.ui.components.animations.AnimatedEmptyState
import com.ch3x.chatlyzer.ui.components.GradientButton

@Composable
fun ChatsContent(
    state: ChatsState,
    onNavigateAnalysis: (String) -> Unit,
    onCreateChat: () -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.screenState) {
            ScreenState.Loading -> {
                LoadingState()
            }

            ScreenState.Success -> {
                if (state.chats.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AnimatedEmptyState(
                            title = "No analyses yet",
                            subtitle = "Import a chat to start analyzing your conversations",
                            emoji = "💬"
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        GradientButton(
                            text = "Analyze New Chat",
                            onClick = onCreateChat
                        )
                    }
                } else {
                    Box {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Your Analyses",
                                Modifier.padding(bottom = 8.dp, top = 16.dp),
                                fontWeight = FontWeight.Normal,
                                fontSize = 22.sp
                            )

                            ChatList(
                                modifier = Modifier.padding().weight(1f),
                                chats = state.chats,
                                onChatClick = onNavigateAnalysis,
                                onDelete = onDelete
                            )
                        }

                        CreateFab(
                            onClick = onCreateChat,
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                        )
                    }
                }
            }

            ScreenState.Error -> {
                ErrorState(errorMessage = state.errorMessage)
            }
        }
    }
}

