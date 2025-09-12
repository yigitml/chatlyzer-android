package com.ch3x.chatlyzer.ui.screens.analysis_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisUIBuilder
import com.ch3x.chatlyzer.ui.components.EmptyState
import com.ch3x.chatlyzer.ui.components.ErrorState
import com.ch3x.chatlyzer.ui.components.LoadingState
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.ui.screens.analysis_detail.AnalysisDetailState
import com.ch3x.chatlyzer.util.JsonMapFactory

@Composable
fun AnalysisDetailContent(
    state: AnalysisDetailState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (state.screenState) {
            ScreenState.Loading -> {
                LoadingState()
            }

            ScreenState.Error -> {
                ErrorState(
                    title = "Error loading analysis",
                    errorMessage = state.errorMessage
                )
            }

            ScreenState.Success -> {
                state.analysis?.result?.let { result ->
                    state.analysisType?.let { analysisType ->
                        AnalysisUIBuilder().BuildAnalysisUI(
                            analysisType,
                            JsonMapFactory.toJson(result)
                        )
                    }
                } ?: EmptyState(
                    title = "No analysis data available",
                )
            }
        }
    }
}