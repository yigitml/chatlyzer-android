package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.R

@Composable
fun SupportedAppsSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Apps Supported:",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        SupportedAppsIcons()

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Over 200+ languages supported",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SupportedAppsIcons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.whatsapp_logo),
            contentDescription = "WhatsApp Logo",
            modifier = Modifier.size(48.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.instagram_logo),
            contentDescription = "Instagram Logo",
            modifier = Modifier.size(48.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.messenger_logo),
            contentDescription = "Messenger Logo",
            modifier = Modifier.size(48.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.discord_logo),
            contentDescription = "Discord Logo",
            modifier = Modifier.size(48.dp)
        )
    }
} 