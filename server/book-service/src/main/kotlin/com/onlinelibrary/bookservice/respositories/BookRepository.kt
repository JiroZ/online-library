package com.onlinelibrary.bookservice.respositories

import com.onlinelibrary.bookservice.dto.book.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String> {
}