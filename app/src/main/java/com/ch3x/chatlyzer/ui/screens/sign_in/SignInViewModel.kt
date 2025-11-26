package com.ch3x.chatlyzer.ui.screens.sign_in

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
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
import com.ch3x.chatlyzer.domain.use_case.AuthUseCase
import com.ch3x.chatlyzer.domain.use_case.GetUserUseCase
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "SignInViewModel"

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
        appPreferencesRepository.isUserLoggedIn()
            .flatMapLatest { loggedIn ->
                if (loggedIn) {
                    getUserUseCase.invoke()
                } else {
                    kotlinx.coroutines.flow.flowOf(Resource.Success(null))
                }
            }
            .onEach { userResource ->
                when (userResource) {
                    is Resource.Success -> {
                        if (userResource.data != null) {
                            _signInState.update { SignInState.Success }
                        } else {
                            _signInState.update { SignInState.Idle }
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "Error fetching user: ${userResource.message}")
                        _signInState.update { SignInState.Error(userResource.message) }
                    }
                    is Resource.Loading -> _signInState.update { SignInState.Loading }
                }
            }
            .catch { e -> Log.e(TAG, "User fetch exception: ${e.localizedMessage}") }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInWithGoogle -> initiateGoogleSignIn(event.context)
        }
    }

    private fun initiateGoogleSignIn(context: Context) {
        viewModelScope.launch {
            try {
                val credentialManager = CredentialManager.create(context)
                // Two-step approach: try authorized accounts first
                performSignIn(credentialManager, context, filterAuthorized = true)
            } catch (e: Exception) {
                Log.e(TAG, "Sign-in initialization failed", e)
                _signInState.update { SignInState.Error("Sign-in init failed: ${e.localizedMessage}") }
            }
        }
    }

    private suspend fun performSignIn(
        credentialManager: CredentialManager,
        context: Context,
        filterAuthorized: Boolean
    ) {
        _signInState.update { SignInState.Loading }

        try {
            val googleOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(filterAuthorized)
                .setServerClientId(Constants.WEB_CLIENT_ID)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleOption)
                .build()

            val result: androidx.credentials.GetCredentialResponse = withContext(Dispatchers.IO) {
                credentialManager.getCredential(context, request)
            }

            val credential: androidx.credentials.Credential = result.credential

            processCredential(result.credential, context)

        } catch (e: NoCredentialException) {
            Log.w(TAG, "NoCredentialException: ${e.localizedMessage}")
            if (filterAuthorized) {
                // Retry with all accounts
                performSignIn(credentialManager, context, filterAuthorized = false)
            } else {
                _signInState.update { SignInState.Error("No credentials available.") }
            }
        } catch (e: GetCredentialException) {
            Log.e(TAG, "GetCredentialException: ${e.localizedMessage}", e)
            _signInState.update { SignInState.Error("Credential retrieval failed: ${e.localizedMessage}") }
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected exception during sign-in", e)
            _signInState.update { SignInState.Error("Unexpected error: ${e.localizedMessage}") }
        }
    }

    private suspend fun processCredential(credential: androidx.credentials.Credential, context: Context) {
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                authenticateUser(googleIdTokenCredential, context)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e(TAG, "Google ID token parsing failed", e)
                _signInState.update { SignInState.Error("Invalid Google ID token: ${e.message}") }
            }
        } else {
            Log.e(TAG, "Unsupported credential type: ${credential::class.java.simpleName}")
            _signInState.update { SignInState.Error("Unsupported credential type") }
        }
    }

    private suspend fun authenticateUser(googleIdTokenCredential: GoogleIdTokenCredential, context: Context) {
        val deviceId = getDeviceId(context)
        authUseCase.invoke(
            AuthMobilePostRequest(
                googleIdTokenCredential.idToken,
                deviceId
            )
        ).collectLatest { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d(TAG, "Authentication success for user: ${googleIdTokenCredential.id}")
                    _signInState.update { SignInState.Success }
                }
                is Resource.Error -> {
                    Log.e(TAG, "Authentication error: ${resource.message}")
                    _signInState.update { SignInState.Error(resource.message) }
                }
                is Resource.Loading -> _signInState.update { SignInState.Loading }
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(context: Context): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    // Optional: you can add logout and refreshToken hooks as before
    // https://developer.android.com/identity/sign-in/credential-manager-siwg logout is at bottom
}