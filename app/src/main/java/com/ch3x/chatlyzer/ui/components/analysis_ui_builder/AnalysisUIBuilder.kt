package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.gson.JsonObject
import com.google.gson.JsonParser

enum class AnalysisType(val displayName: String, val emoji: String) {
    CHAT_STATS("Chat Stats", "📊"),
    RED_FLAG("Red Flag", "🚩"),
    GREEN_FLAG("Green Flag", "✅"),
    VIBE_CHECK("Vibe Check", "🔮"),
    SIMP_O_METER("Simp-O-Meter", "💕"),
    GHOST_RISK("Ghost Risk", "👻"),
    MAIN_CHARACTER_ENERGY("Main Character Energy", "⭐"),
    EMOTIONAL_DEPTH("Emotional Depth", "💙");

    companion object {
        fun fromString(value: String): AnalysisType? {
            return when (value.lowercase()) {
                "chatstats", "chat_stats" -> CHAT_STATS
                "redflag", "red_flag" -> RED_FLAG
                "greenflag", "green_flag" -> GREEN_FLAG
                "vibecheck", "vibe_check" -> VIBE_CHECK
                "simpometer", "simp_o_meter" -> SIMP_O_METER
                "ghostrisk", "ghost_risk" -> GHOST_RISK
                "maincharacterenergy", "main_character_energy" -> MAIN_CHARACTER_ENERGY
                "emotionaldepth", "emotional_depth" -> EMOTIONAL_DEPTH
                else -> null
            }
        }
    }
}

class AnalysisUIBuilder {
    
    private val chatStatsBuilder = ChatStatsAnalysisBuilder()
    private val redFlagBuilder = RedFlagAnalysisBuilder()
    private val greenFlagBuilder = GreenFlagAnalysisBuilder()
    private val vibeCheckBuilder = VibeCheckAnalysisBuilder()
    private val simpOMeterBuilder = SimpOMeterAnalysisBuilder()
    private val ghostRiskBuilder = GhostRiskAnalysisBuilder()
    private val mainCharacterEnergyBuilder = MainCharacterEnergyAnalysisBuilder()
    private val emotionalDepthBuilder = EmotionalDepthAnalysisBuilder()
    
    @Composable
    fun BuildAnalysisUI(
        analysisType: AnalysisType,
        resultDataJson: String,
        modifier: Modifier = Modifier
    ) {
        val resultData = try {
            JsonParser.parseString(resultDataJson).asJsonObject
        } catch (e: Exception) {
            // TODO: Handle parsing error
            JsonObject()
        }
        
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnalysisHeader(analysisType)
            
            when (analysisType) {
                AnalysisType.CHAT_STATS -> chatStatsBuilder.Build(resultData)
                AnalysisType.RED_FLAG -> redFlagBuilder.Build(resultData)
                AnalysisType.GREEN_FLAG -> greenFlagBuilder.Build(resultData)
                AnalysisType.VIBE_CHECK -> vibeCheckBuilder.Build(resultData)
                AnalysisType.SIMP_O_METER -> simpOMeterBuilder.Build(resultData)
                AnalysisType.GHOST_RISK -> ghostRiskBuilder.Build(resultData)
                AnalysisType.MAIN_CHARACTER_ENERGY -> mainCharacterEnergyBuilder.Build(resultData)
                AnalysisType.EMOTIONAL_DEPTH -> emotionalDepthBuilder.Build(resultData)
            }
        }
    }

    @Composable
    private fun AnalysisHeader(analysisType: AnalysisType) {
        com.ch3x.chatlyzer.ui.components.GlassCard(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = analysisType.emoji,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = analysisType.displayName,
                    style = androidx.compose.ui.text.TextStyle(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            com.ch3x.chatlyzer.ui.theme.PrimaryGradientColors
                        ),
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
} 