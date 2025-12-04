package com.ch3x.chatlyzer.ui.screens.chat_create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateEvent
import com.ch3x.chatlyzer.ui.screens.chat_create.ChatCreateState

@Composable
fun ChatCreateContent(
    state: ChatCreateState,
    onEvent: (ChatCreateEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding(), // Handle keyboard
        verticalArrangement = Arrangement.spacedBy(24.dp), // Increased spacing for better breathing room
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 100.dp, // Space for FAB
            start = 24.dp,
            end = 24.dp
        )
    ) {
        // 1. Title Section (Top priority)
        item(key = "title") {
            ChatTitleSection(
                title = state.title,
                onTitleChange = { onEvent(ChatCreateEvent.UpdateTitle(it)) }
            )
        }

        // 2. Analysis Type Selector (Core decision)
        item(key = "analysis_type") {
            AnalysisTypeSelector(state.analysisType, onEvent)
        }

        // 3. Messages Preview (Context)
        item(key = "messages") {
            MessagesSection(
                messages = state.messages
            )
        }

        // 4. File Info (Secondary context, moved to bottom)
        state.importedFileInfo?.let { fileInfo ->
            item(key = "file_info") {
                CompactFileInfoSection(
                    fileInfo = fileInfo,
                    onClearImport = { onEvent(ChatCreateEvent.ClearImportedData) },
                    onChangePlatform = { onEvent(ChatCreateEvent.ShowPlatformSelector) }
                )
            }
        }

        if (state.isImporting) {
            item(key = "import_progress") {
                ImportProgressSection(progress = state.importProgress)
            }
        }
    }
} 