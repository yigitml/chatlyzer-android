package com.ch3x.chatlyzer.data.remote.interceptor

import com.ch3x.chatlyzer.domain.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking {
                sessionManager.logout()
            }
        }

        return response
    }
}
