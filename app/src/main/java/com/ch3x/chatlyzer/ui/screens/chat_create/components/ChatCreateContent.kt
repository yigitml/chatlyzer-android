package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.ui.screens.chat_create.AnalysisType
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
        if (state.isImporting) {
            item {
                ImportProgressSection(progress = state.importProgress)
            }
        }

        state.importedFileInfo?.let { fileInfo ->
            item {
                ImportedFileInfoSection(
                    fileInfo = fileInfo,
                    onClearImport = { onEvent(ChatCreateEvent.ClearImportedData) },
                    onChangePlatform = { onEvent(ChatCreateEvent.ShowPlatformSelector) }
                )
            }
        }

        item {
            AnalysisTypeSelector(state.analysisType, onEvent)
        }

        item {
            ChatTitleSection(
                title = state.title,
                onTitleChange = { onEvent(ChatCreateEvent.UpdateTitle(it)) }
            )
        }

        item {
            MessagesSection(
                messages = state.messages,
                onImportFile = onImportFile
            )
        }
    }
} 