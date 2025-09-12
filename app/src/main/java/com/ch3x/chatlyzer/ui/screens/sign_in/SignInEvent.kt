package com.ch3x.chatlyzer.ui.screens.sign_in

import android.content.Context

sealed class SignInEvent {
    data class SignInWithGoogle(val context: Context) : SignInEvent()
}