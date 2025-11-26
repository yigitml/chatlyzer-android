package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.*
import com.google.gson.JsonObject

class MainCharacterEnergyAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val mceScore = data.get("mceScore")?.asFloat ?: 0f
        val traits = data.getAsJsonArray("traits")
        val standoutMoments = data.getAsJsonArray("standoutMoments")
        
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProgressStatCard(
                title = "Main Character Energy",
                value = mceScore,
                maxValue = 10f,
                subtitle = AnalysisUtils.getMainCharacterLevel(mceScore),
                icon = "⭐",
                progressColor = Color.Yellow
            )

            val traitsList = mutableListOf<String>()
            traits?.let { traitsArray ->
                for (i in 0 until traitsArray.size()) {
                    traitsList.add(traitsArray[i].asString)
                }
            }
            
            val examples = mutableListOf<MessageExample>()
            standoutMoments?.let { moments ->
                for (i in 0 until moments.size()) {
                    val moment = moments[i].asJsonObject
                    examples.add(
                        MessageExample(
                            sender = if (moment.get("sender")?.asString == "user") "You" else "Other",
                            content = moment.get("contentSnippet")?.asString ?: "",
                            timestamp = AnalysisUtils.formatDate(moment.get("timestamp")?.asString ?: ""),
                            contextLabel = "Main character moment"
                        )
                    )
                }
            }
            
            AnalysisWithExamplesCard(
                title = "Main Character Traits",
                description = "Detected traits: ${traitsList.joinToString(", ")}",
                icon = "✨",
                examples = examples,
            )
        }
    }
} 