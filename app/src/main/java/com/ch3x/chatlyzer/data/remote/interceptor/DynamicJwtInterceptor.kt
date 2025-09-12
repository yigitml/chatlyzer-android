package com.ch3x.chatlyzer.data.remote.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class DynamicJwtInterceptor @Inject constructor(
    private val tokenProviderProvider: Provider<TokenProvider>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val tokenProvider = tokenProviderProvider.get()

        val token = runBlocking { tokenProvider.getToken() }

        val authenticatedRequest = token?.let {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        } ?: originalRequest

        return chain.proceed(authenticatedRequest)
    }
}