package com.onlinelibrary.bookservice.authority;

import org.springframework.security.core.GrantedAuthority

class LibraryUserAuthority(val role: String): GrantedAuthority {
    override fun getAuthority(): String {
        return role
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other is LibraryUserAuthority) {
            role == other.role
        } else false
    }

    override fun hashCode(): Int {
        return role.hashCode()
    }

    override fun toString(): String {
        return role
    }
}

