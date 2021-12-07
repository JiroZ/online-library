package com.onlinelibrary.accessservice.services.userservices

import com.onlinelibrary.accessservice.dto.messages.Messages.*


interface UserService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationResponse;
    fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse;
}