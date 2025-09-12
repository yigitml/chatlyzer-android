package com.ch3x.chatlyzer.ui.screens.analyzes

sealed class AnalyzesEvent {
    object GetAllAnalyzes : AnalyzesEvent()
    data class GetAnalyzesByChatId(val chatId: String) : AnalyzesEvent()
    data class GetChatById(val id: String) : AnalyzesEvent()
    data class AnalyzeChat(val chatId: String) : AnalyzesEvent()
}