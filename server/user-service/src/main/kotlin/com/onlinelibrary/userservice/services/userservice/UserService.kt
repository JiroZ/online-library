package com.onlinelibrary.userservice.services.userservice

import com.onlinelibrary.userservice.dto.messages.Messages.*


interface UserService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationResponse;
    fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse;
}