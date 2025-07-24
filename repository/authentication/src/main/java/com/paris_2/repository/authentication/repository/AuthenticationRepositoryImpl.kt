package com.paris_2.repository.authentication.repository

import com.paris_2.domain.authentication.exception.*
import com.paris_2.domain.authentication.repository.AuthenticationRepository
import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.authentication.exeptions.NetworkException

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : AuthenticationRepository {


    override suspend fun login(username: String, password: String): Boolean = handleAuthExceptions {
        val tokenResponse = remoteDataSource.getRequestToken()
        val requestToken = tokenResponse.requestToken ?: return@handleAuthExceptions false
        val validationResponse = remoteDataSource.validateWithLogin(LoginRequest(username, password, requestToken))
        if (validationResponse.success == null || !validationResponse.success) {
            throw InvalidCredentialsException()
        }
        val sessionDto = remoteDataSource.createSession(requestToken)
        sessionDto.sessionId?.let {
            localDataSource.saveSessionId(it)
            localDataSource.setIsGuest(false)
            return@handleAuthExceptions true
        }
        false
    }

    override suspend fun guestLogin(): Boolean = handleAuthExceptions {
        val guestSessionDto = remoteDataSource.createGuestSession()
        guestSessionDto.guestSessionId?.let {
            localDataSource.saveSessionId(it)
            localDataSource.setIsGuest(true)
            return@handleAuthExceptions true
        }
        false
    }

    private inline fun <T> handleAuthExceptions(block: () -> T): T {
        try {
            return block()
        } catch (e: NetworkException.UnauthenticatedException) {
            throw InvalidCredentialsException(e.message ?: "Invalid credentials")
        } catch (e: NetworkException.ServerException) {
            throw AuthNetworkException(e.message ?: "Server error")
        } catch (e: NetworkException.UnknownException) {
            throw UnknownAuthException(e.message ?: "Unknown authentication error")
        } catch (e: Exception) {
            throw UnknownAuthException(e.message ?: "Unknown authentication error")
        }
    }

    override fun saveSessionId(sessionId: String) {
        localDataSource.saveSessionId(sessionId)
    }

    override fun getSessionId(): String? {
        return localDataSource.getSessionId()
    }

    override fun getRegisterUrl() = remoteDataSource.getRegisterUrl()

    override fun getForgetPasswordUrl (): String {
        return remoteDataSource.getForgetPasswordUrl()
    }

    override fun isLoggedIn(): Boolean {
        return localDataSource.isLoggedIn()
    }

}
