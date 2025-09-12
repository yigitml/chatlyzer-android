package com.ch3x.chatlyzer.ui.screens.chats.components

import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.util.DateUtils

object ChatUtils {
    
    fun getChatInitials(chat: Chat): String {
        return when {
            chat.title?.isNotBlank() == true -> {
                val words = chat.title.trim().split(" ")
                if (words.size >= 2) {
                    "${words[0].first()}${words[1].first()}".uppercase()
                } else {
                    chat.title.take(2).uppercase()
                }
            }
            chat.participants.isNotEmpty() -> {
                val firstParticipant = chat.participants.first()
                val words = firstParticipant.split(" ")
                if (words.size >= 2) {
                    "${words[0].first()}${words[1].first()}".uppercase()
                } else {
                    firstParticipant.take(2).uppercase()
                }
            }
            else -> "WH" // WhatsApp default
        }
    }

    fun getFormattedParticipants(participants: List<String>): String {
        return when {
            participants.isEmpty() -> "No participants"
            participants.size == 1 -> participants[0]
            participants.size == 2 -> "${participants[0]} and ${participants[1]}"
            else -> "${participants[0]}, ${participants[1]} and ${participants.size - 2} more"
        }
    }
    
    // Delegate to DateUtils
    fun formatDate(date: java.util.Date) = DateUtils.formatDate(date)
    fun formatDateTime(date: java.util.Date) = DateUtils.formatDateTime(date)
} 