package com.onlinelibrary.bookservice.dto.log

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "server-logs")
data class ServerLog(
    @Id val logId: String,
    val action: String,
    val message: String,
    val date: Date
)