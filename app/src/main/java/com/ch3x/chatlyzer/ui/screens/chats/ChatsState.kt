package com.ch3x.chatlyzer.ui.screens.chats

import androidx.compose.runtime.Immutable
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.ui.screens.ScreenState

@Immutable
data class ChatsState(
    val screenState: ScreenState = ScreenState.Loading,
    val chats: List<Chat> = emptyList(),
    val errorMessage: String = ""
)