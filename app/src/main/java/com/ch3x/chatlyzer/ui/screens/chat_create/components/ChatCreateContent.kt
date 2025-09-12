package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateEvent
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateState

@Composable
fun ChatCreateContent(
    state: ChatCreateState,
    onEvent: (ChatCreateEvent) -> Unit,
    onImportFile: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Import progress section
        if (state.isImporting) {
            item {
                ImportProgressSection(progress = state.importProgress)
            }
        }

        // Imported file info section
        state.importedFileInfo?.let { fileInfo ->
            item {
                ImportedFileInfoSection(
                    fileInfo = fileInfo,
                    onClearImport = { onEvent(ChatCreateEvent.ClearImportedData) },
                    onChangePlatform = { onEvent(ChatCreateEvent.ShowPlatformSelector) }
                )
            }
        }

        // Chat title section
        item {
            ChatTitleSection(
                title = state.title,
                onTitleChange = { onEvent(ChatCreateEvent.UpdateTitle(it)) }
            )
        }

        // Messages section
        item {
            MessagesSection(
                messages = state.messages,
                onImportFile = onImportFile
            )
        }
    }
} 