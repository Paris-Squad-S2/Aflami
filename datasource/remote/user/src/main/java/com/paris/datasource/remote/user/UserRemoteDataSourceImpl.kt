package com.paris.datasource.remote.user

import com.paris.repository.user.dataSource.remote.UserRemoteDataSource
import com.paris.repository.user.exeptions.NetworkException
import com.paris.repository.user.model.remote.AccountDto
import com.paris.repository.user.model.remote.GuestSessionDto
import com.paris.repository.user.model.remote.LoginRequest
import com.paris.repository.user.model.remote.RequestTokenDto
import com.paris.repository.user.model.remote.SessionDto
import retrofit2.HttpException

class UserRemoteDataSourceImpl (
    private val apiService: UserApi
) : UserRemoteDataSource {

    override suspend fun getRequestToken(): RequestTokenDto = safeApiCall {
        apiService.getRequestToken()
    }

    override suspend fun validateWithLogin(request: LoginRequest): RequestTokenDto = safeApiCall {
        apiService.validateWithLogin(request)
    }

    override suspend fun createSession(requestToken: String): SessionDto = safeApiCall {
        apiService.createSession(mapOf(REQUEST_TOKEN to requestToken))
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
    override suspend fun getAccountDetails(): AccountDto {
        return apiService.getAccountDetails()
    }

    companion object{
        private const val REGISTER_URL = "https://www.themoviedb.org/signup"
        private const val FORGET_PASSWORD_URL = "https://www.themoviedb.org/reset-password"
        private const val REQUEST_TOKEN = "request_token"
    }
}