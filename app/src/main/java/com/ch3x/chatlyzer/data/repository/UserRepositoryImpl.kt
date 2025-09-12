package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.UserDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.UserPutRequest
import com.ch3x.chatlyzer.data.remote.api.UserApi
import com.ch3x.chatlyzer.domain.model.User
import com.ch3x.chatlyzer.domain.repository.UserRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi
): UserRepository {
    override suspend fun insertUser(user: User) =
        userDao.insertUser(user.toEntity())

    override suspend fun updateUser(user: User) =
        userDao.updateUser(user.toEntity())

    override suspend fun deleteUser(user: User) =
        userDao.deleteUser(user.toEntity())

    override suspend fun getUserById(userId: String): User? =
        userDao.getUserById(userId)?.toDomain()

    override suspend fun getUserByEmail(email: String): User? =
        userDao.getUserByEmail(email)?.toDomain()

    override suspend fun getAllUsers(): List<User> =
        userDao.getAllUsers().map { it.toDomain() }

    override suspend fun clearAllUsers() =
        userDao.deleteAllUsers()

    override suspend fun fetchUser(): User =
        userApi.getUser().requireDataOrThrow().toDomain()

    override suspend fun updateUser(userPutRequest: UserPutRequest): User =
        userApi.updateUser(userPutRequest).requireDataOrThrow().toDomain()

    override suspend fun deleteUser(): User =
        userApi.deleteUser().requireDataOrThrow().toDomain()
}