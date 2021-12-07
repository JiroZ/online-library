package com.onlinelibrary.accessservice.dto.user

data class ServerUser(var email: String,var isAdmin: Boolean, var authHeader: String)