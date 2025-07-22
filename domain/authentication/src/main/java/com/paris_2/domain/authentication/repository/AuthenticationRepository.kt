package com.paris_2.domain.authentication.repository

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun guestLogin(): Boolean
    fun saveSessionId(sessionId: String)
    fun getSessionId(): String?
}