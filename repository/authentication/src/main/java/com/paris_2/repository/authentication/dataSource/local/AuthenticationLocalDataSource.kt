package com.paris_2.repository.authentication.dataSource.local

interface AuthenticationLocalDataSource {
    fun saveSessionId(sessionId: String)
    fun getSessionId(): String?
}