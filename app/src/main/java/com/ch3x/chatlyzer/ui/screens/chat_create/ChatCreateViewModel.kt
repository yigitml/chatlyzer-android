package com.ch3x.chatlyzer.ui.screens.chat_create

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch3x.chatlyzer.data.mapper.toDto
import com.ch3x.chatlyzer.data.remote.ChatPostRequest
import com.ch3x.chatlyzer.data.repository.SharedDataRepository
import com.ch3x.chatlyzer.domain.model.Message
import com.ch3x.chatlyzer.domain.use_case.CreateChatUseCase
import com.ch3x.chatlyzer.util.ChatPlatform
import com.ch3x.chatlyzer.util.MessageConverterFactory
import com.ch3x.chatlyzer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import androidx.core.net.toUri
import com.ch3x.chatlyzer.data.remote.AnalysisType
import com.ch3x.chatlyzer.data.remote.PrivacyAnalysisPostRequest
import com.ch3x.chatlyzer.domain.use_case.CreatePrivacyAnalysisUseCase

@HiltViewModel
class ChatCreateViewModel @Inject constructor(
    private val createChatUseCase: CreateChatUseCase,
    private val sharedDataRepository: SharedDataRepository,
    private val createPrivacyAnalysisUseCase: CreatePrivacyAnalysisUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(ChatCreateState())
    val state: State<ChatCreateState> = _state

    init {
        checkForSharedFile()
    }

    private fun checkForSharedFile() {
        val sharedFile = sharedDataRepository.getPendingSharedFile()
        if (sharedFile != null) {
            val (fileUri, fileType) = sharedFile
            onEvent(ChatCreateEvent.HandleSharedFile(fileUri, fileType))
            sharedDataRepository.clearPendingSharedFile()
        }
    }

    fun onEvent(event: ChatCreateEvent) {
        when (event) {
            is ChatCreateEvent.UpdateTitle -> updateTitle(event.title)
            is ChatCreateEvent.AddMessage -> addMessage(event.message)
            is ChatCreateEvent.RemoveMessage -> removeMessage(event.messageIndex)
            is ChatCreateEvent.UpdateMessage -> updateMessage(event.messageIndex, event.message)
            is ChatCreateEvent.HandleSharedFile -> handleSharedFile(event.fileUri, event.fileType)
            is ChatCreateEvent.ImportFile -> importFile(event.fileUri)
            is ChatCreateEvent.SelectPlatform -> selectPlatform(event.platform)
            is ChatCreateEvent.ConfirmPlatformSelection -> confirmPlatformSelection(event.platform)
            is ChatCreateEvent.ChangeAnalysisType -> changeAnalysisType(analysisType = event.analysisType)
            is ChatCreateEvent.CreateChat -> createChat()
            is ChatCreateEvent.ClearError -> clearError()
            is ChatCreateEvent.ClearImportedData -> clearImportedData()
            is ChatCreateEvent.ShowPlatformSelector -> showPlatformSelector()
            is ChatCreateEvent.HidePlatformSelector -> hidePlatformSelector()
            is ChatCreateEvent.RetryImport -> retryImport()
        }
    }

    private fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    private fun addMessage(message: Message) {
        val currentMessages = _state.value.messages.toMutableList()
        currentMessages.add(message)
        _state.value = _state.value.copy(messages = currentMessages)
    }

    private fun removeMessage(messageIndex: Int) {
        val currentMessages = _state.value.messages.toMutableList()
        if (messageIndex in currentMessages.indices) {
            currentMessages.removeAt(messageIndex)
            _state.value = _state.value.copy(messages = currentMessages)
        }
    }

    private fun updateMessage(messageIndex: Int, message: Message) {
        val currentMessages = _state.value.messages.toMutableList()
        if (messageIndex in currentMessages.indices) {
            currentMessages[messageIndex] = message
            _state.value = _state.value.copy(messages = currentMessages)
        }
    }

    private fun handleSharedFile(fileUri: String, fileType: String) {
        val uri = fileUri.toUri()
        importFile(uri)
    }

    private fun importFile(fileUri: Uri) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isImporting = true,
                    importProgress = 0f,
                    errorMessage = ""
                )

                val fileContent = readFileContent(fileUri)
                if (fileContent.isBlank()) {
                    _state.value = _state.value.copy(
                        isImporting = false,
                        errorMessage = "File is empty or could not be read"
                    )
                    return@launch
                }

                _state.value = _state.value.copy(importProgress = 0.3f)

                val detectedPlatform = MessageConverterFactory.detectPlatform(fileContent)

                _state.value = _state.value.copy(
                    importProgress = 0.5f,
                    detectedPlatform = detectedPlatform
                )

                val convertedMessages =
                    MessageConverterFactory.convertMessages(fileContent, detectedPlatform)

                _state.value = _state.value.copy(importProgress = 0.8f)

                val messages = convertedMessages.map { convertedMsg ->
                    Message(
                        id = UUID.randomUUID().toString(),
                        chatId = "",
                        sender = convertedMsg.sender,
                        content = convertedMsg.content,
                        timestamp = convertedMsg.timestamp,
                        createdAt = Date(),
                        updatedAt = Date()
                    )
                }

                val fileName = getFileName(fileUri)
                val fileSize = getFileSize(fileUri)

                _state.value = _state.value.copy(
                    isImporting = false,
                    importProgress = 1f,
                    messages = messages,
                    title = generateChatTitle(detectedPlatform, messages.size),
                    selectedPlatform = detectedPlatform,
                    importedFileInfo = ImportedFileInfo(
                        fileName = fileName,
                        fileSize = fileSize,
                        messageCount = messages.size,
                        platform = detectedPlatform
                    )
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isImporting = false,
                    importProgress = 0f,
                    errorMessage = "Failed to import file: ${e.message}"
                )
            }
        }
    }

    private suspend fun readFileContent(uri: Uri): String = withContext(Dispatchers.IO) {
        context.contentResolver.openInputStream(uri)?.bufferedReader(Charsets.UTF_8)?.use {
            it.readText()
        } ?: ""
    }

    private fun getFileName(uri: Uri): String {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                cursor.getString(nameIndex)
            } ?: "Unknown file"
        } catch (e: Exception) {
            "Unknown file"
        }
    }

    private fun getFileSize(uri: Uri): Long {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val sizeIndex = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE)
                cursor.moveToFirst()
                cursor.getLong(sizeIndex)
            } ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    private fun generateChatTitle(platform: ChatPlatform, messageCount: Int): String {
        val platformName = when (platform) {
            ChatPlatform.WHATSAPP -> "WhatsApp"
            ChatPlatform.INSTAGRAM -> "Instagram"
        }
        return "$platformName Chat ($messageCount messages)"
    }

    private fun selectPlatform(platform: ChatPlatform) {
        _state.value = _state.value.copy(selectedPlatform = platform)
    }

    private fun confirmPlatformSelection(platform: ChatPlatform) {
        _state.value = _state.value.copy(
            selectedPlatform = platform,
            showPlatformSelector = false
        )

        // TODO: Implement platform selection logic
        _state.value.importedFileInfo?.let {
            // You might want to re-parse with the new platform
            // For now, just update the platform
        }
    }

    private fun changeAnalysisType(analysisType: com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType) {
        _state.value = _state.value.copy(
            analysisType = analysisType
        )
    }

    private fun showPlatformSelector() {
        _state.value = _state.value.copy(showPlatformSelector = true)
    }

    private fun hidePlatformSelector() {
        _state.value = _state.value.copy(showPlatformSelector = false)
    }

    private fun clearImportedData() {
        _state.value = _state.value.copy(
            messages = emptyList(),
            importedFileInfo = null,
            detectedPlatform = null,
            selectedPlatform = ChatPlatform.WHATSAPP,
            title = ""
        )
    }

    private fun retryImport() {
        // TODO: This would require storing the original file URI
        // For now, just clear the error
        _state.value = _state.value.copy(errorMessage = "")
    }

    private fun createChat() {
        val analysisType: com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType = _state.value.analysisType

        when (analysisType) {
            com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType.NORMAL -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isCreating = true, errorMessage = "")

                    try {
                        createChatUseCase(
                            ChatPostRequest(
                                title = _state.value.title,
                                messages = _state.value.messages.map { it.toDto() }
                            )
                        ).collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    _state.value = _state.value.copy(
                                        isCreating = false,
                                        createdChatId = result.data.id
                                    )
                                }

                                is Resource.Error -> {
                                    _state.value = _state.value.copy(
                                        isCreating = false,
                                        errorMessage = result.message
                                    )
                                }

                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(
                                        isCreating = true
                                    )
                                }
                            }
                        }


                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            isCreating = false,
                            errorMessage = "An unexpected error occurred: ${e.message}"
                        )
                    }
                }
            }
            else -> {
                createPrivacyAnalysis()
            }
        }
    }

    private fun createPrivacyAnalysis() {
        val isGhostMode = _state.value.analysisType == com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType.GHOST

        if(_state.value.title.isBlank()) {
            _state.value = _state.value.copy(errorMessage = "Please enter a chat title")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isCreating = true, errorMessage = "")

            try {
                createPrivacyAnalysisUseCase(
                    PrivacyAnalysisPostRequest(
                        title = _state.value.title,
                        isGhostMode = isGhostMode,
                        messages = _state.value.messages.map {
                            PrivacyAnalysisPostRequest.Message(
                                it.sender,
                                it.timestamp,
                                it.content,
                                it.metadata
                            )
                        }
                    )
                ).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (isGhostMode) {
                                _state.value = _state.value.copy()
                            } else {
                                _state.value = _state.value.copy(
                                    isCreating = false,
                                    createdChatId = result.data[0].chatId
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isCreating = false,
                                errorMessage = result.message
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isCreating = true
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isCreating = false,
                    errorMessage = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(errorMessage = "")
    }
}