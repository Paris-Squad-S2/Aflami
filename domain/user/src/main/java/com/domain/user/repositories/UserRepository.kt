package com.domain.user.repositories

import com.domain.user.models.User

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun removeRating(movieId: Int): Boolean
    suspend fun logout(): Boolean
    suspend fun login(email: String, password: String): User?
    suspend fun register(email: String, password: String): User?
}