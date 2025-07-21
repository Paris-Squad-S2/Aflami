package com.paris_2.datasource.remote.authentication

import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.model.remote.RequestTokenDto
import com.paris_2.repository.authentication.model.remote.SessionDto
import retrofit2.Retrofit
import retrofit2.HttpException
import java.io.IOException

class AuthenticationRemoteDataSourceImpl(retrofit: Retrofit) : AuthenticationRemoteDataSource {
    private val api = retrofit.create(AuthenticationApi::class.java)

    override suspend fun getRequestToken(): RequestTokenDto = safeApiCall {
        api.getRequestToken()
    }

    override suspend fun validateWithLogin(request: LoginRequest): RequestTokenDto = safeApiCall {
        api.validateWithLogin(request)
    }

    override suspend fun createSession(requestToken: String): SessionDto = safeApiCall {
        api.createSession(mapOf("request_token" to requestToken))
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
        return try {
            apiCall()
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> throw UnauthenticatedException("Not authenticated: ${e.message()}")
                in 500..599 -> throw ServerException("Server error: ${e.message()}")
                else -> throw Exception("HTTP error: ${e.message()}", e)
            }
        } catch (e: IOException) {
            throw Exception("Network error: ${e.message}", e)
        } catch (e: Exception) {
            throw Exception("Unexpected error: ${e.message}", e)
        }
    }
}