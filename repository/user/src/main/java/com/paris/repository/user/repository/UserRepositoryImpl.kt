package com.paris.repository.user.repository

import com.paris.domain.user.exception.AuthNetworkException
import com.paris.domain.user.exception.InvalidCredentialsException
import com.paris.domain.user.exception.UnknownAuthException
import com.paris.domain.user.repository.UserRepository
import com.paris.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris.repository.user.dataSource.remote.UserRemoteDataSource
import com.paris.repository.user.exeptions.NetworkException
import com.paris.repository.user.model.remote.LoginRequest

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,

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
            localDataSource.saveUserName(username)
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

    override fun getForgetPasswordUrl(): String = remoteDataSource.getForgetPasswordUrl()

    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()

    override fun hasAnySession(): Boolean = localDataSource.hasAnySession()

    override suspend fun getAccountId(): Int? = runCatching {
        handleAuthExceptions { remoteDataSource.getAccountDetails().id }
    }.getOrNull()

    override fun deleteSessionId(): Boolean = localDataSource.deleteSessionId()

    override fun getUserName(): String = localDataSource.getUserName()

}
