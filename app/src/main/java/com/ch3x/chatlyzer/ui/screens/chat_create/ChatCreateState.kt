package com.ch3x.chatlyzer.ui.screens.chat_create

import androidx.compose.runtime.Immutable
import com.ch3x.chatlyzer.domain.model.Message
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.util.ChatPlatform

@Immutable
data class ChatCreateState(
    val screenState: ScreenState = ScreenState.Success,
    val title: String = "",
    val messages: List<Message> = emptyList(),
    val errorMessage: String = "",
    val isCreating: Boolean = false,
    val createdChatId: String? = null,
    val isImporting: Boolean = false,
    val importProgress: Float = 0f,
    val detectedPlatform: ChatPlatform? = null,
    val selectedPlatform: ChatPlatform = ChatPlatform.WHATSAPP,
    val showPlatformSelector: Boolean = false,
    val importedFileInfo: ImportedFileInfo? = null,
    val analysisType: AnalysisType = AnalysisType.NORMAL
)

@Immutable
data class ImportedFileInfo(
    val fileName: String,
    val fileSize: Long,
    val messageCount: Int,
    val platform: ChatPlatform
)

enum class AnalysisType{
    NORMAL,
    PRIVACY,
    GHOST
}