package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.MessageExample

@Composable
fun AnalysisWithExamplesCard(
    title: String,
    description: String,
    score: Float? = null,
    maxScore: Float = 10f,
    icon: String,
    examples: List<MessageExample>,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = icon,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                score?.let {
                    Text(
                        text = "%.1f/%.0f".format(it, maxScore),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            if (examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "📝 Examples from your chat:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                examples.take(2).forEach { example ->
                    MessageRefCard(
                        sender = example.sender,
                        content = example.content,
                        timestamp = example.timestamp,
                      contextLabel = ""
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                }
                
                if (examples.size > 2) {
                    Text(
                        text = "+${examples.size - 2} more examples",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}