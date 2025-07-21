package com.paris_2.repository.authentication.dataSource.remote

import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.model.remote.RequestTokenDto
import com.paris_2.repository.authentication.model.remote.SessionDto

interface AuthenticationRemoteDataSource {
    suspend fun getRequestToken(): RequestTokenDto
    suspend fun validateWithLogin(request: LoginRequest): RequestTokenDto
    suspend fun createSession(requestToken: String): SessionDto
}