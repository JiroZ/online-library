package com.onlinelibrary.bookservice.dto.book

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "book-accesses")
data class BookAccess(
    @Id val email: String,
    val issuedBooks: MutableList<IssuedBook>
)