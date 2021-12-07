package com.onlinelibrary.bookservice.services.userservices

import com.onlinelibrary.bookservice.dto.messages.Messages.*


interface UserService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationResponse;
    fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse;
}