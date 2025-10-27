package com.ch3x.chatlyzer.data.remote

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val message: String? = null,
    val status: Int? = 200
)

fun <T> ApiResponse<T>.requireDataOrThrow(): T {
    if (this.data != null) {
        return this.data
    }
    
    if (!this.success) throw Exception(this.message ?: "API request failed")
    if (this.error != null) throw Exception(this.error)
    throw Exception("Response data is null")
}

fun parseRetrofitError(e: HttpException): String {
    return try {
        val errorBody = e.response()?.errorBody()?.string()
        println("Retrofit error body: $errorBody")

        val apiError = Gson().fromJson(errorBody, ApiResponse::class.java)
        apiError?.message ?: apiError?.error ?: "HTTP ${e.code()} Error"
    } catch (ex: Exception) {
        "HTTP ${e.code()} Error"
    }
}