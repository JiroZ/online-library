package com.onlinelibrary.userservice.dto.user

import javax.validation.constraints.Pattern

data class ServerUser(
    @field:Pattern(regexp = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", message = "Incorrect Email ID")
    var email: String,
    var isAdmin: Boolean,
    var authHeader: String
    )