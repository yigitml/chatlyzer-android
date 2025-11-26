package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.ChatDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.ChatDeleteRequest
import com.ch3x.chatlyzer.data.remote.ChatPostRequest
import com.ch3x.chatlyzer.data.remote.ChatPutRequest
import com.ch3x.chatlyzer.data.remote.api.ChatApi
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.domain.repository.ChatRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val chatDao: ChatDao
): ChatRepository {
    override suspend fun cacheChat(chat: Chat) =
        chatDao.insertChat(chat.toEntity())

    override suspend fun cacheChats(chats: List<Chat>) =
        chatDao.insertChats(chats.map { it.toEntity() })

    override suspend fun updateCachedChat(chat: Chat) =
        chatDao.updateChat(chat.toEntity())

    override suspend fun deleteCachedChat(chat: Chat) =
        chatDao.deleteChat(chat.toEntity())

    override suspend fun getCachedChatById(chatId: String): Chat? =
        chatDao.getChatById(chatId)?.toDomain()

    override suspend fun getCachedChats(): List<Chat> =
        chatDao.getAllChats().map { it.toDomain() }

    override suspend fun clearCachedChats() =
        chatDao.deleteAllChats()

    override suspend fun fetchChats(): List<Chat> =
        chatApi.getChats().requireDataOrThrow().map { it.toDomain() }

    override suspend fun fetchChatById(chatId: String): Chat =
        chatApi.getChatById(chatId).requireDataOrThrow().toDomain()

    override suspend fun createChat(chatPostRequest: ChatPostRequest): Chat =
        chatApi.createChat(chatPostRequest).requireDataOrThrow().toDomain()

    override suspend fun updateChat(chatPutRequest: ChatPutRequest): Chat =
        chatApi.updateChat(chatPutRequest).requireDataOrThrow().toDomain()

    override suspend fun deleteChat(chatDeleteRequest: ChatDeleteRequest): Chat =
        chatApi.deleteChat(chatDeleteRequest).requireDataOrThrow().toDomain()

    override fun getChats(): kotlinx.coroutines.flow.Flow<com.ch3x.chatlyzer.util.Resource<List<Chat>>> = kotlinx.coroutines.flow.flow {
        emit(com.ch3x.chatlyzer.util.Resource.Loading())
        
        val cachedChats = getCachedChats()
        emit(com.ch3x.chatlyzer.util.Resource.Success(cachedChats))
        
        try {
            val remoteChats = fetchChats()
            // Clear old cache to avoid stale data if items were deleted on server?
            // Or just insert/update.
            // For a robust sync, we should probably clear and insert, or diff.
            // Let's clear and insert for simplicity as per "SSOT" usually implies network is truth.
            clearCachedChats() 
            cacheChats(remoteChats)
            emit(com.ch3x.chatlyzer.util.Resource.Success(remoteChats))
        } catch (e: Exception) {
            emit(com.ch3x.chatlyzer.util.Resource.Error(e.message ?: "Unknown error", cachedChats))
        }
    }
}