package com.ch3x.chatlyzer.ui.screens.analyzes.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.DataUsage
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.ch3x.chatlyzer.util.DateUtils.isSameDay
import com.ch3x.chatlyzer.util.DateUtils.isWithinWeek
import com.ch3x.chatlyzer.util.DateUtils.isWithinYear
import com.ch3x.chatlyzer.util.DateUtils.isYesterday
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object AnalysisTypeUtils {
    
    @Composable
    fun getAnalysisTypeIcon(type: String): ImageVector {
        return when (type.lowercase()) {
            "chat_stats" -> Icons.Outlined.BarChart
            "sentiment" -> Icons.Outlined.Analytics
            else -> Icons.Outlined.DataUsage
        }
    }

    @Composable
    fun getAnalysisTypeColor(type: String): Color {
        return MaterialTheme.colorScheme.primary
        return when (type.lowercase()) {
            "chat_stats" -> MaterialTheme.colorScheme.primary
            "sentiment" -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.tertiary
        }
    }

    fun getAnalysisTypeDisplayName(type: String): String {
        return when (type.lowercase()) {
            "chat_stats" -> "Chat Statistics"
            "red_flag" -> "Red Flags"
            "green_flag" -> "Green Flags"
            "vibe_check" -> "Vibe Check"
            "simp_o_meter" -> "SimpOMeter"
            "ghost_risk" -> "Ghost Risk"
            "main_character_energy" -> "Main Character Energy"
            else -> type.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    fun formatDate(date: Date): String {
        val now = Calendar.getInstance()
        val dateCalendar = Calendar.getInstance().apply { time = date }

        return when {
            isSameDay(now, dateCalendar) -> {
                SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)
            }
            isYesterday(now, dateCalendar) -> {
                "Yesterday"
            }
            isWithinWeek(now, dateCalendar) -> {
                SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
            }
            isWithinYear(now, dateCalendar) -> {
                SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
            }
            else -> {
                SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(date)
            }
        }
    }
}