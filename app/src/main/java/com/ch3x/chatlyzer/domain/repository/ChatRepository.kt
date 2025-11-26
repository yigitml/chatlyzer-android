package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.ChatDeleteRequest
import com.ch3x.chatlyzer.data.remote.ChatPostRequest
import com.ch3x.chatlyzer.data.remote.ChatPutRequest
import com.ch3x.chatlyzer.domain.model.Chat

interface ChatRepository {
    suspend fun cacheChat(
        chat: Chat
    )

    suspend fun cacheChats(
        chats: List<Chat>
    )

    suspend fun updateCachedChat(
        chat: Chat
    )

    suspend fun deleteCachedChat(
        chat: Chat
    )

    suspend fun getCachedChatById(
        chatId: String
    ): Chat?

    suspend fun getCachedChats(): List<Chat>

    suspend fun clearCachedChats()

    suspend fun fetchChats(): List<Chat>

    suspend fun fetchChatById(
        chatId: String
    ): Chat

    suspend fun createChat(
        chatPostRequest: ChatPostRequest
    ): Chat

    suspend fun updateChat(
        chatPutRequest: ChatPutRequest
    ): Chat

    suspend fun deleteChat(
        chatDeleteRequest: ChatDeleteRequest
    ): Chat

    fun getChats(): kotlinx.coroutines.flow.Flow<com.ch3x.chatlyzer.util.Resource<List<Chat>>>
}