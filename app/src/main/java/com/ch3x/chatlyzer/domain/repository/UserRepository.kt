package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.UserPutRequest
import com.ch3x.chatlyzer.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun getUserById(userId: String): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun getAllUsers(): List<User>

    suspend fun clearAllUsers()

    suspend fun fetchUser(): User

    suspend fun updateUser(
        userPutRequest: UserPutRequest
    ): User

    suspend fun deleteUser(): User
} 