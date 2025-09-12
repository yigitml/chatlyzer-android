package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.MessageDeleteRequest
import com.ch3x.chatlyzer.data.remote.MessagePostRequest
import com.ch3x.chatlyzer.data.remote.MessagePutRequest
import com.ch3x.chatlyzer.domain.model.Message

interface MessageRepository {
    suspend fun cacheMessage(message: Message)

    suspend fun cacheMessages(messages: List<Message>)

    suspend fun updateCachedMessage(message: Message)

    suspend fun deleteCachedMessage(message: Message)

    suspend fun getCachedMessageById(messageId: String): Message?

    suspend fun getCachedMessages(): List<Message>

    suspend fun clearCachedMessages()

    suspend fun fetchMessageById(id: String): Message?

    suspend fun fetchMessagesByChatId(chatId: String): List<Message>

    suspend fun createMessage(messagePostRequest: MessagePostRequest): Message

    suspend fun updateMessage(
        messagePutRequest: MessagePutRequest
    ): Message

    suspend fun deleteMessage(messageDeleteRequest: MessageDeleteRequest): Message
} 