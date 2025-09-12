package com.ch3x.chatlyzer.ui.components.analysis_ui_builder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

object AnalysisUtils {

    fun formatDate(isoString: String): String {
        // TODO: There is soooo many date formatters in this app
        // Unify them please! PLEASE
        return isoString.take(10)
    }

    fun getPhaseEmoji(phase: String): String = when (phase.lowercase()) {
        "intro" -> "👋"
        "honeymoon" -> "💕"
        "drift" -> "🌊"
        "real talk" -> "💭"
        "dry spell" -> "🏜️"
        "rekindled" -> "🔥"
        else -> "📅"
    }

    fun getVibeEmoji(vibe: String): String = when (vibe.lowercase()) {
        "positive" -> "😊"
        "flirty" -> "😘"
        "funny" -> "😂"
        "awkward" -> "😬"
        "chaotic" -> "🤪"
        "dry" -> "😐"
        else -> "😐"
    }


    fun getSimpLevel(score: Float): String = when {
        score <= 3f -> "Balanced"
        score <= 6f -> "Mild Simp"
        score <= 8f -> "Major Simp"
        else -> "Ultra Simp"
    }


    fun getSimpColor(score: Float): Color = when {
        score <= 3f -> Color.Green.copy(alpha = 0.2f)
        score <= 6f -> Color.Yellow.copy(alpha = 0.2f)
        score <= 8f -> Color.Cyan.copy(alpha = 0.2f)
        else -> Color.Red.copy(alpha = 0.2f)
    }


    fun getGhostRiskColor(riskLevel: String): Color = when (riskLevel.lowercase()) {
        "low" -> Color.Green.copy(alpha = 0.2f)
        "medium" -> Color.Yellow.copy(alpha = 0.2f)
        "high" -> Color.Red.copy(alpha = 0.2f)
        else -> Color.Gray.copy(alpha = 0.2f)

    }

    fun getMainCharacterLevel(score: Float): String = when {
        score <= 3f -> "Background Character"
        score <= 6f -> "Supporting Character"
        score <= 8f -> "Main Character"
        else -> "The Protagonist"
    }


    fun getEmotionalDepthLevel(score: Float): String = when {
        score <= 3f -> "Surface Level"
        score <= 6f -> "Some Depth"
        score <= 8f -> "Deep Connection"
        else -> "Soul Baring"
    }
}

// Data Classes
data class MessageExample(
    val sender: String,
    val content: String,
    val timestamp: String,
    val contextLabel: String = ""
)

data class UserStat(
    val name: String,
    val value: Int
)

data class ConversationPhase(
    val name: String,
    val startDate: String,
    val endDate: String,
    val emoji: String
)

// Unified Layout Directives
object AnalysisLayoutDirectives {
    // Unified Spacing System
    val SECTION_SPACING = 24.dp
    val CARD_SPACING = 16.dp
    val ITEM_SPACING = 12.dp
    val CONTENT_SPACING = 8.dp
    
    // Unified Padding System
    val SCREEN_PADDING = 16.dp
    val CARD_PADDING = 20.dp
    val CONTENT_PADDING = 12.dp
    
    // Unified Corner Radius
    val CARD_CORNER_RADIUS = 16.dp
    val SMALL_CORNER_RADIUS = 12.dp
    
    // Unified Alpha Values
    val BACKGROUND_ALPHA = 0.08f
    val ACCENT_ALPHA = 0.12f
    val SUBTLE_ALPHA = 0.05f
}

// Unified Component Composables
@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS)
    ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.CONTENT_SPACING))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ScoreCard(
    title: String,
    score: Float,
    maxScore: Float,
    description: String,
    isPercentage: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS)
    ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CARD_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (isPercentage) "${score.toInt()}%" else "${score}/10",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, bottom = AnalysisLayoutDirectives.CONTENT_SPACING)
            )
            
            LinearProgressIndicator(
                progress = score / maxScore,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                trackColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun InsightCard(
    title: String,
    items: List<String>,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS)
    ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CARD_PADDING)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(AnalysisLayoutDirectives.CONTENT_SPACING))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            
            items.forEach { item ->
                Text(
                    text = "• $item",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun WarningCard(
    title: String,
    description: String,
    severity: Float,
    examples: List<MessageExample>,
    warningColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS)
    ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CARD_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "${severity}/10",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            
            Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.CONTENT_SPACING))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
            
            if (examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
                Text(
                    text = "Examples:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                )
                
                examples.take(2).forEach { example ->
                    Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.CONTENT_SPACING))
                    MessageExampleCard(example)
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS)
    ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.CONTENT_SPACING))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TimelineCard(
    phases: List<ConversationPhase>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS)
    ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CARD_PADDING)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📈",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(AnalysisLayoutDirectives.CONTENT_SPACING))
                Text(
                    text = "Conversation Timeline",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.ITEM_SPACING))
            
            phases.forEachIndexed { index, phase ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = phase.emoji,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(AnalysisLayoutDirectives.CONTENT_SPACING))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = phase.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (phase.endDate.isNotEmpty()) "${phase.startDate} - ${phase.endDate}" else phase.startDate,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                
                if (index < phases.size - 1) {
                    Spacer(modifier = Modifier.height(AnalysisLayoutDirectives.CONTENT_SPACING))
                }
            }
        }
    }
}

@Composable
fun MessageExampleCard(
    example: MessageExample,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AnalysisLayoutDirectives.CARD_CORNER_RADIUS),
        ) {
        Column(
            modifier = Modifier.padding(AnalysisLayoutDirectives.CONTENT_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = example.sender,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = example.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Text(
                text = example.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}