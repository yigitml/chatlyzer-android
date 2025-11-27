package com.ch3x.chatlyzer.ui.screens.chats.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.domain.model.Chat

import androidx.compose.foundation.lazy.itemsIndexed

@Composable
fun ChatList(
    chats: List<Chat>,
    onChatClick: (String) -> Unit,
    onDelete: (String) ->  Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(
            items = chats,
            key = { _, chat -> chat.id },
            contentType = { _, _ -> "chat_item" }
        ) { index, chat ->
            ChatItem(
                chat = chat,
                index = index,
                onChatClick = onChatClick,
                onDelete = onDelete
            )
        }
    }
} 