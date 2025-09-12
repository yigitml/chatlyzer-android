package com.ch3x.chatlyzer.ui.screens.chats.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun ChatsContent(
    state: ChatsState,
    onNavigateAnalysis: (String) -> Unit,
    onCreateChat: () -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (state.screenState) {
            ScreenState.Loading -> {
                LoadingState()
            }

            ScreenState.Success -> {
                if (state.chats.isEmpty()) {
                    EmptyState(
                        title = "No chats yet",
                        subtitle = "Import your first WhatsApp chat to get started with analysis",
                        icon = Icons.AutoMirrored.Outlined.Chat,
                        buttonText = "Add Chat",
                        onButtonClick = onCreateChat
                    )
                } else {
                    Box {
                        CreateFab(
                            onClick = onCreateChat,
                            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                        )

                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Your Chats",
                                Modifier.padding(bottom = 8.dp, top = 16.dp),
                                fontWeight = FontWeight.Normal,
                                fontSize = 22.sp
                            )

                            ChatList(
                                modifier = Modifier.padding(),
                                chats = state.chats,
                                onChatClick = onNavigateAnalysis,
                                onDelete = onDelete
                            )
                        }
                    }
                }
            }

            ScreenState.Error -> {
                ErrorState(errorMessage = state.errorMessage)
            }
        }
    }
}

