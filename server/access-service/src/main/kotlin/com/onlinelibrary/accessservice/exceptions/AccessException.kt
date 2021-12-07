package com.onlinelibrary.accessservice.exceptions

sealed class AccessException(message: String) : Exception(message)

class RevokeAccessException(message: String) : AccessException(message)
class IssueAccessException(message: String) : AccessException(message)
class GetAccessException(message: String) : AccessException(message)
class RequestAccessException(message: String) : AccessException(message)
class ApproveAccessException(message: String) : AccessException(message)
class RejectAccessException(message: String) : AccessException(message)
class UpdateAccessException(message: String) : AccessException(message)
class InvalidAccessException(message: String) : AccessException(message)
