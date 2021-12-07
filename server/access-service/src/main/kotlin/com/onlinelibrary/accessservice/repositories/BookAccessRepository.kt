package com.onlinelibrary.accessservice.repositories

import com.onlinelibrary.accessservice.dto.book.BookAccess
import com.onlinelibrary.accessservice.dto.user.LibraryUser
import org.springframework.data.mongodb.repository.MongoRepository

interface BookAccessRepository : MongoRepository<BookAccess, String>