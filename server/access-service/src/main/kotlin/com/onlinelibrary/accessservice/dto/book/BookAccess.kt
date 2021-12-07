package com.onlinelibrary.accessservice.dto.book

import com.onlinelibrary.accessservice.dto.user.LibraryUser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection="book-accesses")
data class BookAccess(
    @Id val email: String,
    val issuedBooks: MutableList<IssuedBook>
)