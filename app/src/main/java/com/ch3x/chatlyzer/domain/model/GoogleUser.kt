package com.ch3x.chatlyzer.domain.model

data class GoogleUser(
    val id: String,
    val idToken: String?,
    val email: String,
    val phoneNumber: String?,
    val displayName: String?,
    val familyName: String?,
    val givenName: String?,
    val photoUrl: String?
)