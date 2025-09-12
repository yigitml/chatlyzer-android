package com.ch3x.chatlyzer.ui.screens.analysis_detail

import androidx.compose.runtime.Immutable
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisType

@Immutable
data class AnalysisDetailState(
    val screenState: ScreenState = ScreenState.Loading,
    val analysis: Analysis? = null,
    val analysisType: AnalysisType? = null,
    val errorMessage: String = ""
)