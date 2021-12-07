package com.onlinelibrary.bookservice.controllers.advisor

import com.onlinelibrary.bookservice.exceptions.AWSException
import com.onlinelibrary.bookservice.exceptions.AccessException
import com.onlinelibrary.bookservice.exceptions.BookException
import com.onlinelibrary.bookservice.exceptions.UserException
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

    @ExceptionHandler(BookException::class)
    fun handleBookException(e: BookException) : ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AWSException::class)
    fun handleAWSException(e: AWSException) : ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AccessException::class)
    fun handleAccessException(e: AccessException) : ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleDefaultException(e:Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}