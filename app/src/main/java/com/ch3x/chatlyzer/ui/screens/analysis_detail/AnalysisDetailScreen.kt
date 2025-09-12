package com.ch3x.chatlyzer.ui.screens.analysis_detail

import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme
import com.ch3x.chatlyzer.ui.screens.analysis_detail.components.AnalysisDetailContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisDetailScreen(
    analysisId: String,
    viewModel: AnalysisDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(analysisId) {
        viewModel.onEvent(AnalysisDetailEvent.GetAnalysisById(analysisId))
    }

    AnalysisDetailContent(state = state)
}

@Preview
@Composable
private fun PreviewAnalysisScreen() {
    ChatlyzerTheme {
        AnalysisDetailScreen("")
    }
}