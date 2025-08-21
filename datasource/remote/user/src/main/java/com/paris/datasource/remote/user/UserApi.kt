package com.paris.datasource.remote.user

import com.paris.repository.user.model.remote.AccountDto
import com.paris.repository.user.model.remote.LoginRequest
import com.paris.repository.user.model.remote.RequestTokenDto
import com.paris.repository.user.model.remote.SessionDto
import com.paris.repository.user.model.remote.GuestSessionDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenDto

    @POST("authentication/token/validate_with_login")
    suspend fun validateWithLogin(@Body request: LoginRequest): RequestTokenDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body body: Map<String, String>): SessionDto

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(): GuestSessionDto

    @GET("account")
    suspend fun getAccountDetails(): AccountDto

}