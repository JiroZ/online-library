package com.onlinelibrary.accessservice.dto.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.onlinelibrary.accessservice.authority.LibraryUserAuthority
import org.springframework.data.annotation.Id
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class LibraryUser(
    @field:Pattern(regexp = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", message = "Incorrect Email ID")
    @Id val email: String,
    @JsonIgnore var password: String?,
    @field:Size(min = 3, max = 10, message = "User Name Should Be Between 3 & 10 Characters.")
    var userName: String,
    val libraryAuthority: Set<LibraryUserAuthority>
) {
}