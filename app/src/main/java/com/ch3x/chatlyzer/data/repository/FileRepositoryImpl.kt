package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.FileDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.FileDeleteRequest
import com.ch3x.chatlyzer.data.remote.api.FileApi
import com.ch3x.chatlyzer.domain.model.File
import com.ch3x.chatlyzer.domain.repository.FileRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepositoryImpl @Inject constructor(
    private val fileApi: FileApi,
    private val fileDao: FileDao
): FileRepository {
    override suspend fun cacheFile(file: File) =
        fileDao.insertFile(file.toEntity())

    override suspend fun cacheFiles(files: List<File>) =
        fileDao.insertFiles(files.map { it.toEntity() })

    override suspend fun updateCachedFile(file: File) =
        fileDao.updateFile(file.toEntity())

    override suspend fun deleteCachedFile(file: File) =
        fileDao.deleteFile(file.toEntity())

    override suspend fun getCachedFileById(fileId: String): File? =
        fileDao.getFileById(fileId)?.toDomain()

    override suspend fun getCachedFiles(): List<File> =
        fileDao.getAllFiles().map { it.toDomain() }

    override suspend fun clearCachedFiles() =
        fileDao.deleteAllFiles()

    override suspend fun fetchFiles(): List<File> =
        fileApi.getFiles().requireDataOrThrow().map { it.toDomain() }

    override suspend fun fetchFileById(id: String): File? =
        fileApi.getFileById(id).requireDataOrThrow().toDomain()

    override suspend fun fetchFilesByChatId(chatId: String): List<File> =
        fileApi.getFilesByChatId(chatId).requireDataOrThrow().map { it.toDomain() }

    override suspend fun uploadFile(file: MultipartBody.Part): File =
        fileApi.uploadFile(file).requireDataOrThrow().toDomain()

    override suspend fun deleteFile(fileDeleteRequest: FileDeleteRequest) =
        fileApi.deleteFile(fileDeleteRequest).requireDataOrThrow().toDomain()
}