package com.ch3x.chatlyzer.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object JsonMapFactory {
    private val mapType = object : TypeToken<Map<String, Any>?>() {}.type
    private val gson: Gson = GsonBuilder().create()

    fun fromJson(jsonString: String): Map<String, Any>? {
        return gson.fromJson(jsonString, mapType)
    }

    fun toJson(map: Map<String, Any>?): String {
        return gson.toJson(map)
    }

    fun fromJsonOrNull(jsonString: String): Map<String, Any>? {
        return try {
            fromJson(jsonString)
        } catch (_: Exception) {
            null
        }
    }
}