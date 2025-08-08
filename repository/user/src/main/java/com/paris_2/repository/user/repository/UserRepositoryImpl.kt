package com.paris_2.repository.user.repository

import com.paris_2.domain.user.exception.AuthNetworkException
import com.paris_2.domain.user.exception.InvalidCredentialsException
import com.paris_2.domain.user.exception.UnknownAuthException
import com.paris_2.domain.user.repository.UserRepository
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.user.dataSource.remote.UserRemoteDataSource
import com.paris_2.repository.user.exeptions.NetworkException
import com.paris_2.repository.user.model.remote.LoginRequest

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) : UserRepository {


    override suspend fun login(username: String, password: String): Boolean = handleAuthExceptions {
        val tokenResponse = remoteDataSource.getRequestToken()
        val requestToken = tokenResponse.requestToken ?: return@handleAuthExceptions false
        val validationResponse =
            remoteDataSource.validateWithLogin(LoginRequest(username, password, requestToken))
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

    override suspend fun getSessionId(): String? {
        return localDataSource.getSessionId()
    }

    override fun getRegisterUrl() = remoteDataSource.getRegisterUrl()

    override fun getForgetPasswordUrl(): String {
        return remoteDataSource.getForgetPasswordUrl()
    }

    override fun isLoggedIn(): Boolean {
        return localDataSource.isLoggedIn()
    }

    override fun hasAnySession(): Boolean {
        return localDataSource.hasAnySession()
    }

    override suspend fun setOnboardingCompleted() {
        localDataSource.setOnboardingCompleted()
    }

    override  fun isOnboardingCompleted(): Boolean {
        return localDataSource.isOnboardingCompleted()
    }

    override suspend fun getAccountId(): Int? {
        return remoteDataSource.getAccountDetails().id
    }

}
