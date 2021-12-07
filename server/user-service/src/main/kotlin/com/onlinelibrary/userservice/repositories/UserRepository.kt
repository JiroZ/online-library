package com.onlinelibrary.userservice.repositories

import com.onlinelibrary.userservice.dto.user.LibraryUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<LibraryUser, String> {
}