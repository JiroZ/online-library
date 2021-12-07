package com.onlinelibrary.bookservice.dto.user

data class ServerUser(var email: String,var isAdmin: Boolean, var authHeader: String)