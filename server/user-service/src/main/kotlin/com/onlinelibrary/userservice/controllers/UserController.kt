package com.onlinelibrary.userservice.controllers

import com.onlinelibrary.userservice.dto.messages.Messages.*
import com.onlinelibrary.userservice.dto.user.LibraryUser
import com.onlinelibrary.userservice.exceptions.UserException
import com.onlinelibrary.userservice.services.userservice.DefaultUserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UserController(
    private val userService: DefaultUserService
) {
    @PostMapping("/user/auth")
    @Throws(UserException::class)
    fun authenticateUser(
        @RequestBody userAuthRequest: UserAuthRequest,
        bindingResult: BindingResult
    ): ResponseEntity<UserAuthResponse> {
        bindErrorResult(bindingResult)
        val userUserAuthResponse: UserAuthResponse = userService.authUser(userAuthRequest)

        return ResponseEntity.ok(userUserAuthResponse)
    }

    @PostMapping("/user/registration")
    @Throws(UserException::class)
    private fun registerUser(
        @Valid @RequestBody userRegistrationRequest: UserRegistrationRequest,
        bindingResult: BindingResult
    ): ResponseEntity<RegistrationResponse> {


        val registrationMessage: RegistrationResponse =
            userService.registerUser(userRegistrationRequest)

        bindErrorResult(bindingResult)
        return ResponseEntity.ok(registrationMessage)
    }

    @GetMapping("/user/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<LibraryUser> {
        return ResponseEntity.ok(userService.getUserByEmail(username))
    }

    @GetMapping("/user/all")
    fun allUsers(): ResponseEntity<List<LibraryUser>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    private fun bindErrorResult(bindingResult: BindingResult) {
        val errorMessage = StringBuilder()
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.fieldErrors
            for (error in errors) {
                errorMessage.append(error.defaultMessage!! + " ")
            }
            throw Exception(errorMessage.toString())
        }
    }
}