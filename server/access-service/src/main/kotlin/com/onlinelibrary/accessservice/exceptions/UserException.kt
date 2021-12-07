package com.onlinelibrary.accessservice.exceptions

sealed class UserException(message: String) : Exception(message)

class WrongPasswordOrUserNameException(message: String) : UserException(message)
class AccountLockedException(message: String) : UserException(message)
class UserNotFoundException(message: String) : UserException(message)
class UserNameNotFoundException(message: String) : UserException(message)
class UserAlreadyExistsException(message: String) : UserException(message)
class AccountDoesntExistException(message: String) : UserException(message)
class AccountDisabledException(message: String) : UserException(message)