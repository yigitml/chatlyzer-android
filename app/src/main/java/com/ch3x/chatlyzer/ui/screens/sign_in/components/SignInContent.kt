package com.ch3x.chatlyzer.ui.screens.sign_in.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.screens.sign_in.SignInEvent
import com.ch3x.chatlyzer.ui.screens.sign_in.SignInState
import com.ch3x.chatlyzer.ui.components.LoadingOverlay

@Composable
fun SignInContent(
    context: Context,
    loginState: SignInState,
    onEvent: (SignInEvent) -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is SignInState.Success -> onLoginSuccess()
            is SignInState.Error -> {
                snackbarHostState.showSnackbar(
                    message = state.message
                )
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SignInContainer(
                context = context,
                loginState = loginState,
                onEvent = onEvent
            )

            LoadingOverlay(
                isVisible = loginState is SignInState.Loading,
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
} 