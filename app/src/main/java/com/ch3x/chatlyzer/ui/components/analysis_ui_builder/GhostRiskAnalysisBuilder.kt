package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.*
import com.google.gson.JsonObject
import java.util.Locale

class GhostRiskAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val riskLevel = data.get("riskLevel")?.asString ?: "low"
        val riskScore = data.get("riskScore")?.asFloat ?: 0f
        val signals = data.getAsJsonArray("signals")
        
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProgressStatCard(
                title = "Ghost Risk",
                value = riskScore,
                maxValue = 10f,
                subtitle = "Risk Level: ${riskLevel.capitalize(Locale.ROOT)}",
                icon = "👻",
                progressColor = AnalysisUtils.getGhostRiskColor(riskLevel)
            )

            signals?.let { signalsArray ->
                repeat(signalsArray.size()) { index ->
                    val signal = signalsArray[index].asJsonObject
                    GhostRiskSignalCard(
                        signalType = signal.get("label")?.asString ?: "Unknown Signal",
                        explanation = signal.get("explanation")?.asString ?: "",
                        examples = signal.getAsJsonArray("messageRefs")?.map { ref ->
                            MessageExample(
                                sender = if (ref.asJsonObject.get("sender")?.asString == "user") "You" else "Other",
                                content = ref.asJsonObject.get("contentSnippet")?.asString ?: "",
                                timestamp = AnalysisUtils.formatDate(ref.asJsonObject.get("timestamp")?.asString ?: ""),
                                contextLabel = "Ghost risk signal"
                            )
                        }?.toMutableList() ?: mutableListOf(),
                        riskLevel = riskLevel
                    )
                }
            }
        }
    }
} 