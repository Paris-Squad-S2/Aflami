package com.paris.repository.user.dataSource.remote

import com.paris.repository.user.model.remote.AccountDto
import com.paris.repository.user.model.remote.LoginRequest
import com.paris.repository.user.model.remote.RequestTokenDto
import com.paris.repository.user.model.remote.SessionDto
import com.paris.repository.user.model.remote.GuestSessionDto

interface UserRemoteDataSource {
    suspend fun getRequestToken(): RequestTokenDto
    suspend fun validateWithLogin(request: LoginRequest): RequestTokenDto
    suspend fun createSession(requestToken: String): SessionDto
    suspend fun createGuestSession(): GuestSessionDto
    fun getRegisterUrl(): String
    fun getForgetPasswordUrl(): String
    suspend fun getAccountDetails(): AccountDto
}