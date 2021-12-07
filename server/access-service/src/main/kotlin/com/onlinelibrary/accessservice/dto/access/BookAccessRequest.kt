package com.onlinelibrary.accessservice.dto.access

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection="book-access-requests")
data class BookAccessRequest (
    @Id val requestId: String,
    val bookId: String,
    val emailId: String
)