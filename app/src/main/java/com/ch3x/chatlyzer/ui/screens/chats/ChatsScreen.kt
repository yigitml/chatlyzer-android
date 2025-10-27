package com.ch3x.chatlyzer.ui.screens.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch3x.chatlyzer.ui.screens.chats.components.ChatsContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun ChatsScreen(
    viewModel: ChatsViewModel = hiltViewModel(),
    onNavigateAnalysis: (String) -> Unit,
    onCreateChat: () -> Unit = {}
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ChatsEvent.GetAllChats)
    }

    ChatsContent(
        state = state,
        onNavigateAnalysis = onNavigateAnalysis,
        onCreateChat = onCreateChat,
        onDelete = { chatId -> viewModel.onEvent(ChatsEvent.DeleteChat(chatId)) },
        modifier = Modifier
    )
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