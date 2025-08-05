package com.paris_2.repository.user.repository

import com.paris_2.domain.user.exception.UnknownAuthException
import com.google.common.truth.Truth.assertThat
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.user.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.user.model.remote.GuestSessionDto
import com.paris_2.repository.user.model.remote.LoginRequest
import com.paris_2.repository.user.model.remote.RequestTokenDto
import com.paris_2.repository.user.model.remote.SessionDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class AuthenticationRepositoryImplTest {
    private lateinit var remoteDataSource: AuthenticationRemoteDataSource
    private lateinit var localDataSource: AuthenticationLocalDataSource
    private lateinit var repository: AuthenticationRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk(relaxed = true)
        repository = AuthenticationRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `login should return true and save sessionId when all steps succeed`() = runTest {
        val username = "user"
        val password = "pass"
        val requestToken = "token123"
        val sessionId = "session456"
        coEvery { remoteDataSource.getRequestToken() } returns RequestTokenDto(requestToken = requestToken, success = true)
        coEvery { remoteDataSource.validateWithLogin(LoginRequest(username, password, requestToken)) } returns RequestTokenDto(requestToken = requestToken, success = true)
        coEvery { remoteDataSource.createSession(requestToken) } returns SessionDto(sessionId)
        coEvery { localDataSource.saveSessionId(sessionId) } returns Unit

        val result = repository.login(username, password)

        assertThat(result).isTrue()
        coVerify { localDataSource.saveSessionId(sessionId) }
    }

    @Test
    fun `login should return false when requestToken is null`() = runTest {
        val username = "user"
        val password = "pass"
        coEvery { remoteDataSource.getRequestToken() } returns RequestTokenDto(requestToken = null, success = false)

        val result = repository.login(username, password)

        assertThat(result).isFalse()
    }

    @Test
    fun `login should return false when sessionId is null`() = runTest {
        val username = "user"
        val password = "pass"
        val requestToken = "token123"
        coEvery { remoteDataSource.getRequestToken() } returns RequestTokenDto(requestToken = requestToken, success = true)
        coEvery { remoteDataSource.validateWithLogin(LoginRequest(username, password, requestToken)) } returns RequestTokenDto(requestToken = requestToken, success = true)
        coEvery { remoteDataSource.createSession(requestToken) } returns SessionDto(null)

        val result = repository.login(username, password)

        assertThat(result).isFalse()
    }

    @Test
    fun `login should throw exception when remoteDataSource throws`() = runTest {
        val username = "user"
        val password = "pass"
        coEvery { remoteDataSource.getRequestToken() } throws RuntimeException("Network error")

        assertFailsWith<UnknownAuthException> {
            repository.login(username, password)
        }
    }

    @Test
    fun `saveSessionId should call localDataSource saveSessionId`() {
        val sessionId = "session123"
        repository.saveSessionId(sessionId)
        verify { localDataSource.saveSessionId(sessionId) }
    }

    @Test
    fun `getSessionId should return value from localDataSource`() = runTest {
        val sessionId = "session123"
        every { localDataSource.getSessionId() } returns sessionId

        val result = repository.getSessionId()

        assertThat(result).isEqualTo(sessionId)
    }

    @Test
    fun `guestLogin should return true and save sessionId when guestSessionId is present`() = runTest {
        val guestSessionId = "guest_123"
        coEvery { remoteDataSource.createGuestSession() } returns GuestSessionDto(success = true, guestSessionId = guestSessionId, expiresAt = "2025-07-23 10:11:19 UTC")
        coEvery { localDataSource.saveSessionId(guestSessionId) } returns Unit

        val result = repository.guestLogin()

        assertThat(result).isTrue()
        coVerify { localDataSource.saveSessionId(guestSessionId) }
    }

    @Test
    fun `guestLogin should return false when guestSessionId is null`() = runTest {
        coEvery { remoteDataSource.createGuestSession() } returns GuestSessionDto(success = true, guestSessionId = null, expiresAt = "2025-07-23 10:11:19 UTC")

        val result = repository.guestLogin()

        assertThat(result).isFalse()
    }

    @Test
    fun `guestLogin should throw exception when remoteDataSource throws`() = runTest {
        coEvery { remoteDataSource.createGuestSession() } throws RuntimeException("Network error")

        assertFailsWith<UnknownAuthException> {
            repository.guestLogin()
        }
    }

    @Test
    fun `login should set isGuest to false when login succeeds`() = runTest {
        val username = "user"
        val password = "pass"
        val requestToken = "token123"
        val sessionId = "session456"
        coEvery { remoteDataSource.getRequestToken() } returns RequestTokenDto(requestToken = requestToken, success = true)
        coEvery { remoteDataSource.validateWithLogin(LoginRequest(username, password, requestToken)) } returns RequestTokenDto(requestToken = requestToken, success = true)
        coEvery { remoteDataSource.createSession(requestToken) } returns SessionDto(sessionId)

        val result = repository.login(username, password)

        assertThat(result).isTrue()
        coVerify {
            localDataSource.saveSessionId(sessionId)
            localDataSource.setIsGuest(false)
        }
    }

    @Test
    fun `guestLogin should set isGuest to true when guest login succeeds`() = runTest {
        val guestSessionId = "guest_123"
        coEvery { remoteDataSource.createGuestSession() } returns GuestSessionDto(
            success = true,
            guestSessionId = guestSessionId,
            expiresAt = "2025-07-23"
        )

        val result = repository.guestLogin()

        assertThat(result).isTrue()
        coVerify {
            localDataSource.saveSessionId(guestSessionId)
            localDataSource.setIsGuest(true)
        }
    }
    @Test
    fun `isLoggedIn should return value from localDataSource`() {
        every { localDataSource.isLoggedIn() } returns true
        assertThat(repository.isLoggedIn()).isTrue()
    }

    @Test
    fun `getRegisterUrl should return url from remoteDataSource`() {
        val expectedUrl = "https://example.com/register"
        every { remoteDataSource.getRegisterUrl() } returns expectedUrl
        assertThat(repository.getRegisterUrl()).isEqualTo(expectedUrl)
    }

    @Test
    fun `getForgetPasswordUrl should return url from remoteDataSource`() {
        val expectedUrl = "https://example.com/reset"
        every { remoteDataSource.getForgetPasswordUrl() } returns expectedUrl
        assertThat(repository.getForgetPasswordUrl()).isEqualTo(expectedUrl)
    }
    @Test
    fun `hasAnySession should return value from localDataSource`() {
        every { localDataSource.hasAnySession() } returns true
        assertThat(repository.hasAnySession()).isTrue()
    }

}