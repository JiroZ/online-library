package com.onlinelibrary.bookservice.respositories

import com.onlinelibrary.bookservice.dto.user.LibraryUser
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<LibraryUser, String> {
}