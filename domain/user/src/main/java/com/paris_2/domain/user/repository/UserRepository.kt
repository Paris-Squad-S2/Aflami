package com.paris_2.domain.user.repository

interface UserRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun guestLogin(): Boolean
    fun getRegisterUrl(): String
    fun getForgetPasswordUrl(): String
    fun saveSessionId(sessionId: String)
    suspend fun getSessionId(): String?
    fun isLoggedIn(): Boolean
    fun hasAnySession(): Boolean
    suspend fun getAccountId(): Int?
}