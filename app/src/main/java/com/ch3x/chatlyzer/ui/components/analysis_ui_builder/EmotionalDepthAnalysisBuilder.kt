package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.AnalysisWithExamplesCard
import com.ch3x.chatlyzer.ui.components.stat_card.ListStatCard
import com.ch3x.chatlyzer.ui.components.stat_card.ProgressStatCard
import com.google.gson.JsonObject

class EmotionalDepthAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val depthScore = data.get("depthScore")?.asFloat ?: 0f
        val vulnerableMoments = data.getAsJsonArray("vulnerableMoments")
        val topicsTouched = data.getAsJsonArray("topicsTouched")
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProgressStatCard(
                    title = "Emotional Depth",
                    value = depthScore,
                    maxValue = 10f,
                    subtitle = AnalysisUtils.getEmotionalDepthLevel(depthScore),
                    icon = "💙",
                    progressColor = Color.Blue
                )
            }

            topicsTouched?.let { topicsArray ->
                item {
                    val topicsList = mutableListOf<String>()
                    for (i in 0 until topicsArray.size()) {
                        topicsList.add(topicsArray[i].asString)
                    }
                    ListStatCard(
                        title = "Deep Topics Discussed",
                        items = topicsList,
                        icon = "💭"
                    )
                }
            }

            vulnerableMoments?.let { momentsArray ->
                items(momentsArray.size()) { index ->
                    val moment = momentsArray[index].asJsonObject
                    val messageRefs = moment.getAsJsonArray("messageRefs")
                    
                    val examples = mutableListOf<MessageExample>()
                    messageRefs?.let { refs ->
                        for (i in 0 until refs.size()) {
                            val ref = refs[i].asJsonObject
                            examples.add(
                                MessageExample(
                                    sender = if (ref.get("sender")?.asString == "user") "You" else "Other",
                                    content = ref.get("contentSnippet")?.asString ?: "",
                                    timestamp = AnalysisUtils.formatDate(ref.get("timestamp")?.asString ?: ""),
                                    contextLabel = "Vulnerable sharing"
                                )
                            )
                        }
                    }
                    
                    AnalysisWithExamplesCard(
                        title = "Vulnerable Moment",
                        description = moment.get("description")?.asString ?: "A moment of emotional openness and vulnerability",
                        icon = "🤗",
                        examples = examples,
                    )
                }
            }
        }
    }
} 