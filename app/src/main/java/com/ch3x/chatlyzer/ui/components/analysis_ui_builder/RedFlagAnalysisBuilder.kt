package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.gson.JsonObject

class RedFlagAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val flags = data.getAsJsonArray("flags")
        
        LazyColumn(
            modifier = Modifier.padding(AnalysisLayoutDirectives.SCREEN_PADDING),
            verticalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.SECTION_SPACING)
        ) {
            // Header Section
            item {
                SectionTitle("🚩 Red Flag Analysis")
                Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            }
            
            item {
                MetricCard(
                    title = "Red Flags Detected",
                    value = flags?.size()?.toString() ?: "0",
                    icon = "🚩"
                )
            }
            
            // Flags Section
            flags?.let { flagsArray ->
                items(flagsArray.size()) { index ->
                    val flag = flagsArray[index].asJsonObject
                    
                    WarningCard(
                        title = flag.get("label")?.asString ?: "Unknown Flag",
                        description = flag.get("explanation")?.asString ?: "",
                        severity = flag.get("severity")?.asFloat ?: 0f,
                        examples = flag.getAsJsonArray("messageRefs")?.let { refs ->
                            (0 until refs.size()).map { i ->
                                val ref = refs[i].asJsonObject
                                MessageExample(
                                    sender = if (ref.get("sender")?.asString == "user") "You" else "Other",
                                    content = ref.get("contentSnippet")?.asString ?: "",
                                    timestamp = AnalysisUtils.formatDate(ref.get("timestamp")?.asString ?: "")
                                )
                            }
                        } ?: emptyList(),
                        warningColor = Color.Red
                    )
                }
            }
        }
    }
} 