package com.ch3x.chatlyzer.ui.screens.chats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch3x.chatlyzer.data.remote.ChatDeleteRequest
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.domain.use_case.DeleteChatUseCase
import com.ch3x.chatlyzer.domain.use_case.GetAllChatsUseCase
import com.ch3x.chatlyzer.ui.screens.ScreenState
import com.ch3x.chatlyzer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val deleteChatUseCase: DeleteChatUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ChatsState())
    val state: State<ChatsState> = _state

    private fun handleChatsResource(resource: Resource<List<Chat>>) {
        _state.value = when (resource) {
            is Resource.Success -> {
                ChatsState(
                    screenState = ScreenState.Success,
                    chats = resource.data
                )
            }

            is Resource.Error -> {
                ChatsState(
                    screenState = ScreenState.Error,
                    errorMessage = resource.message
                )
            }

            is Resource.Loading -> {
                ChatsState(
                    screenState = ScreenState.Loading
                )
            }
        }
    }

    private fun getAllChats() {
        viewModelScope.launch {
            getAllChatsUseCase().collect {
                handleChatsResource(it)
            }
        }
    }

    private fun deleteChat(id: String) {
        deleteChatUseCase(ChatDeleteRequest(id)).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    getAllChats()
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ChatsEvent) {
        when (event) {
            is ChatsEvent.GetAllChats -> {
                getAllChats()
            }
            is ChatsEvent.DeleteChat -> {
                deleteChat(event.id)
            }
        }
    }
}