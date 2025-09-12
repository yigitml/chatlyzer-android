package com.ch3x.chatlyzer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun StarRating(modifier: Modifier = Modifier, count: Int = 5) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(count) {
            Text(
                text = "⭐",
                fontSize = 24.sp
            )
        }
    }
}