package com.ch3x.chatlyzer.domain.session

import kotlinx.coroutines.flow.SharedFlow

interface SessionManager {
    val logoutEvent: SharedFlow<Unit>
    suspend fun logout()
}
