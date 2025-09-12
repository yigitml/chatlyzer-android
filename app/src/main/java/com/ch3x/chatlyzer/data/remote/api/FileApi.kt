package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.FileDeleteRequest
import com.ch3x.chatlyzer.data.remote.dto.FileDto
import com.ch3x.chatlyzer.data.remote.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface FileApi {

    @GET("file")
    suspend fun getFiles():
            ApiResponse<List<FileDto>>

    @GET("file")
    suspend fun getFileById(
        @Query("id") id: String
    ): ApiResponse<FileDto>

    @GET("file")
    suspend fun getFilesByChatId(
        @Query("chatId") chatId: String
    ): ApiResponse<List<FileDto>>

    @Multipart
    @POST("file")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): ApiResponse<FileDto>

    @GET("file")
    suspend fun deleteFile(
        @Body request: FileDeleteRequest
    ): ApiResponse<FileDto>
}

/*

--- Examples ---

fun File.toMultipartBody(): MultipartBody.Part {
    val requestBody = this.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(
        name = "file",
        filename = this.name,
        body = requestBody
    )
}

object FileUploadExample {
    suspend fun uploadFile(fileApi: FileApi, file: File) {
        try {
            val response = fileApi.uploadFile(file.toMultipartBody())
            // Handle successful response
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun getFiles(fileApi: FileApi) {
        try {
            val response = fileApi.getFiles()
            // Handle successful response
        } catch (e: Exception) {
            // Handle error
        }
    }
}
 */