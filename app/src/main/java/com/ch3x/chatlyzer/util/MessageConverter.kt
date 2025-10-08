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
    INSTAGRAM("instagram")
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
        // WhatsApp date+time format for exported messages like: 31.12.23, 23:59 - Sender: Message
        private val MESSAGE_HEADER = Regex("""^(\d{1,2}\.\d{1,2}\.\d{2,4}), (\d{2}:\d{2}) - (.*)""")

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
            val match = MESSAGE_HEADER.find(line)

            if (match != null) {
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
                val timestamp = parseTimestamp(dateStr, timeStr) ?: continue
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
                        // Unexpected format, skip or log
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

    private fun parseTimestamp(dateStr: String, timeStr: String): Date? {
        val formats = listOf(
            "dd.MM.yy HH:mm",
            "dd.MM.yyyy HH:mm"
        )

        for (locale in listOf(Locale.ENGLISH, Locale("tr"), Locale.GERMAN)) {
            for (pattern in formats) {
                try {
                    val sdf = SimpleDateFormat(pattern, locale)
                    sdf.timeZone = TimeZone.getDefault()
                    return sdf.parse("$dateStr $timeStr")
                } catch (_: Exception) {
                    // Try next format
                }
            }
        }
        return null
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

object MessageConverterFactory {
    private val converters = mapOf(
        ChatPlatform.WHATSAPP to WhatsAppConverter(),
        ChatPlatform.INSTAGRAM to InstagramConverter()
    )

    fun getConverter(platform: ChatPlatform): MessageConverter {
        return converters[platform]
            ?: throw IllegalArgumentException("Unsupported platform: $platform")
    }

    fun detectPlatform(rawText: String): ChatPlatform {
        if (Regex("""\d{2}\.\d{2}\.\d{2},\s\d{2}:\d{2}\s-\s""").containsMatchIn(rawText)) {
            return ChatPlatform.WHATSAPP
        }

        else throw Exception("Platform couldn't be identified")
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