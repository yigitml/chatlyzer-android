package com.ch3x.chatlyzer.ui.screens.analyzes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch3x.chatlyzer.ui.screens.analyzes.components.AnalyzesContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun AnalyzesScreen(
    chatId: String? = null,
    viewModel: AnalyzesViewModel = hiltViewModel(),
    onNavigateToAnalysisDetail: (String) -> Unit,
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = chatId) {
        if (chatId != null) {
            viewModel.onEvent(AnalyzesEvent.GetAnalyzesByChatId(chatId))
            viewModel.onEvent(AnalyzesEvent.GetChatById(chatId))
        } else {
            viewModel.onEvent(AnalyzesEvent.GetAllAnalyzes)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnalyzesContent(
            state = state,
            chatId = chatId,
            onNavigateToAnalysisDetail = onNavigateToAnalysisDetail,
            onCreateAnalysis = { if (chatId != null) viewModel.onEvent(AnalyzesEvent.AnalyzeChat(chatId)) },
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun PreviewAnalyzesScreen() {
    ChatlyzerTheme { AnalyzesScreen(
        chatId = null,
        onNavigateToAnalysisDetail = {},
    )
    }
}