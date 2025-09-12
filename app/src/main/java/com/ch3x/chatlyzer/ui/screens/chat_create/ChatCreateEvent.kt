package com.ch3x.chatlyzer.ui.screens.chat_create

import android.net.Uri
import com.ch3x.chatlyzer.domain.model.Message
import com.ch3x.chatlyzer.util.ChatPlatform

sealed class ChatCreateEvent {
    data class UpdateTitle(val title: String) : ChatCreateEvent()
    data class AddMessage(val message: Message) : ChatCreateEvent()
    data class RemoveMessage(val messageIndex: Int) : ChatCreateEvent()
    data class UpdateMessage(val messageIndex: Int, val message: Message) : ChatCreateEvent()
    data class HandleSharedFile(val fileUri: String, val fileType: String) : ChatCreateEvent()
    data class ImportFile(val fileUri: Uri) : ChatCreateEvent()
    data class SelectPlatform(val platform: ChatPlatform) : ChatCreateEvent()
    data class ConfirmPlatformSelection(val platform: ChatPlatform) : ChatCreateEvent()
    object CreateChat : ChatCreateEvent()
    object ClearError : ChatCreateEvent()
    object ClearImportedData : ChatCreateEvent()
    object ShowPlatformSelector : ChatCreateEvent()
    object HidePlatformSelector : ChatCreateEvent()
    object RetryImport : ChatCreateEvent()
} 