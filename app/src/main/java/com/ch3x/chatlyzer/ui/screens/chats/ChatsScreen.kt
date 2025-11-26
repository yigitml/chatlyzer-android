package com.ch3x.chatlyzer.ui.screens.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch3x.chatlyzer.ui.screens.chats.components.ChatsContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel = hiltViewModel(),
    onNavigateAnalysis: (String) -> Unit,
    onCreateChat: () -> Unit = {}
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ChatsEvent.GetAllChats)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ChatsEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                else -> {}
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ChatsContent(
            state = state,
            onNavigateAnalysis = onNavigateAnalysis,
            onCreateChat = onCreateChat,
            onDelete = { chatId -> viewModel.onEvent(ChatsEvent.DeleteChat(chatId)) },
            modifier = Modifier
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
private fun PreviewChatsScreen() {
    ChatlyzerTheme {
        ChatsScreen(
            onNavigateAnalysis = {},
            onCreateChat = {

            }
        )
    }
}