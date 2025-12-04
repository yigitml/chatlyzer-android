package com.ch3x.chatlyzer.ui.screens.analysis_carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.use_case.GetAnalyzesByChatIdUseCase
import com.ch3x.chatlyzer.domain.use_case.GetChatByIdUseCase
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisType
import com.ch3x.chatlyzer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnalysisCarouselViewModel @Inject constructor(
    private val getChatByIdUseCase: GetChatByIdUseCase,
    private val getAnalyzesByChatIdUseCase: GetAnalyzesByChatIdUseCase,
    private val createAnalysisUseCase: com.ch3x.chatlyzer.domain.use_case.CreateAnalysisUseCase,
    private val ghostResultRepository: com.ch3x.chatlyzer.data.repository.GhostResultRepository
) : ViewModel() {

    private val _state = mutableStateOf(AnalysisCarouselState())
    val state: State<AnalysisCarouselState> = _state

    fun loadData(chatId: String) {
        // Check if this is Ghost Mode
        if (chatId == "GHOST_MODE") {
            val ghostResults = ghostResultRepository.getResults()
            if (ghostResults != null) {
                val analysesByType = ghostResults.mapNotNull { analysis ->
                    val typeStr = analysis.result?.get("type") as? String
                    val type = typeStr?.let { AnalysisType.fromString(it) }
                    if (type != null) type to analysis else null
                }.toMap()

                _state.value = _state.value.copy(
                    isLoading = false,
                    analysesByType = analysesByType,
                    isGhostMode = true
                )
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ghost Mode data not found",
                    isGhostMode = true
                )
            }
            return
        }

        // Normal mode - fetch from backend
        // Fetch Chat Details
        getChatByIdUseCase(chatId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(chat = result.data)
                }
                is Resource.Error -> {
                    // Handle error if needed, but maybe prioritize analysis error
                }
                is Resource.Loading -> {
                    // Loading state handled by analysis fetch mostly
                }
            }
        }.launchIn(viewModelScope)

        // Fetch Analyses
        getAnalyzesByChatIdUseCase(chatId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val analyses = result.data
                    val analysesByType = analyses.mapNotNull { analysis ->
                        val typeStr = analysis.result?.get("type") as? String
                        val type = typeStr?.let { AnalysisType.fromString(it) }
                        if (type != null) type to analysis else null
                    }.toMap()

                    _state.value = state.value.copy(
                        isLoading = false,
                        analysesByType = analysesByType
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createAnalysis(chatId: String) {
        createAnalysisUseCase(com.ch3x.chatlyzer.data.remote.AnalysisPostRequest(chatId = chatId)).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Refresh data after successful creation
                    loadData(chatId)
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
