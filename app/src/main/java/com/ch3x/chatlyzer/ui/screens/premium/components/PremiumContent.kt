package com.ch3x.chatlyzer.ui.screens.premium.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun PremiumContent(
    onUnlockClick: () -> Unit = {},
    onRestorePurchase: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        PremiumHeader()

        Spacer(modifier = Modifier.height(48.dp))

        PreviewCard()

        Spacer(modifier = Modifier.height(16.dp))

        PaginationDots(currentPage = 0, totalPages = 4)

        Spacer(modifier = Modifier.height(16.dp))

        SubscriptionCard()

        Spacer(modifier = Modifier.height(24.dp))

        UnlockButton(onClick = onUnlockClick)

        Spacer(modifier = Modifier.height(16.dp))

        FooterLinks(
            onRestorePurchase = onRestorePurchase,
            onTermsClick = onTermsClick,
            onPrivacyClick = onPrivacyClick
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
} 