package com.ch3x.chatlyzer.ui.components.stat_card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.MessageExample

@Composable
fun GhostRiskSignalCard(
    signalType: String,
    explanation: String,
    examples: List<MessageExample>,
    riskLevel: String
) {
    val backgroundColor = when (riskLevel.lowercase()) {
        "high" -> Color(0xFFFFEBEE)
        "medium" -> Color(0xFFFFF3E0)
        "low" -> Color(0xFFE8F5E8)
        else -> Color(0xFFF5F5F5)
    }

    val borderColor = when (riskLevel.lowercase()) {
        "high" -> Color(0xFFE57373)
        "medium" -> Color(0xFFFFB74D)
        "low" -> Color(0xFF81C784)
        else -> Color(0xFFBDBDBD)
    }

    val riskEmoji = when (riskLevel.lowercase()) {
        "high" -> "🚨"
        "medium" -> "⚠️"
        "low" -> "💭"
        else -> "👻"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
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
                        text = riskEmoji,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = signalType,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = riskLevel.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = explanation,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            if (examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "🔍 Evidence in your chat:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )

                Spacer(modifier = Modifier.height(12.dp))

                examples.take(2).forEach { example ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = example.sender,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = example.timestamp,
                                fontSize = 11.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "\"${example.content}\"",
                            fontSize = 13.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (examples.size > 2) {
                    Text(
                        text = "... and ${examples.size - 2} more similar messages",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}