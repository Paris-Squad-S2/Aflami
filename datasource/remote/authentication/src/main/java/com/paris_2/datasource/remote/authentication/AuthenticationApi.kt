package com.paris_2.datasource.remote.authentication

import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.model.remote.RequestTokenDto
import com.paris_2.repository.authentication.model.remote.SessionDto
import com.paris_2.repository.authentication.model.remote.GuestSessionDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationApi {
    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenDto

    @POST("authentication/token/validate_with_login")
    suspend fun validateWithLogin(@Body request: LoginRequest): RequestTokenDto

    @POST("authentication/session/new")
    suspend fun createSession(@Body body: Map<String, String>): SessionDto

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(): GuestSessionDto
}