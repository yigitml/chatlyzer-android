package com.ch3x.chatlyzer.ui.screens.analysis_carousel

import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisType

data class AnalysisCarouselState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val chat: Chat? = null,
    val analysesByType: Map<AnalysisType, Analysis> = emptyMap(),
    val isGhostMode: Boolean = false
)
