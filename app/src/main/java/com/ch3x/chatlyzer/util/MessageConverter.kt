package com.ch3x.chatlyzer.util

import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class ParsedMessage(
    val sender: String,
    val content: String,
    val timestamp: Date,
    val metadata: Map<String, Any>? = null
)

enum class ChatPlatform(val value: String) {
    WHATSAPP("whatsapp"),
    INSTAGRAM("instagram"),
    TELEGRAM("telegram"),
    DISCORD("discord"),
    GENERIC("generic")
}

abstract class MessageConverter {
    abstract val platform: ChatPlatform
    abstract fun parseMessages(rawText: String): List<ParsedMessage>

    fun convertToMessages(parsedMessages: List<ParsedMessage>): List<ParsedMessage> {
        return parsedMessages.map { msg ->
            ParsedMessage(
                sender = msg.sender,
                content = msg.content,
                timestamp = msg.timestamp,
                metadata = msg.metadata
            )
        }
    }
}

class WhatsAppConverter : MessageConverter() {
    override val platform = ChatPlatform.WHATSAPP

    companion object {
        // WhatsApp date+time formats for exported messages
        // Old format: 31.12.23, 23:59 - Sender: Message
        private val MESSAGE_HEADER_OLD = Regex("""^(\d{1,2}\.\d{1,2}\.\d{2,4}), (\d{2}:\d{2}) - (.*)$""")
        // New format: [31.12.2023, 23:59:59] Sender: Message
        private val MESSAGE_HEADER_NEW = Regex("""^\[(\d{1,2}\.\d{1,2}\.\d{4}), (\d{2}:\d{2}:\d{2})\] (.*)$""")

        // System messages (tr, en, de)
        val SYSTEM_MESSAGES = listOf(
            // English
            "Messages and calls are end-to-end encrypted.",
            "You joined using an invite link",
            "You're now an admin",
            "You created this group",
            "You were added",
            "You added",
            "You removed",
            "You left",
            "You changed the group description",
            "You changed the group name",
            "You changed the group icon",
            "This message was deleted",
            "Missed voice call",
            "Missed video call",

            // Turkish
            "Mesajlar ve aramalar uçtan uca şifrelenmiştir.",
            "Bir davet bağlantısı kullanarak katıldınız",
            "Artık bir yöneticisiniz",
            "Bu grubu sen oluşturdun",
            "Eklendiniz",
            "Eklediniz",
            "Çıkardınız",
            "Gruptan ayrıldınız",
            "Grup açıklamasını değiştirdiniz",
            "Grup adını değiştirdiniz",
            "Grup simgesini değiştirdiniz",
            "Bu mesaj silindi",
            "Cevapsız sesli arama",
            "Cevapsız görüntülü arama",

            // German
            "Nachrichten und Anrufe sind Ende-zu-Ende-verschlüsselt.",
            "Du bist über einen Einladungslink beigetreten",
            "Du bist jetzt Admin",
            "Du hast diese Gruppe erstellt",
            "Du wurdest hinzugefügt",
            "Du hast hinzugefügt",
            "Du hast entfernt",
            "Du hast die Gruppe verlassen",
            "Du hast die Gruppenbeschreibung geändert",
            "Du hast den Gruppennamen geändert",
            "Du hast das Gruppenbild geändert",
            "Diese Nachricht wurde gelöscht",
            "Verpasster Sprachanruf",
            "Verpasster Videoanruf"
        )
    }

