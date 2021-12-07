package com.onlinelibrary.bookservice.dto.book

import org.springframework.data.annotation.Id
import java.util.*

data class Book(
    @Id val id: String,
    val bookTitle: String,
    val bookDescription: String,
    val date: Date,
    val fileName: String
)