package com.ch3x.chatlyzer.util

sealed class Resource<out T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()

    data class Success<T>(val data: T) : Resource<T>()

    data class Error<T>(val message: String, val data: T? = null) : Resource<T>()

    fun data(): T? = when (this) {
        is Success -> data
        is Error -> data
        is Loading -> data
    }
}