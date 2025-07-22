package com.paris_2.repository.authentication.repository

import com.google.common.truth.Truth.assertThat
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.model.remote.RequestTokenDto
import com.paris_2.repository.authentication.model.remote.SessionDto
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

        assertFailsWith<RuntimeException> {
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
    fun `getSessionId should return value from localDataSource`() {
        val sessionId = "session123"
        every { localDataSource.getSessionId() } returns sessionId

        val result = repository.getSessionId()

        assertThat(result).isEqualTo(sessionId)
    }
}