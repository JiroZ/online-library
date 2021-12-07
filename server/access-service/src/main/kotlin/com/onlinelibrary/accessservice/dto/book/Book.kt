package com.onlinelibrary.accessservice.dto.book

import org.springframework.data.annotation.Id
import java.util.*

data class Book(
    @Id val id: String,
    val bookTitle: String,
    val date: Date,
    val fileName: String
)