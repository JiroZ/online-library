package com.onlinelibrary.accessservice.exceptions

sealed class BookException(message: String) : Exception(message)

class BookNotFoundException(message: String) : BookException(message)
class NoBooksFoundException(message: String) : BookException(message)
