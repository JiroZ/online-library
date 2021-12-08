package com.onlinelibrary.userservice.dto.messages

import com.onlinelibrary.userservice.dto.user.LibraryUser
import javax.validation.constraints.Pattern

class Messages {
    data class UserAuthRequest(
        @field:Pattern(regexp = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", message = "Incorrect Email ID")
        val email: String,
        val password: String
    )

    data class UserRegistrationRequest(
        @field:Pattern(regexp = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", message = "Incorrect Email ID")
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