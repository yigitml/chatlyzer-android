package com.ch3x.chatlyzer.data.remote

import com.ch3x.chatlyzer.data.remote.dto.MessageDto
import java.util.Date

data class AuthWebPostRequest(
    val accessToken: String,
    val sessionId: String
)

data class AuthMobilePostRequest(
    val accessToken: String,
    val deviceId: String
)

data class FileGetRequest(
    val id: String? = null
)

data class FilePostRequest(
    val modelId: String,
    val photoCount: Int
)

data class FileDeleteRequest(
    val id: String
)

data class ChatGetRequest(
    val id: String? = null
)

data class ChatPostRequest(
    val title: String,
    val messages: List<MessageDto>? = null
)

data class ChatPutRequest(
    val id: String,
    val title: String? = null
)

data class ChatDeleteRequest(
    val id: String
)

data class AnalysisGetRequest(
    val id: String? = null
)

enum class AnalysisType {
    ChatStats,
    RedFlag,
    GreenFlag,
    VibeCheck,
    SimpOMeter,
    GhostRisk,
    MainCharacterEnergy,
    EmotionalDepth;

    override fun toString(): String {
        return when (this) {
            ChatStats -> "ChatStats"
            RedFlag -> "RedFlag"
            GreenFlag -> "GreenFlag"
            VibeCheck -> "VibeCheck"
            SimpOMeter -> "SimpOMeter"
            GhostRisk -> "GhostRisk"
            MainCharacterEnergy -> "MainCharacterEnergy"
            EmotionalDepth -> "EmotionalDepth"
        }
    }

    companion object {
        fun fromString(value: String): AnalysisType? {
            return when (value) {
                "ChatStats" -> ChatStats
                "RedFlag" -> RedFlag
                "GreenFlag" -> GreenFlag
                "VibeCheck" -> VibeCheck
                "SimpOMeter" -> SimpOMeter
                "GhostRisk" -> GhostRisk
                "MainCharacterEnergy" -> MainCharacterEnergy
                "EmotionalDepth" -> EmotionalDepth
                else -> null
            }
        }
    }
}

data class AnalysisPostRequest(
    val chatId: String,
)

data class AnalysisPutRequest(
    val id: String,
    val result: String
)

data class AnalysisDeleteRequest(
    val id: String
)

data class PrivacyAnalysisPostRequest(
    val title: String,
    val isGhostMode: Boolean,
    val messages: String
) {
    data class Message(
        val sender: String,
        val timestamp: Date? = null,
        val content: String,
        val metadata: Any? = null
    )
}

data class MessageGetRequest(
    val id: String? = null,
    val chatId: String? = null
)

data class MessagePostRequest(
    val content: String,
    val timestamp: Date? = null,
    val sender: String,
    val chatId: String,
    val metadata: Any? = null
)

data class MessagePutRequest(
    val id: String,
    val content: String? = null,
    val metadata: Any? = null
)

data class MessageDeleteRequest(
    val id: String
)

data class SubscriptionGetRequest(
    val id: String? = null
)

data class SubscriptionDeleteRequest(
    val id: String
)

data class UserPostRequest(
    val name: String
)

data class UserPutRequest(
    val name: String? = null,
    val image: String? = null,
    val isOnboarded: Boolean? = null,
    val isFirstModelCreated: Boolean? = null,
    val isTourCompleted: Boolean? = null
)