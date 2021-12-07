package com.onlinelibrary.userservice.controllers.advisor

import com.onlinelibrary.userservice.exceptions.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvisor {

    @ExceptionHandler(UserException::class)
    fun handleUserException(e: UserException) : ResponseEntity<String> {
        return ResponseEntity(e.message,HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleDefaultException(e:Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}