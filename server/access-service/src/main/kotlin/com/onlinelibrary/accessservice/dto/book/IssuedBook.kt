package com.onlinelibrary.accessservice.dto.book

import java.util.*

data class  IssuedBook (
    val bookId: String,
    val issueDate: Date,
    val expDate: Date
)