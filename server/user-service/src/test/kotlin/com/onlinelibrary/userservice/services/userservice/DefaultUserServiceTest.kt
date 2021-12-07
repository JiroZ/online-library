package com.onlinelibrary.userservice.services.userservice

import com.onlinelibrary.userservice.dto.messages.Messages
import com.onlinelibrary.userservice.exceptions.UserException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DefaultUserServiceTest(@Autowired val userService: UserService) {

//    val registrationRequest: Messages.UserRegistrationRequest =
//        Messages.UserRegistrationRequest("bhavishya2012@gmail.com", "bhavishya", "1234")
//    val authWrongRequest = Messages.UserAuthRequest("bhavissshya20@gmail.com", "1234")
//    val authRequest = Messages.UserAuthRequest("bhavishya20@gmail.com", "1234")
//
//    @Test
//    fun `Check if user can be registered successfully`() {
//        val message = userService.registerUser(registrationRequest)
//        assertTrue(message.isRegistered)
//    }
//
//    @Test
//    fun `Check if username is already registered`() {
//        val message = userService.registerUser(registrationRequest)
//        assertFalse(message.isRegistered)
//    }
//
//    @Test
//    fun `Check if user can be authenticated`() {
//        val message = userService.authUser(authRequest)
//
//        assertTrue(message.isAuthenticated)
//    }
//
//    @Test()
//    fun `Check if authentication is given wrong data`() {
//        assertThrows(UserException::class.java) {
//            userService.authUser(authWrongRequest)
//        }
//    }
}