package com.ch3x.chatlyzer.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    
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

    fun formatDateTime(date: Date): String {
        return SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault()).format(date)
    }

    fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterday(cal1: Calendar, cal2: Calendar): Boolean {
        val yesterday = cal1.clone() as Calendar
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        return isSameDay(yesterday, cal2)
    }

    fun isWithinWeek(cal1: Calendar, cal2: Calendar): Boolean {
        val weekAgo = cal1.clone() as Calendar
        weekAgo.add(Calendar.DAY_OF_YEAR, -7)
        return cal2.after(weekAgo)
    }

    fun isWithinYear(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }
} 