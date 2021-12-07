package com.onlinelibrary.accessservice.repositories

import com.onlinelibrary.accessservice.dto.user.LibraryUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<LibraryUser, String> {
}