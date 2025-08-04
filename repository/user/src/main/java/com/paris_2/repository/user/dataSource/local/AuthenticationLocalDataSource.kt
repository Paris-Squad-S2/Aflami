package com.paris_2.repository.user.dataSource.local

interface AuthenticationLocalDataSource {
    fun saveSessionId(sessionId: String)
    fun getSessionId(): String?
    fun isLoggedIn(): Boolean

    fun setIsGuest(isGuest: Boolean)
    fun isGuest(): Boolean
    fun hasAnySession(): Boolean
}