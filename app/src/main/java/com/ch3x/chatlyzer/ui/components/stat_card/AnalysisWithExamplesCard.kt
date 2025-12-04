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
import androidx.compose.ui.graphics.Color
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
    com.ch3x.chatlyzer.ui.components.GlassCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(com.ch3x.chatlyzer.ui.components.analysis_ui_builder.AnalysisLayoutDirectives.CARD_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = icon,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                score?.let {
                    Text(
                        text = "%.1f/%.0f".format(it, maxScore),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = com.ch3x.chatlyzer.ui.theme.GoldenYellow
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            if (examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "📝 Examples from your chat:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
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
                        modifier = Modifier.padding(start = 8.dp),
                        color = com.ch3x.chatlyzer.ui.theme.GoldenYellow
                    )
                }
            }
        }
    }
}