package com.ch3x.chatlyzer.ui.screens.introduction

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ch3x.chatlyzer.ui.screens.introduction.components.IntroductionContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun IntroductionScreen(onNavigateToSignInPage: () -> Unit) {
    IntroductionContent(onNavigateToSignInPage = onNavigateToSignInPage)
}

@Preview(showBackground = true)
@Composable
fun IntroductionScreenPreview() {
    ChatlyzerTheme {
        IntroductionScreen {}
    }
}