    override fun parseMessages(rawText: String): List<ParsedMessage> {
        val lines = rawText.lines()
        val messages = mutableListOf<ParsedMessage>()
        var currentMessage: StringBuilder? = null
        var currentSender: String? = null
        var currentTimestamp: Date? = null

        for (line in lines) {
            // Try both WhatsApp formats
            var match = MESSAGE_HEADER_NEW.find(line)
            var isNewFormat = true

            if (match == null) {
                match = MESSAGE_HEADER_OLD.find(line)
                isNewFormat = false
            }

            if (match != null) {
                // Finalize previous message if exists
                if (currentSender != null && currentMessage != null && currentTimestamp != null) {
                    messages.add(
                        ParsedMessage(
                            sender = currentSender,
                            content = currentMessage.toString().trim(),
                            timestamp = currentTimestamp,
                            metadata = mapOf("platform" to platform.value)
                        )
                    )
                }

                val (dateStr, timeStr, rest) = match.destructured
                val timestamp = parseTimestamp(dateStr, timeStr, isNewFormat) ?: continue

                val colonIndex = rest.indexOf(":")

                if (colonIndex > 0) {
                    val sender = rest.substring(0, colonIndex).trim()
                    val content = rest.substring(colonIndex + 1).trim()

                    if (isSystemMessage(content)) {
                        messages.add(
                            ParsedMessage(
                                sender = "System",
                                content = content,
                                timestamp = timestamp,
                                metadata = mapOf(
                                    "platform" to platform.value,
                                    "messageType" to "system"
                                )
                            )
                        )
                        currentSender = null
                        currentMessage = null
                        currentTimestamp = null
                        continue
                    }

                    currentSender = sender
                    currentTimestamp = timestamp
                    currentMessage = StringBuilder(content)
                } else {
                    // System message without colon
                    if (isSystemMessage(rest)) {
                        messages.add(
                            ParsedMessage(
                                sender = "System",
                                content = rest.trim(),
                                timestamp = timestamp,
                                metadata = mapOf(
                                    "platform" to platform.value,
                                    "messageType" to "system"
                                )
                            )
                        )
                        currentSender = null
                        currentMessage = null
                        currentTimestamp = null
                    } else {
                        // Unexpected format, skip
                        currentSender = null
                        currentMessage = null
                        currentTimestamp = null
                    }
                }
            } else {
                // Continuation of multi-line message
                currentMessage?.append("\n$line")
            }
        }

        // Final flush
        if (currentSender != null && currentMessage != null && currentTimestamp != null) {
            messages.add(
                ParsedMessage(
                    sender = currentSender,
                    content = currentMessage.toString().trim(),
                    timestamp = currentTimestamp,
                    metadata = mapOf("platform" to platform.value)
                )
            )
        }

        return messages
    }

    private fun parseTimestamp(dateStr: String, timeStr: String, hasSeconds: Boolean = false): Date? {
        try {
            // Parse based on the format pattern
            val dateParts = dateStr.split('.')
            if (dateParts.size != 3) return null
            
            val day = dateParts[0]
            val month = dateParts[1]
            val year = dateParts[2]
            
            val timeParts = timeStr.split(':')
            if (timeParts.size < 2) return null
            
            val hours = timeParts[0]
            val minutes = timeParts[1]
            val seconds = if (hasSeconds && timeParts.size > 2) timeParts[2] else "0"

            var fullYear: Int
            if (year.length == 2) {
                val yearNum = year.toInt()
                // Assume 20xx for years 00-50, 19xx for years 51-99
                fullYear = if (yearNum <= 50) 2000 + yearNum else 1900 + yearNum
            } else {
                fullYear = year.toInt()
            }

            val formattedDateStr = "$day.$month.$fullYear $hours:$minutes:$seconds"
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.parse(formattedDateStr)

        } catch (error: Exception) {
            println("Failed to parse WhatsApp timestamp: $dateStr $timeStr $error")
            return null
        }
    }

    private fun isSystemMessage(content: String): Boolean {
        return SYSTEM_MESSAGES.any { content.startsWith(it) }
    }
}

class InstagramConverter : MessageConverter() {
    override val platform = ChatPlatform.INSTAGRAM

