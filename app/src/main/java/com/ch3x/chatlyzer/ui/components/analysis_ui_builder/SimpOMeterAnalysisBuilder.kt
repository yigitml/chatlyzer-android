package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.*
import com.google.gson.JsonObject

class SimpOMeterAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val simpScore = data.get("simpScore")?.asFloat ?: 0f
        val behaviors = data.getAsJsonArray("behaviorsDetected")
        val messageRefs = data.getAsJsonArray("messageRefs")
        
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProgressStatCard(
                title = "Simp Score",
                value = simpScore,
                maxValue = 10f,
                subtitle = AnalysisUtils.getSimpLevel(simpScore),
                icon = "💕",
                progressColor = AnalysisUtils.getSimpColor(simpScore)
            )

            behaviors?.let { behaviorsArray ->
                val behaviorsList = mutableListOf<String>()
                for (i in 0 until behaviorsArray.size()) {
                    behaviorsList.add(behaviorsArray[i].asString)
                }
                ListStatCard(
                    title = "Behaviors Detected",
                    items = behaviorsList,
                    icon = "🎭"
                )
            }

            messageRefs?.let { refs ->
                repeat(refs.size().coerceAtMost(3)) { index ->
                    val ref = refs[index].asJsonObject
                    MessageRefCard(
                        sender = if (ref.get("sender")?.asString == "user") "You" else "Other",
                        content = ref.get("contentSnippet")?.asString ?: "",
                        timestamp = AnalysisUtils.formatDate(ref.get("timestamp")?.asString ?: "")
                    )
                }
            }
        }
    }
} 