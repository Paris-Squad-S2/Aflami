package com.paris_2.datasource.remote.authentication

import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.authentication.exeptions.NetworkException
import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.model.remote.RequestTokenDto
import com.paris_2.repository.authentication.model.remote.SessionDto
import com.paris_2.repository.authentication.model.remote.GuestSessionDto
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRemoteDataSourceImpl @Inject constructor(
    private val apiService: AuthenticationApi
) : AuthenticationRemoteDataSource {

    override suspend fun getRequestToken(): RequestTokenDto = safeApiCall {
        apiService.getRequestToken()
    }

    override suspend fun validateWithLogin(request: LoginRequest): RequestTokenDto = safeApiCall {
        apiService.validateWithLogin(request)
    }

    override suspend fun createSession(requestToken: String): SessionDto = safeApiCall {
        apiService.createSession(mapOf("request_token" to requestToken))
    }

    override suspend fun createGuestSession(): GuestSessionDto = safeApiCall {
        apiService.createGuestSession()
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
        return try {
            apiCall()
        } catch (exception: HttpException) {
            when (exception.code()) {
                401 -> throw NetworkException.UnauthenticatedException("Not authenticated: ${exception.message()}")
                in 500..599 -> throw NetworkException.ServerException("Server error: ${exception.message()}")
                else -> throw NetworkException.UnknownException("HTTP error: ${exception.message()}")
            }
        } catch (e: Exception) {
            throw NetworkException.UnknownException("Unexpected error: ${e.message}")
        }
    }

    override fun getRegisterUrl() = REGISTER_URL
    override fun getForgetPasswordUrl() = FORGET_PASSWORD_URL

    companion object{
        const val REGISTER_URL = "https://www.themoviedb.org/signup"
        const val FORGET_PASSWORD_URL = "https://www.themoviedb.org/reset-password"
    }
}