package com.ch3x.chatlyzer.ui.screens.analyzes

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch3x.chatlyzer.data.remote.AnalysisPostRequest
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.domain.use_case.CreateAnalysisUseCase
import com.ch3x.chatlyzer.domain.use_case.GetAllAnalyzesUseCase
import com.ch3x.chatlyzer.domain.use_case.GetAnalyzesByChatIdUseCase
import com.ch3x.chatlyzer.domain.use_case.GetChatByIdUseCase
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.lifecycle.SavedStateHandle

@HiltViewModel
class AnalyzesViewModel @Inject constructor(
    private val getAnalyzesByChatIdUseCase: GetAnalyzesByChatIdUseCase,
    private val getAllAnalyzesUseCase: GetAllAnalyzesUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase,
    private val createAnalysisUseCase: CreateAnalysisUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(AnalyzesState())
    val state: State<AnalyzesState> = _state

    init {
        val chatId = savedStateHandle.get<String>("chatId")
        if (!chatId.isNullOrEmpty()) {
            getAnalyzesByChatId(chatId)
            getChatById(chatId)
        } else {
            getAllAnalyzes()
        }
    }

    private fun handleAnalyzesResource(resource: Resource<List<Analysis>>) {
        _state.value = when (resource) {
            is Resource.Success -> {
                _state.value.copy(
                    screenState = ScreenState.Success,
                    analyzes = resource.data,
                    isAnalysisInProgress = false
                )
            }

            is Resource.Error -> {
                _state.value.copy(
                    screenState = ScreenState.Error,
                    errorMessage = resource.message
                )
            }

            is Resource.Loading -> {
                _state.value.copy(
                    screenState = ScreenState.Loading
                )
            }
        }
    }

    private fun handleChatResource(resource: Resource<Chat>) {
        if (resource is Resource.Success) {
            _state.value = _state.value.copy(
                chat = resource.data
            )
        }
    }

    private fun getAnalyzesByChatId(chatId: String) {
        viewModelScope.launch {
            getAnalyzesByChatIdUseCase(chatId).collect {
                handleAnalyzesResource(it)
            }
        }
    }

    private fun getAllAnalyzes() {
        viewModelScope.launch {
            getAllAnalyzesUseCase().collect {
                handleAnalyzesResource(it)
            }
        }
    }

    private fun getChatById(chatId: String) {
        viewModelScope.launch {
            getChatByIdUseCase(chatId).collect {
                handleChatResource(it)
            }
        }
    }

    private fun createAnalysis(chatId: String) {
        viewModelScope.launch {
            createAnalysisUseCase(AnalysisPostRequest(chatId)).collect { resource ->
                if (resource is Resource.Error && resource.message?.contains("already in progress", ignoreCase = true) == true) {
                    _state.value = _state.value.copy(
                        isAnalysisInProgress = true,
                        errorMessage = ""
                    )
                    startPolling(chatId)
                } else {
                    handleAnalyzesResource(resource)
                }
            }
        }
    }

    private fun startPolling(chatId: String, retryCount: Int = 0) {
        if (retryCount > 20) { // 1 minute timeout
            _state.value = _state.value.copy(
                isAnalysisInProgress = false,
                errorMessage = "Analysis timed out. Please try again."
            )
            return
        }
        viewModelScope.launch {
            delay(3000) // Wait 3 seconds
            getAnalyzesByChatIdUseCase(chatId).collect { resource ->
                if (resource is Resource.Success) {
                    if (resource.data.isNotEmpty()) {
                        handleAnalyzesResource(resource)
                    } else {
                        startPolling(chatId, retryCount + 1)
                    }
                } else {
                    handleAnalyzesResource(resource)
                }
            }
        }
    }

    fun onEvent(event: AnalyzesEvent) {
        when (event) {
            is AnalyzesEvent.GetAnalyzesByChatId -> {
                getAnalyzesByChatId(event.chatId)
            }

            AnalyzesEvent.GetAllAnalyzes -> {
                getAllAnalyzes()
            }

            is AnalyzesEvent.GetChatById -> {
                getChatById(event.id)
            }

            is AnalyzesEvent.AnalyzeChat -> {
                createAnalysis(event.chatId)
            }
        }
    }
}