package com.ch3x.chatlyzer.ui.screens.premium.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaginationDots(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) Color.White else Color.Gray
                    )
            )
            
            if (index < totalPages - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
} 