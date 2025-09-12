package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.*
import com.google.gson.JsonObject
import java.util.Locale

class VibeCheckAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val overallVibe = data.get("overallVibe")?.asString ?: "neutral"
        val emojiScore = data.get("emojiScore")?.asFloat ?: 0f
        val humorDetected = data.get("humorDetected")?.asBoolean ?: false
        val keywords = data.getAsJsonArray("keywords")
        val moodDescriptors = data.getAsJsonArray("moodDescriptors")
        val messageRefs = data.getAsJsonArray("messageRefs")
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Overall Vibe",
                        value = overallVibe.capitalize(Locale.ROOT),
                        icon = AnalysisUtils.getVibeEmoji(overallVibe)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Humor Detected",
                        value = if (humorDetected) "Yes" else "No",
                        icon = if (humorDetected) "😂" else "😐"
                    )
                }
            }

            item {
                ProgressStatCard(
                    title = "Emoji Score",
                    value = emojiScore,
                    maxValue = 10f,
                    subtitle = "How expressive is the conversation",
                    icon = "😊",
                    isPercentage = false // This is a 0-10 score
                )
            }

            keywords?.let { keywordsArray ->
                item {
                    val keywordsList = mutableListOf<String>()
                    for (i in 0 until keywordsArray.size()) {
                        keywordsList.add(keywordsArray[i].asString)
                    }
                    ListStatCard(
                        title = "Keywords",
                        items = keywordsList,
                        icon = "🔑"
                    )
                }
            }

            moodDescriptors?.let { moodArray ->
                item {
                    val moodList = mutableListOf<String>()
                    for (i in 0 until moodArray.size()) {
                        moodList.add(moodArray[i].asString)
                    }
                    ListStatCard(
                        title = "Mood Descriptors",
                        items = moodList,
                        icon = "🎭"
                    )
                }
            }

            messageRefs?.let { refs ->
                items(refs.size().coerceAtMost(3)) { index ->
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