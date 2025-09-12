package com.ch3x.chatlyzer.ui.screens.sign_in.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ch3x.chatlyzer.ui.screens.sign_in.SignInEvent
import com.ch3x.chatlyzer.ui.screens.sign_in.SignInState

@Composable
fun SignInContainer(
    context: Context,
    loginState: SignInState,
    onEvent: (SignInEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            GoogleSignInButton(
                onClick = { onEvent(SignInEvent.SignInWithGoogle(context)) },
                isLoading = loginState is SignInState.Loading
            )
        }
    }
} 