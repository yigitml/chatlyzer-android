package com.ch3x.chatlyzer.ui.screens.chat_create

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch3x.chatlyzer.ui.components.LoadingOverlay
import com.ch3x.chatlyzer.ui.components.LoadingState
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.ui.screens.chat_create.components.ChatCreateContent
import com.ch3x.chatlyzer.ui.screens.chat_create.components.CreateChatFab
import com.ch3x.chatlyzer.ui.screens.chat_create.components.PlatformSelectorDialog
import com.ch3x.chatlyzer.ui.components.animations.SuccessAnimation
import com.ch3x.chatlyzer.ui.screens.chat_create.components.ImportScreenContent
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatCreateScreen(
    viewModel: ChatCreateViewModel = hiltViewModel(),
    initialFileUri: String? = null,
    initialFileType: String? = null,
    onChatCreated: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    var showSuccess by remember { mutableStateOf(false) }

    // Handle initial shared data
    LaunchedEffect(initialFileUri, initialFileType) {
        if (initialFileUri != null && initialFileType != null) {
            viewModel.onEvent(ChatCreateEvent.HandleSharedFile(initialFileUri, initialFileType))
        }
    }

    // Handle chat creation success
    LaunchedEffect(state.createdChatId) {
        state.createdChatId?.let { chatId ->
            showSuccess = true
            delay(1500) // Show success animation
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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.data?.let { uri ->
            viewModel.onEvent(ChatCreateEvent.ImportFile(uri))
        }
    }

    // Handle back press for steps
    androidx.activity.compose.BackHandler(enabled = state.step == ChatCreateStep.CONFIGURE && initialFileUri == null) {
        viewModel.onEvent(ChatCreateEvent.SetStep(ChatCreateStep.IMPORT))
    }

    when (state.screenState) {
        ScreenState.Loading -> {
            LoadingState()
        }
        else -> {
            Scaffold(
                floatingActionButton = {
                    if (state.step == ChatCreateStep.CONFIGURE) {
                        CreateChatFab(
                            enabled = state.title.isNotBlank() && state.messages.isNotEmpty(),
                            isCreating = state.isCreating,
                            onClick = { viewModel.onEvent(ChatCreateEvent.CreateChat) }
                        )
                    }
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    AnimatedContent(
                        targetState = state.step,
                        label = "step_transition"
                    ) { targetStep ->
                        when (targetStep) {
                            ChatCreateStep.IMPORT -> {
                                ImportScreenContent(
                                    onImportClick = {
                                        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                                            type = "*/*"
                                            addCategory(Intent.CATEGORY_OPENABLE)
                                            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("text/plain", "application/zip"))
                                        }
                                        launcher.launch(intent)
                                    }
                                )
                            }
                            ChatCreateStep.CONFIGURE -> {
                                ChatCreateContent(
                                    state = state,
                                    onEvent = viewModel::onEvent
                                )
                            }
                        }
                    }

                    LoadingOverlay(
                        isVisible = state.isCreating || state.isImporting
                    )
                }
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

    // Success animation with confetti
    SuccessAnimation(
        show = showSuccess,
        withConfetti = true,
        onComplete = { }
    )
}