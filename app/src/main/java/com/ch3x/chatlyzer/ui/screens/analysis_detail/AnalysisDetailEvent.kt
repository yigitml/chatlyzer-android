package com.ch3x.chatlyzer.ui.screens.analysis_detail

sealed class AnalysisDetailEvent {
    data class GetAnalysisById(val id: String) : AnalysisDetailEvent()
}