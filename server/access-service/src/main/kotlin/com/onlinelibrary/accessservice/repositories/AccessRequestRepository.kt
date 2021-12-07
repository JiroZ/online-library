package com.onlinelibrary.accessservice.repositories

import com.onlinelibrary.accessservice.dto.access.BookAccessRequest
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface AccessRequestRepository: MongoRepository<BookAccessRequest, String> {
    fun findAllByEmailId(emailId: String): List<BookAccessRequest>
}