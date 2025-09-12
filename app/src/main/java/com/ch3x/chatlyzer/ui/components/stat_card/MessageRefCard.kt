package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageRefCard(
    sender: String,
    content: String,
    timestamp: String,
    contextLabel: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (contextLabel.isNotEmpty()) {
            Text(
                text = "💡 Example of: $contextLabel",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sender,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = timestamp,
                fontSize = 12.sp,
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
} 