package com.onlinelibrary.userservice.services.userservice

import com.onlinelibrary.userservice.dto.messages.Messages
import com.onlinelibrary.userservice.exceptions.UserException
import org.junit.Before
import org.junit.BeforeClass
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DefaultUserServiceTest(@Autowired @Mock val userService: UserService) {

    val registrationRequest: Messages.UserRegistrationRequest =
        Messages.UserRegistrationRequest("bhavishya20@gmail.com", "bhavishya", "1234")
    val authRequest = Messages.UserAuthRequest("bhavishya20@gmail.com", "1234",)
    val authWrongRequest = Messages.UserAuthRequest("bhavishya20@gmail.com", "122234",)

    var message: Messages.RegistrationResponse? = null

    @BeforeEach
    fun `init Registration`() {
        if(message != null) {
            message = userService.registerUser(registrationRequest)
        }
    }

    @Test
    fun `Check if username is already registered`() {
        assertThrows(UserException::class.java) {
            userService.registerUser(registrationRequest)
            userService.registerUser(registrationRequest)
        }
    }

    @Test
    fun `Check if user can be authenticated`() {

        val message = userService.authUser(authRequest)
        assertTrue(message.isAuthenticated)
    }

    @Test
    fun `Check if authentication is given wrong data`() {
        assertThrows(UserException::class.java) {
            userService.authUser(authWrongRequest)
        }
    }
}