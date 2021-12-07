package com.onlinelibrary.bookservice.exceptions

sealed class BookException(message: String) : Exception(message)

class BookNotFoundException(message: String) : BookException(message)

class BookAddException(message: String) : BookException(message)
class BookDeleteException(message: String) : BookException(message)
class BookGetException(message: String) : BookException(message)
