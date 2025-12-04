package com.ch3x.chatlyzer.data.session

import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.domain.session.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) : SessionManager {

    private val _logoutEvent = MutableSharedFlow<Unit>(replay = 0)
    override val logoutEvent: SharedFlow<Unit> = _logoutEvent.asSharedFlow()

    override suspend fun logout() {
        appPreferencesRepository.logout()
        _logoutEvent.emit(Unit)
    }
}
