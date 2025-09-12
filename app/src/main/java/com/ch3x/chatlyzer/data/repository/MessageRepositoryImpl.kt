package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.MessageDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.MessageDeleteRequest
import com.ch3x.chatlyzer.data.remote.MessagePostRequest
import com.ch3x.chatlyzer.data.remote.MessagePutRequest
import com.ch3x.chatlyzer.data.remote.api.MessageApi
import com.ch3x.chatlyzer.domain.model.Message
import com.ch3x.chatlyzer.domain.repository.MessageRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor(
    private val messageApi: MessageApi,
    private val messageDao: MessageDao
): MessageRepository {
    override suspend fun cacheMessage(message: Message) =
        messageDao.insertMessage(message.toEntity())

    override suspend fun cacheMessages(messages: List<Message>) =
        messageDao.insertMessages(messages.map { it.toEntity() })

    override suspend fun updateCachedMessage(message: Message) =
        messageDao.updateMessage(message.toEntity())

    override suspend fun deleteCachedMessage(message: Message) =
        messageDao.deleteMessage(message.toEntity())

    override suspend fun getCachedMessageById(messageId: String): Message? =
        messageDao.getMessageById(messageId)?.toDomain()

    override suspend fun getCachedMessages(): List<Message> =
        messageDao.getAllMessages().map { it.toDomain() }

    override suspend fun clearCachedMessages() =
        messageDao.deleteAllMessages()

    override suspend fun fetchMessageById(id: String): Message? =
        messageApi.getMessageById(id).requireDataOrThrow().toDomain()

    override suspend fun fetchMessagesByChatId(chatId: String): List<Message> =
        messageApi.getMessagesByChatId(chatId).requireDataOrThrow().map { it.toDomain() }

    override suspend fun createMessage(messagePostRequest: MessagePostRequest): Message =
        messageApi.createMessage(messagePostRequest).requireDataOrThrow().toDomain()

    override suspend fun updateMessage(messagePutRequest: MessagePutRequest): Message =
        messageApi.updateMessage(messagePutRequest).requireDataOrThrow().toDomain()

    override suspend fun deleteMessage(messageDeleteRequest: MessageDeleteRequest): Message =
        messageApi.deleteMessage(messageDeleteRequest).requireDataOrThrow().toDomain()
}