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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyzesViewModel @Inject constructor(
    private val getAnalyzesByChatIdUseCase: GetAnalyzesByChatIdUseCase,
    private val getAllAnalyzesUseCase: GetAllAnalyzesUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase,
    private val createAnalysisUseCase: CreateAnalysisUseCase
): ViewModel() {

    private val _state = mutableStateOf(AnalyzesState())
    val state: State<AnalyzesState> = _state

    private fun handleAnalyzesResource(resource: Resource<List<Analysis>>) {
        _state.value = when (resource) {
            is Resource.Success -> {
                _state.value.copy(
                    screenState = ScreenState.Success,
                    analyzes = resource.data
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
        _state.value = when (resource) {
            is Resource.Success -> {
                _state.value.copy(
                    screenState = ScreenState.Success,
                    chat = resource.data
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
            createAnalysisUseCase(AnalysisPostRequest(chatId)).collect {
                handleAnalyzesResource(it)
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