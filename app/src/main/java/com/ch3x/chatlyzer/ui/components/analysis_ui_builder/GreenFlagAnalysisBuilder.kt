package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.AnalysisWithExamplesCard
import com.ch3x.chatlyzer.ui.components.analysis_ui_builder.MessageExample
import com.ch3x.chatlyzer.ui.components.stat_card.StatCard
import com.google.gson.JsonObject

class GreenFlagAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val traits = data.getAsJsonArray("traits")
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StatCard(
                    title = "Green Flags Found",
                    value = traits?.size()?.toString() ?: "0",
                    icon = "✅"
                )
            }

            traits?.let { traitsArray ->
                items(traitsArray.size()) { index ->
                    val trait = traitsArray[index].asJsonObject
                    val messageRefs = trait.getAsJsonArray("messageRefs")
                    
                    val examples = mutableListOf<MessageExample>()
                    messageRefs?.let { refs ->
                        for (i in 0 until refs.size()) {
                            val ref = refs[i].asJsonObject
                            examples.add(
                                MessageExample(
                                    sender = if (ref.get("sender")?.asString == "user") "You" else "Other",
                                    content = ref.get("contentSnippet")?.asString ?: "",
                                    timestamp = AnalysisUtils.formatDate(ref.get("timestamp")?.asString ?: ""),
                                    contextLabel = trait.get("label")?.asString ?: "Positive behavior"
                                )
                            )
                        }
                    }
                    
                    AnalysisWithExamplesCard(
                        title = trait.get("label")?.asString ?: "Unknown Trait",
                        description = trait.get("explanation")?.asString ?: "",
                        score = trait.get("positivityScore")?.asFloat,
                        icon = "✅",
                        examples = examples,
                    )
                }
            }
        }
    }
} 