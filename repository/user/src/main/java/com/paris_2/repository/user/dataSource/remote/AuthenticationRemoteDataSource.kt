package com.paris_2.repository.user.dataSource.remote

import com.paris_2.repository.user.model.remote.LoginRequest
import com.paris_2.repository.user.model.remote.RequestTokenDto
import com.paris_2.repository.user.model.remote.SessionDto
import com.paris_2.repository.user.model.remote.GuestSessionDto

interface AuthenticationRemoteDataSource {
    suspend fun getRequestToken(): RequestTokenDto
    suspend fun validateWithLogin(request: LoginRequest): RequestTokenDto
    suspend fun createSession(requestToken: String): SessionDto
    suspend fun createGuestSession(): GuestSessionDto
    fun getRegisterUrl(): String
    fun getForgetPasswordUrl(): String
}