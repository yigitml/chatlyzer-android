package com.ch3x.chatlyzer.domain.repository

import android.net.Uri

interface LocalFileRepository {
    suspend fun readFileContent(uri: Uri): String
    fun getFileName(uri: Uri): String
    fun getFileSize(uri: Uri): Long
}
