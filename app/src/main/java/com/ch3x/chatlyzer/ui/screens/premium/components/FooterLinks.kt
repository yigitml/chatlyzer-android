package com.ch3x.chatlyzer.ui.screens.premium.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun FooterLinks(
    onRestorePurchase: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Restore Purchase",
            fontSize = 14.sp,
            modifier = Modifier.clickable { onRestorePurchase() }
        )

        Text(
            text = " • ",
            fontSize = 14.sp,
        )

        Text(
            text = "Terms & Conditions",
            fontSize = 14.sp,
            modifier = Modifier.clickable { onTermsClick() }
        )

        Text(
            text = " • ",
            fontSize = 14.sp,
        )

        Text(
            text = "Privacy Policy",
            fontSize = 14.sp,
            modifier = Modifier.clickable { onPrivacyClick() }
        )
    }
} 