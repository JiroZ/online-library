package com.onlinelibrary.userservice.dto.messages

import com.onlinelibrary.userservice.dto.user.LibraryUser

class Messages {
    data class UserAuthRequest(
        val email: String,
        val password: String
    )

    data class UserRegistrationRequest(
        val email: String,
        val userName: String,
        val password: String
    )

    data class RegistrationResponse(
        var registrationNotice: String,
        var isRegistered: Boolean,
        var user: LibraryUser?
    )

    data class UserAuthResponse(
        var user: LibraryUser?,
        var isAuthenticated: Boolean,
        var authNotice: String,
        var token: String
    )
}