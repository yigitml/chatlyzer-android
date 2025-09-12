package com.ch3x.chatlyzer.ui.screens.sign_in

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ch3x.chatlyzer.ui.screens.sign_in.components.SignInContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun SignInScreen(
    context: Context = LocalContext.current,
    viewModel: SignInViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val loginState by viewModel.signInState.collectAsStateWithLifecycle()

    SignInContent(
        context = context,
        loginState = loginState,
        onEvent = viewModel::onEvent,
        onLoginSuccess = onLoginSuccess
    )
}

@Preview
@Composable
private fun PreviewSignInScreen() {
    ChatlyzerTheme {
        SignInScreen(
            onLoginSuccess = {}
        )
    }
}