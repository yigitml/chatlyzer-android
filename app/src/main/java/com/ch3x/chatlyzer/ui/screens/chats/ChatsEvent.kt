package com.ch3x.chatlyzer.ui.screens.chats

sealed class ChatsEvent {
    object GetAllChats : ChatsEvent()
    data class DeleteChat(val id: String) : ChatsEvent()
    data class ShowError(val message: String) : ChatsEvent()
}