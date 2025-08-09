package com.paris_2.repository.user.dataSource.local

interface AuthenticationLocalDataSource {
    fun saveSessionId(sessionId: String)
    fun getSessionId(): String?
    fun deleteSessionId(): Boolean
    fun isLoggedIn(): Boolean
    fun setIsGuest(isGuest: Boolean)
    fun isGuest(): Boolean
    fun hasAnySession(): Boolean
    fun saveUserName(username: String)
    fun getUserName(): String
}