package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.FileDeleteRequest
import com.ch3x.chatlyzer.domain.model.File
import okhttp3.MultipartBody

interface FileRepository {
    suspend fun cacheFile(file: File)

    suspend fun cacheFiles(files: List<File>)

    suspend fun updateCachedFile(file: File)

    suspend fun deleteCachedFile(file: File)

    suspend fun getCachedFileById(fileId: String): File?

    suspend fun getCachedFiles(): List<File>

    suspend fun clearCachedFiles()

    suspend fun fetchFiles(): List<File>

    suspend fun fetchFileById(id: String): File?

    suspend fun fetchFilesByChatId(chatId: String): List<File>

    suspend fun uploadFile(file: MultipartBody.Part): File

    suspend fun deleteFile(fileDeleteRequest: FileDeleteRequest): File
}