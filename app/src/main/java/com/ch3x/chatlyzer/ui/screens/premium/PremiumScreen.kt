package com.ch3x.chatlyzer.ui.screens.premium

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ch3x.chatlyzer.ui.screens.premium.components.PremiumContent
import com.ch3x.chatlyzer.ui.theme.ChatlyzerTheme

@Composable
fun PremiumScreen(
    onUnlockClick: () -> Unit = {},
    onRestorePurchase: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {}
) {
    PremiumContent(
        onUnlockClick = onUnlockClick,
        onRestorePurchase = onRestorePurchase,
        onTermsClick = onTermsClick,
        onPrivacyClick = onPrivacyClick
    )
}

@Preview
@Composable
private fun PreviewPremiumScreen() {
    ChatlyzerTheme {
        PremiumScreen()
    }
}