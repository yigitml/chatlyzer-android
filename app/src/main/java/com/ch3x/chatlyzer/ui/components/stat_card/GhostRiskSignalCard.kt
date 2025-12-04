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
    val riskEmoji = when (riskLevel.lowercase()) {
        "high" -> "🚨"
        "medium" -> "⚠️"
        "low" -> "💭"
        else -> "👻"
    }

    val riskColor = when (riskLevel.lowercase()) {
        "high" -> com.ch3x.chatlyzer.ui.theme.ErrorRed
        "medium" -> com.ch3x.chatlyzer.ui.theme.TealAccent
        "low" -> com.ch3x.chatlyzer.ui.theme.SuccessGreen
        else -> com.ch3x.chatlyzer.ui.theme.TextGray
    }

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
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(4.dp),
                    color = riskColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = riskLevel.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = riskColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = explanation,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = com.ch3x.chatlyzer.ui.theme.TextGray
            )

            if (examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "🔍 Evidence in your chat:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                examples.take(2).forEach { example ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f),
                                shape = RoundedCornerShape(12.dp)
                            )
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
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = example.timestamp,
                                fontSize = 11.sp,
                                color = com.ch3x.chatlyzer.ui.theme.TextGray
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "\"${example.content}\"",
                            fontSize = 13.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = com.ch3x.chatlyzer.ui.theme.TextGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (examples.size > 2) {
                    Text(
                        text = "... and ${examples.size - 2} more similar messages",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = com.ch3x.chatlyzer.ui.theme.GoldenYellow
                    )
                }
            }
        }
    }
}