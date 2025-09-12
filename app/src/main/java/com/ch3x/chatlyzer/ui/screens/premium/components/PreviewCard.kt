package com.ch3x.chatlyzer.ui.screens.premium.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreviewCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.2f))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1.2f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    PreviewCardHeader()

                    Spacer(modifier = Modifier.height(12.dp))

                    PreviewCardTitle()

                    Spacer(modifier = Modifier.height(16.dp))

                    RedFlagsList()
                }
            }
        }
    }
}

@Composable
private fun PreviewCardHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Red Flags",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = " ▶",
            fontSize = 18.sp,
        )
    }
}

@Composable
private fun PreviewCardTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Boyfriend",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
} 