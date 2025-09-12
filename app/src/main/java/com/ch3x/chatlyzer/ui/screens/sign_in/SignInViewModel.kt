package com.ch3x.chatlyzer.ui.screens.sign_in

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch3x.chatlyzer.data.remote.AuthMobilePostRequest
import com.ch3x.chatlyzer.domain.model.GoogleUser
import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.domain.use_case.GetUserUseCase
import com.ch3x.chatlyzer.domain.use_case.AuthUseCase
import com.ch3x.chatlyzer.util.Constants
import com.ch3x.chatlyzer.util.Resource
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val appPreferencesRepository: AppPreferencesRepository
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    init { checkInitialLoginState() }

    private fun checkInitialLoginState() {
        viewModelScope.launch {
            appPreferencesRepository.isUserLoggedIn().onEach {
                if (it) {
                    getUserUseCase.invoke().onEach { userResource ->
                        println("User Resource: $userResource")
                        if (userResource is Resource.Success) {
                            _signInState.update { SignInState.Success }
                        } else if (userResource is Resource.Error) {
                            _signInState.update { SignInState.Error(userResource.message) }
                        }
                    }.catch { println(it.message) }.launchIn(viewModelScope)
                } else {
                    _signInState.update { SignInState.Idle }
                }
            }.launchIn(this)
        }
    }

    private fun initiateGoogleSignIn(context: Context) {
        viewModelScope.launch {
            try {
                val credentialManager = CredentialManager.create(context)
                performSignIn(credentialManager, context)
            } catch (e: Exception) {
                _signInState.update {
                    SignInState.Error("Sign-in initialization failed: ${e.localizedMessage}")
                }
            }
        }
    }

    private suspend fun performSignIn(
        credentialManager: CredentialManager,
        context: Context
    ) {
        _signInState.update { SignInState.Loading }

        try {
            val request = createGoogleSignInRequest()
            val result = withContext(Dispatchers.IO) {
                credentialManager.getCredential(
                    request = request,
                    context = context
                )
            }

            processCredential(result.credential, context)
        } catch (e: GetCredentialException) {
            handleSignInError(context, e)
        }
    }

    private suspend fun processCredential(credential: androidx.credentials.Credential, context: Context) {
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        authenticateUser(googleIdTokenCredential, context)
                    } catch (e: GoogleIdTokenParsingException) {
                        _signInState.update {
                            SignInState.Error("Invalid Google ID token: ${e.message}")
                        }
                    }
                } else {
                    _signInState.update {
                        SignInState.Error("Unsupported credential type")
                    }
                }
            }
            else -> {
                _signInState.update {
                    SignInState.Error("Unknown credential type")
                }
            }
        }
    }

    private suspend fun authenticateUser(
        googleIdTokenCredential: GoogleIdTokenCredential,
        context: Context
    ) {
        val deviceId = getDeviceId(context)
        authUseCase.invoke(
            AuthMobilePostRequest(
                googleIdTokenCredential.idToken,
                deviceId
            )
        ).collectLatest { resource ->
            when (resource) {
                is Resource.Success -> {
                    val googleUser = extractUserInfo(googleIdTokenCredential)
                    _signInState.update { SignInState.Success }
                }
                is Resource.Error -> {
                    _signInState.update {
                        SignInState.Error(resource.message)
                    }
                }
                is Resource.Loading -> {
                    _signInState.update { SignInState.Loading }
                }
            }
        }
    }

    private fun createGoogleSignInRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(Constants.WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun extractUserInfo(c: GoogleIdTokenCredential): GoogleUser = GoogleUser(
        id = c.id,
        idToken = c.idToken,
        email = "", // No email available from GoogleIdTokenCredential
        phoneNumber = c.phoneNumber,
        displayName = c.displayName,
        familyName = c.familyName,
        givenName = c.givenName,
        photoUrl = c.profilePictureUri?.toString()
    )

    private suspend fun handleSignInError(
        context: Context,
        e: Exception
    ) {
        when (e) {
            is NoCredentialException -> {
                _signInState.update {
                    SignInState.Error("Sign in failed: ${e.message}")
                }
            }
            is GetCredentialException -> {
                _signInState.update {
                    SignInState.Error("Sign in failed: ${e.message}")
                }
            }
            else -> {
                _signInState.update {
                    SignInState.Error("An unexpected error occurred during sign-in.")
                }
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(context: Context): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    private fun refreshToken() {
        viewModelScope.launch {
            try {
                // TODO: Implement token refresh mechanism
                // This might involve checking the current token's expiration
                // and using either the stored Google ID token or existing JWT
            } catch (e: Exception) {
                _signInState.update {
                    SignInState.Error("Token refresh failed: ${e.message}")
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                // TODO: Implement logout logic
                // Clear stored tokens, user info, etc.
                _signInState.update { SignInState.Idle }
            } catch (e: Exception) {
                _signInState.update {
                    SignInState.Error("Logout failed: ${e.message}")
                }
            }
        }
    }

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.SignInWithGoogle -> initiateGoogleSignIn(event.context)
        }
    }
}