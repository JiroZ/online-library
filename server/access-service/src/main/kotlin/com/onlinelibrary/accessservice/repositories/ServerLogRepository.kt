package com.onlinelibrary.accessservice.repositories

import com.onlinelibrary.accessservice.dto.log.ServerLog
import org.springframework.data.mongodb.repository.MongoRepository

interface ServerLogRepository : MongoRepository<ServerLog, String>