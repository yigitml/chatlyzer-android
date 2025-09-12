package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("isOnboarded") val isOnboarded: Boolean = false,
    @SerializedName("image") val image: String? = null,
    @SerializedName("googleId") val googleId: String? = null,
    @SerializedName("tokenVersion") val tokenVersion: Int = 0,
    @SerializedName("lastLoginAt") val lastLoginAt: Date? = null,
    @SerializedName("isActive") val isActive: Boolean = true,
    @SerializedName("updatedAt") val updatedAt: Date,
    @SerializedName("deletedAt") val deletedAt: Date? = null,
)