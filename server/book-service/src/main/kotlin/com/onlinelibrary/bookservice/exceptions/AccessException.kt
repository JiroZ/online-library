package com.onlinelibrary.bookservice.exceptions

sealed class AccessException(message: String) : Exception(message)

class InvalidAccessException(message: String) : AccessException(message)
class UnableToRevokeAccessException(message: String) : AccessException(message)
