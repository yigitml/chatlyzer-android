package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.gson.JsonObject

class RedFlagAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        val flags = data.getAsJsonArray("flags")
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.SECTION_SPACING)
        ) {

            
            MetricCard(
                title = "Red Flags Detected",
                value = flags?.size()?.toString() ?: "0",
                icon = "🚩"
            )
            
            // Flags Section
            flags?.let { flagsArray ->
                repeat(flagsArray.size()) { index ->
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