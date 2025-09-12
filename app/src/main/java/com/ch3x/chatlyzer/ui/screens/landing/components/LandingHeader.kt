package com.ch3x.chatlyzer.ui.screens.landing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.theme.Pink

@Composable
fun LandingHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Chatlyzer AI",
            fontSize = 36.sp,
            fontWeight = FontWeight.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Analyze Their Red Flags",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
} 