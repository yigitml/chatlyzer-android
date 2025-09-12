package com.ch3x.chatlyzer.ui.screens.analyzes

import androidx.compose.runtime.Immutable
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.ui.screens.ScreenState

@Immutable
data class AnalyzesState(
    val screenState: ScreenState = ScreenState.Loading,
    val chat: Chat? = null,
    val analyzes: List<Analysis> = emptyList(),
    val errorMessage: String = ""
)