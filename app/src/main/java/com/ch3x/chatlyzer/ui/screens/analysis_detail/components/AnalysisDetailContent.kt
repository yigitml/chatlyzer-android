package com.ch3x.chatlyzer.ui.screens.analysis_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Brush
import com.ch3x.chatlyzer.ui.theme.BackgroundDark
import com.ch3x.chatlyzer.ui.theme.SurfaceDark

@Composable
fun AnalysisDetailContent(
    state: AnalysisDetailState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
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
}