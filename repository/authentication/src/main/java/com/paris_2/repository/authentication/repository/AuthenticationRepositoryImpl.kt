package com.paris_2.repository.authentication.repository

import com.paris_2.domain.authentication.repository.AuthenticationRepository
import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : AuthenticationRepository {
    override suspend fun login(username: String, password: String): Boolean {
        val tokenResponse = remoteDataSource.getRequestToken()
        val requestToken = tokenResponse.requestToken ?: return false
        remoteDataSource.validateWithLogin(LoginRequest(username, password, requestToken))
        val sessionDto = remoteDataSource.createSession(requestToken)
        sessionDto.sessionId?.let {
            localDataSource.saveSessionId(it)
            return true
        }
        return false
    }

    override fun saveSessionId(sessionId: String) {
        localDataSource.saveSessionId(sessionId)
    }

    override fun getSessionId(): String? {
        return localDataSource.getSessionId()
    }
}
