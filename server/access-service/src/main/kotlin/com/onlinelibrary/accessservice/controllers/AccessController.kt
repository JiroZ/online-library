package com.onlinelibrary.accessservice.controllers

import com.onlinelibrary.accessservice.dto.access.BookAccessRequest
import com.onlinelibrary.accessservice.dto.book.IssuedBook
import com.onlinelibrary.accessservice.dto.log.ServerLog
import com.onlinelibrary.accessservice.dto.messages.Messages.*
import com.onlinelibrary.accessservice.exceptions.AWSException
import com.onlinelibrary.accessservice.exceptions.AccessException
import com.onlinelibrary.accessservice.exceptions.BookException
import com.onlinelibrary.accessservice.exceptions.UserException
import com.onlinelibrary.accessservice.services.accessservice.AccessService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.ws.rs.core.Response

@RestController
@RequestMapping("/access")
class AccessController(val accessService: AccessService) {
    @PostMapping("/update")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun updateAccessesByCSV(@ModelAttribute message: CSVRequestMessage, bindingResult: BindingResult): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        return ResponseEntity.ok(accessService.updateAccess(message.csvFile))
    }

    @PostMapping("/revoke")
    fun revokeAccess(@RequestBody message: RevokeAccessRequestMessage, bindingResult: BindingResult): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        return ResponseEntity.ok(accessService.revokeAccess(message))
    }

    @GetMapping("/logs")
    fun getServerLogs(): ResponseEntity<List<ServerLog>> {
        return ResponseEntity.ok(accessService.getServerLogs())
    }

    @PostMapping("/request")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun requestAccess(@RequestBody message : RequestBookAccessMessage, bindingResult: BindingResult): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        return ResponseEntity.ok(accessService.requestAccess(message))
    }

    @GetMapping("/requests")
    fun getAllBookRequests(): ResponseEntity<List<BookAccessRequest>> {
        return ResponseEntity.ok(accessService.getAllBookRequests())
    }

    @PostMapping("/approve")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun approveAccessRequest(@RequestBody message : ApproveAccessRequestMessage, bindingResult: BindingResult): ResponseEntity<String> {
        bindErrorResult(bindingResult)
        return ResponseEntity.ok(accessService.approveAccess(message))
    }

    @PostMapping("/reject")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun rejectAccessRequest(@RequestBody message : RejectAccessRequestMessage, bindingResult: BindingResult): ResponseEntity<String> {
        return ResponseEntity.ok(accessService.rejectAccess(message))
    }

    @GetMapping("/{emailId}")
    @Throws(UserException::class, AWSException::class, AccessException::class, BookException::class)
    fun getIssuedBooksByEmailId(@PathVariable emailId : String): ResponseEntity<MutableList<IssuedBook>> {
        return ResponseEntity.ok(accessService.getIssuedBooksByEmailId(emailId))
    }

    @GetMapping("/requests/{emailId}")
    fun getBookAccessRequestsByEmailId(@PathVariable emailId : String): ResponseEntity<List<BookAccessRequest>> {
        return ResponseEntity.ok(accessService.getBookAccessRequestsByEmailId(emailId))
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