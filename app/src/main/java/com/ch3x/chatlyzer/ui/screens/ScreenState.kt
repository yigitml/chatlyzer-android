package com.ch3x.chatlyzer.ui.screens

sealed class ScreenState {
    data object Success : ScreenState()
    data object Loading : ScreenState()
    data object Error : ScreenState()
}