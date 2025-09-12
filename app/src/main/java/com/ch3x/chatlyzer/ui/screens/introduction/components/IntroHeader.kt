package com.ch3x.chatlyzer.ui.screens.introduction.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.components.StarRating

@Composable
fun IntroHeader(modifier: Modifier = Modifier) {
    StarRating()

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Uncover Hidden Patterns in Your Conversations.",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
} 