package com.ch3x.chatlyzer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.theme.Pink

@Composable
fun PaginationDots(modifier: Modifier = Modifier, currentPage: Int, pageCount: Int = 3) {
    Row(horizontalArrangement = Arrangement.Center) {
        repeat(pageCount) { index ->
            Box(
                modifier = modifier
                    .size(if (index == currentPage) 12.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) Pink else Color.LightGray
                    )
            )
            if (index < pageCount - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}