    override fun parseMessages(rawText: String): List<ParsedMessage> {
        val messages = mutableListOf<ParsedMessage>()

        try {
            val jsonArray = JSONArray(rawText)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                val sender = obj.optString("sender_name", "Unknown")
                val timestampMs = obj.optLong("timestamp_ms", -1L)
                val content = obj.optString("content", "").trim()
                val type = obj.optString("type", "Generic")

                if (timestampMs <= 0) continue

                val timestamp = Date(timestampMs)

                // Handle possible system messages
                if (type != "Generic" || isSystemMessage(content)) {
                    messages.add(
                        ParsedMessage(
                            sender = "System",
                            content = content,
                            timestamp = timestamp,
                            metadata = mapOf(
                                "platform" to platform.value,
                                "messageType" to "system",
                                "originalSender" to sender,
                                "type" to type
                            )
                        )
                    )
                    continue
                }

                if (content.isNotEmpty()) {
                    messages.add(
                        ParsedMessage(
                            sender = sender,
                            content = content,
                            timestamp = timestamp,
                            metadata = mapOf("platform" to platform.value)
                        )
                    )
                }
            }

        } catch (e: Exception) {
            println("Failed to parse Instagram messages: ${e.message}")
        }

        return messages
    }

    private fun isSystemMessage(content: String): Boolean {
        val systemKeywords = listOf(
            "unsent a message",
            "missed a video call",
            "missed a call",
            "created group",
            "added you to the group"
        )
        return systemKeywords.any { content.contains(it, ignoreCase = true) }
    }
}

class TelegramConverter : MessageConverter() {
    override val platform = ChatPlatform.TELEGRAM

    override fun parseMessages(rawText: String): List<ParsedMessage> {
        val lines = rawText.lines().filter { it.trim().isNotEmpty() }
        val messages = mutableListOf<ParsedMessage>()

        // Telegram pattern: [DD.MM.YYYY HH:MM:SS] Sender: Message
        val messagePattern = Regex("""^\[(\d{2}\.\d{2}\.\d{4})\s(\d{2}:\d{2}:\d{2})\]\s(.+)$""")

        for (line in lines) {
            val match = messagePattern.find(line) ?: continue

            val (dateStr, timeStr, content) = match.destructured

            // Parse timestamp
            val timestamp = parseTelegramTimestamp(dateStr, timeStr) ?: continue

            val colonIndex = content.indexOf(':')

            if (colonIndex > 0) {
                val sender = content.substring(0, colonIndex).trim()
                val messageContent = content.substring(colonIndex + 1).trim()

                if (messageContent.isNotEmpty()) {
                    messages.add(
                        ParsedMessage(
                            sender = sender,
                            content = messageContent,
                            timestamp = timestamp,
                            metadata = mapOf("platform" to platform.value)
                        )
                    )
                }
            }
        }

        return messages
    }

    private fun parseTelegramTimestamp(dateStr: String, timeStr: String): Date? {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.parse("$dateStr $timeStr")
        } catch (error: Exception) {
            println("Failed to parse Telegram timestamp: $dateStr $timeStr $error")
            return null
        }
    }
}

class DiscordConverter : MessageConverter() {
    override val platform = ChatPlatform.DISCORD

    override fun parseMessages(rawText: String): List<ParsedMessage> {
        val lines = rawText.lines().filter { it.trim().isNotEmpty() }
        val messages = mutableListOf<ParsedMessage>()

        // Discord pattern: [DD-Mon-YY HH:MM:SS] Sender: Message
        val messagePattern = Regex("""^\[(\d{2}-\w{3}-\d{2})\s(\d{2}:\d{2}:\d{2})\]\s(.+)$""")

        for (line in lines) {
            val match = messagePattern.find(line) ?: continue

            val (dateStr, timeStr, content) = match.destructured

            // Parse timestamp
            val timestamp = parseDiscordTimestamp(dateStr, timeStr) ?: continue

            val colonIndex = content.indexOf(':')

            if (colonIndex > 0) {
                val sender = content.substring(0, colonIndex).trim()
                val messageContent = content.substring(colonIndex + 1).trim()

                if (messageContent.isNotEmpty()) {
                    messages.add(
                        ParsedMessage(
                            sender = sender,
                            content = messageContent,
                            timestamp = timestamp,
                            metadata = mapOf("platform" to platform.value)
                        )
                    )
                }
            }
        }

        return messages
    }

    private fun parseDiscordTimestamp(dateStr: String, timeStr: String): Date? {
        try {
            // Parse format like "31-Dec-23"
            // Note: SimpleDateFormat "dd-MMM-yy" might need English locale for Month names
            val sdf = SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.ENGLISH)
            sdf.timeZone = TimeZone.getDefault()
            return sdf.parse("$dateStr $timeStr")
        } catch (error: Exception) {
            println("Failed to parse Discord timestamp: $dateStr $timeStr $error")
            return null
        }
    }
}

