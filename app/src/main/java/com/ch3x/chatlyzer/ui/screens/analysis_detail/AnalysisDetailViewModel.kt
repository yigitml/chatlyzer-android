package com.ch3x.chatlyzer.ui.screens.analysis_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.use_case.GetAnalysisByIdUseCase
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.util.Resource
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisDetailViewModel @Inject constructor(
    private val getAnalysisByIdUseCase: GetAnalysisByIdUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AnalysisDetailState())
    val state: State<AnalysisDetailState> = _state

    private fun handleAnalysisResource(resource: Resource<Analysis>) {
        _state.value = when (resource) {
            is Resource.Success -> {
                val analysisTypeString = resource.data.result?.get("type") as? String
                val analysisType = analysisTypeString?.let { AnalysisType.fromString(it) }
                AnalysisDetailState(
                    screenState = ScreenState.Success,
                    analysis = resource.data,
                    analysisType = analysisType
                )
            }

            is Resource.Error -> {
                AnalysisDetailState(
                    screenState = ScreenState.Error,
                    errorMessage = resource.message
                )
            }

            is Resource.Loading -> {
                AnalysisDetailState(
                    screenState = ScreenState.Loading
                )
            }
        }
    }

    private fun getAnalysisById(id: String) {
        viewModelScope.launch {
            getAnalysisByIdUseCase(id).collect {
                handleAnalysisResource(it)
            }
        }
    }

    fun onEvent(event: AnalysisDetailEvent) {
        when (event) {
            is AnalysisDetailEvent.GetAnalysisById -> {
                getAnalysisById(event.id)
            }
        }
    }
}