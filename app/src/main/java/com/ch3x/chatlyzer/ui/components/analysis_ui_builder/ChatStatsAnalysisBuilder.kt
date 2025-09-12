package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ch3x.chatlyzer.ui.components.stat_card.*
import com.google.gson.JsonObject
import java.util.Locale

class ChatStatsAnalysisBuilder {
    
    @Composable
    fun Build(data: JsonObject) {
        // Extract data
        val totals = data.getAsJsonObject("totals")
        val vibeBalance = data.getAsJsonObject("vibeBalance")
        val emojiUsage = data.getAsJsonArray("emojiUsage")
        val avgResponseTime = data.getAsJsonArray("avgResponseTime")
        val userRoles = data.getAsJsonArray("userRoles")
        val initiatorStats = data.getAsJsonObject("initiatorStats")
        val deepMoments = data.getAsJsonArray("deepMoments")
        val emojiPersona = data.getAsJsonArray("emojiPersona")
        val chatStreak = data.getAsJsonObject("chatStreak")
        val conversationPhases = data.getAsJsonArray("conversationPhases")
        val vibeTimeline = data.getAsJsonArray("vibeTimeline")
        
        LazyColumn(
            modifier = Modifier.padding(AnalysisLayoutDirectives.SCREEN_PADDING),
            verticalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.SECTION_SPACING)
        ) {
            // Overview Section
            item { 
                SectionTitle("📊 Overview")
                Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.CONTENT_SPACING)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Messages",
                        value = totals?.get("messageCount")?.asInt?.toString() ?: "0",
                        icon = "💬"
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Words",
                        value = totals?.get("wordCount")?.asInt?.toString() ?: "0",
                        icon = "📝"
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Emojis",
                        value = totals?.get("emojiCount")?.asInt?.toString() ?: "0",
                        icon = "😊"
                    )
                }
            }
            
            // Balance Section
            item { 
                SectionTitle("⚖️ Conversation Balance")
                Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            }
            
            vibeBalance?.let { vibe ->
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.CONTENT_SPACING)) {
                        ScoreCard(
                            title = "Mutual Effort",
                            score = vibe.get("mutualEffortScore")?.asFloat ?: 0f,
                            maxScore = 10f,
                            description = "Conversation energy balance"
                        )
                        ScoreCard(
                            title = "Emotional Balance",
                            score = vibe.get("emotionalBalance")?.asFloat ?: 0f,
                            maxScore = 10f,
                            description = "Emotional sharing balance"
                        )
                        ScoreCard(
                            title = "Engagement Level",
                            score = vibe.get("dryVsJuicyRatio")?.asFloat ?: 0f,
                            maxScore = 100f,
                            description = "% of engaging messages",
                            isPercentage = true
                        )
                    }
                }
            }
            
            // User Comparison Section
            item { 
                SectionTitle("👥 User Activity")
                Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            }
            
            totals?.let { t ->
                val messagesArray = t.getAsJsonArray("messagesPerUser")
                val wordsArray = t.getAsJsonArray("wordsPerUser")
                
                if (messagesArray?.size()!! >= 2 && wordsArray?.size()!! >= 2) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.CONTENT_SPACING)) {
                            UserComparisonCard(
                                title = "Messages Sent",
                                user1Name = messagesArray[0].asJsonObject.get("username")?.asString ?: "User 1",
                                user1Value = (messagesArray[0].asJsonObject.get("messageCount")?.asInt ?: 0).toString(),
                                user2Name = messagesArray[1].asJsonObject.get("username")?.asString ?: "User 2",
                                user2Value = (messagesArray[1].asJsonObject.get("messageCount")?.asInt ?: 0).toString(),
                                icon = "💬"
                            )
                            UserComparisonCard(
                                title = "Words Written",
                                    user1Name = wordsArray[0].asJsonObject.get("username")?.asString ?: "User 1",
                                    user1Value = (wordsArray[0].asJsonObject.get("wordCount")?.asInt ?: 0).toString(),
                                    user2Name = wordsArray[1].asJsonObject.get("username")?.asString ?: "User 2",
                                    user2Value = (wordsArray[1].asJsonObject.get("wordCount")?.asInt ?: 0).toString(),
                                icon = "📝"
                            )
                        }
                    }
                }
            }
            
            // Behavior Insights Section
            item { 
                SectionTitle("🎭 Behavior Insights")
                Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            }
            
            userRoles?.let { roles ->
                item {
                    InsightCard(
                        title = "User Personalities",
                        items = (0 until roles.size()).map { i ->
                            val role = roles[i].asJsonObject
                            "${role.get("username")?.asString}: ${role.get("role")?.asString?.replaceFirstChar { it.uppercase() }}"
                        },
                        icon = "🎭"
                    )
                }
            }
            
            initiatorStats?.let { stats ->
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AnalysisLayoutDirectives.CONTENT_SPACING)
                    ) {
                        InfoCard(
                            modifier = Modifier.weight(1f),
                            title = "Conversation Starter",
                            value = stats.get("mostLikelyToStartConvo")?.asString ?: "Unknown",
                            icon = "🚀"
                        )
                        InfoCard(
                            modifier = Modifier.weight(1f),
                            title = "Gets Ghosted Most",
                            value = stats.get("mostGhosted")?.asString ?: "Unknown",
                            icon = "👻"
                        )
                    }
                }
            }
            
            // Timeline Section
            conversationPhases?.let { phases ->
                item { 
                    SectionTitle("📈 Conversation Journey")
                    Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
                }
                
                item {
                    TimelineCard(
                        phases = (0 until phases.size()).map { i ->
                            val phase = phases[i].asJsonObject
                            ConversationPhase(
                                name = phase.get("phase")?.asString?.replaceFirstChar { it.uppercase() } ?: "Unknown",
                                startDate = AnalysisUtils.formatDate(phase.get("start")?.asString ?: ""),
                                endDate = AnalysisUtils.formatDate(phase.get("end")?.asString ?: ""),
                                emoji = AnalysisUtils.getPhaseEmoji(phase.get("phase")?.asString ?: "")
                            )
                        }
                    )
                }
            }
        }
    }
} 