class GenericConverter : MessageConverter() {
    override val platform = ChatPlatform.GENERIC

    override fun parseMessages(rawText: String): List<ParsedMessage> {
        val lines = rawText.lines().filter { it.trim().isNotEmpty() }
        val messages = mutableListOf<ParsedMessage>()

        for (line in lines) {
            // Simple format: "sender: message" or just "message"
            val colonIndex = line.indexOf(':')

            if (colonIndex > 0) {
                val sender = line.substring(0, colonIndex).trim()
                val content = line.substring(colonIndex + 1).trim()

                if (content.isNotEmpty()) {
                    messages.add(
                        ParsedMessage(
                            sender = sender,
                            content = content,
                            timestamp = Date(), // Use current time as fallback
                            metadata = mapOf("platform" to platform.value)
                        )
                    )
                }
            } else if (line.trim().isNotEmpty()) {
                messages.add(
                    ParsedMessage(
                        sender = "Unknown",
                        content = line.trim(),
                        timestamp = Date(),
                        metadata = mapOf("platform" to platform.value)
                    )
                )
            }
        }

        return messages
    }
}

object MessageConverterFactory {
    private val converters = mapOf(
        ChatPlatform.WHATSAPP to WhatsAppConverter(),
        ChatPlatform.INSTAGRAM to InstagramConverter(),
        ChatPlatform.TELEGRAM to TelegramConverter(),
        ChatPlatform.DISCORD to DiscordConverter(),
        ChatPlatform.GENERIC to GenericConverter()
    )

    fun getConverter(platform: ChatPlatform): MessageConverter {
        return converters[platform]
            ?: throw IllegalArgumentException("Unsupported platform: $platform")
    }

    fun detectPlatform(rawText: String): ChatPlatform {
        // Auto-detect platform based on content patterns

        // WhatsApp patterns:
        // Old format: DD.MM.YY, HH:MM -
        // New format: [DD.MM.YYYY, HH:MM:SS]
        if (Regex("""\d{1,2}\.\d{1,2}\.\d{2,4},\s\d{2}:\d{2}\s-\s""").containsMatchIn(rawText) ||
            Regex("""\[\d{1,2}\.\d{1,2}\.\d{4},\s\d{2}:\d{2}:\d{2}\]""").containsMatchIn(rawText)) {
            return ChatPlatform.WHATSAPP
        }

        // Instagram: Try to parse as JSON array
        try {
            val parsed = JSONArray(rawText)
            if (parsed.length() > 0) {
                val obj = parsed.optJSONObject(0)
                if (obj != null && obj.has("sender_name")) {
                    return ChatPlatform.INSTAGRAM
                }
            }
        } catch (_: Exception) {
            // Not valid JSON, continue with other checks
        }

        // Telegram pattern: [DD.MM.YYYY HH:MM:SS]
        if (Regex("""\[\d{2}\.\d{2}\.\d{4}\s\d{2}:\d{2}:\d{2}\]""").containsMatchIn(rawText)) {
            return ChatPlatform.TELEGRAM
        }

        // Discord pattern: [DD-Mon-YY HH:MM:SS]
        if (Regex("""\[\d{2}-\w{3}-\d{2}\s\d{2}:\d{2}:\d{2}\]""").containsMatchIn(rawText)) {
            return ChatPlatform.DISCORD
        }
        
        // Default to Generic if nothing else matches but there is content
        if (rawText.isNotBlank()) {
             return ChatPlatform.GENERIC
        }

        throw Exception("Platform couldn't be identified")
    }

    fun convertMessages(
        rawText: String,
        platform: ChatPlatform? = null
    ): List<ParsedMessage> {
        val detectedPlatform = platform ?: detectPlatform(rawText)
        val converter = getConverter(detectedPlatform)
        val parsedMessages = converter.parseMessages(rawText)
        return converter.convertToMessages(parsedMessages)
    }
}