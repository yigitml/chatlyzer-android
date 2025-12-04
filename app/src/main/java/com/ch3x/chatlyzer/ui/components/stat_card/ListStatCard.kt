package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListStatCard(
    title: String,
    items: List<String>,
    icon: String,
    maxItemsToShow: Int = 5
) {
    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisLayoutDirectives.CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            items.take(maxItemsToShow).forEach { item ->
                Text(
                    text = "• $item",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 2.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (items.size > maxItemsToShow) {
                Text(
                    text = "+${items.size - maxItemsToShow} more",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    color = com.ch3x.chatlyzer.ui.theme.GoldenYellow
                )
            }
        }
    }
} 