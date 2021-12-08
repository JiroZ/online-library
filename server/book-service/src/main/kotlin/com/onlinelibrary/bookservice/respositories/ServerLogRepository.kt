package com.onlinelibrary.bookservice.respositories

import com.onlinelibrary.bookservice.dto.log.ServerLog
import org.springframework.data.mongodb.repository.MongoRepository

interface ServerLogRepository : MongoRepository<ServerLog, String>