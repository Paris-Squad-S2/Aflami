package com.paris_2.repository.authentication.repository

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): Boolean
    fun saveSessionId(sessionId: String)
    fun getSessionId(): String?
}
