package com.onlinelibrary.accessservice.repositories

import com.onlinelibrary.accessservice.dto.book.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String> {
}