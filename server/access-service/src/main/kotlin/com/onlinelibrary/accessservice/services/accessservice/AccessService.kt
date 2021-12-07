package com.onlinelibrary.accessservice.services.accessservice

import com.onlinelibrary.accessservice.dto.access.BookAccessRequest
import com.onlinelibrary.accessservice.dto.book.IssuedBook
import com.onlinelibrary.accessservice.dto.log.ServerLog
import com.onlinelibrary.accessservice.dto.messages.Messages
import com.onlinelibrary.accessservice.dto.messages.Messages.*
import com.onlinelibrary.accessservice.exceptions.AccessException
import org.springframework.web.multipart.MultipartFile

interface AccessService {
    fun updateAccess(file: MultipartFile): String
    fun requestAccess(message: RequestBookAccessMessage): String
    fun approveAccess(message: ApproveAccessRequestMessage): String
    fun rejectAccess(message: RejectAccessRequestMessage): String
    fun revokeAccess(message: RevokeAccessRequestMessage): String

    fun getIssuedBooksByEmailId(emailId: String): MutableList<IssuedBook>
    fun getAllBookRequests(): List<BookAccessRequest>
    fun getBookAccessRequestsByEmailId(emailId: String): List<BookAccessRequest>
    fun getServerLogs(): List<ServerLog>
}