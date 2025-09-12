package com.ch3x.chatlyzer.ui.screens.chat_create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch3x.chatlyzer.ui.components.LoadingState
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.ui.screens.chat_create.components.ChatCreateContent
import com.ch3x.chatlyzer.ui.screens.chat_create.components.CreateChatFab
import com.ch3x.chatlyzer.ui.screens.chat_create.components.PlatformSelectorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatCreateScreen(
    viewModel: ChatCreateViewModel = hiltViewModel(),
    initialFileUri: String? = null,
    initialFileType: String? = null,
    onChatCreated: (String) -> Unit,
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle initial shared data
    LaunchedEffect(initialFileUri, initialFileType) {
        if (initialFileUri != null && initialFileType != null) {
            viewModel.onEvent(ChatCreateEvent.HandleSharedFile(initialFileUri, initialFileType))
        }
    }

    // Handle chat creation success
    LaunchedEffect(state.createdChatId) {
        state.createdChatId?.let { chatId ->
            onChatCreated(chatId)
        }
    }

    // Handle error messages
    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = state.errorMessage,
                actionLabel = "Dismiss"
            )
            viewModel.onEvent(ChatCreateEvent.ClearError)
        }
    }

    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onEvent(ChatCreateEvent.ImportFile(it)) }
    }

    when (state.screenState) {
        ScreenState.Loading -> {
            LoadingState()
        }

        else -> {
            Scaffold(
                floatingActionButton = {
                    CreateChatFab(
                        enabled = state.title.isNotBlank() && state.messages.isNotEmpty(),
                        isCreating = state.isCreating,
                        onClick = { viewModel.onEvent(ChatCreateEvent.CreateChat) }
                    )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { paddingValues ->
                ChatCreateContent(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    onEvent = viewModel::onEvent,
                    onImportFile = { filePickerLauncher.launch("text/*") }
                )
            }
        }
    }

    // Platform selector dialog
    if (state.showPlatformSelector) {
        PlatformSelectorDialog(
            selectedPlatform = state.selectedPlatform,
            onPlatformSelected = { platform ->
                viewModel.onEvent(ChatCreateEvent.ConfirmPlatformSelection(platform))
            },
            onDismiss = { viewModel.onEvent(ChatCreateEvent.HidePlatformSelector) }
        )
    }
}

@Preview
@Composable
private fun PreviewChatCreateScreen() {
    ChatCreateScreen(
        onChatCreated = {}
    )
}