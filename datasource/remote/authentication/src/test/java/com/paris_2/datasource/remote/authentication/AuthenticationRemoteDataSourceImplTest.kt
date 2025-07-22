package com.paris_2.datasource.remote.authentication

import com.google.common.truth.Truth.assertThat
import com.paris_2.repository.authentication.exeptions.NetworkException
import com.paris_2.repository.authentication.model.remote.GuestSessionDto
import com.paris_2.repository.authentication.model.remote.LoginRequest
import com.paris_2.repository.authentication.model.remote.RequestTokenDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import kotlin.test.assertFailsWith

class AuthenticationRemoteDataSourceImplTest {
    private lateinit var api: AuthenticationApi
    private lateinit var dataSource: AuthenticationRemoteDataSourceImpl

    @Before
    fun setUp() {
        api = mockk()
        dataSource = AuthenticationRemoteDataSourceImpl(api)
    }

    @Test
    fun `validateWithLogin should return expected RequestTokenDto when API call is successful`() = runTest {
        val request = LoginRequest("user", "pass", "token")
        val expected = RequestTokenDto("token", "token", true)
        coEvery { api.validateWithLogin(request) } returns expected

        val result = dataSource.validateWithLogin(request)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `validateWithLogin should throw UnauthenticatedException when API returns 401`() = runTest {
        val request = LoginRequest("user", "pass", "token")
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 401
            coEvery { message() } returns "Unauthorized"
        }
        coEvery { api.validateWithLogin(request) } throws httpException

        assertFailsWith<NetworkException.UnauthenticatedException> {
            dataSource.validateWithLogin(request)
        }
    }

    @Test
    fun `validateWithLogin should throw ServerException when API returns 500`() = runTest {
        val request = LoginRequest("user", "pass", "token")
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 500
            coEvery { message() } returns "Server Error"
        }
        coEvery { api.validateWithLogin(request) } throws httpException

        assertFailsWith<NetworkException.ServerException> {
            dataSource.validateWithLogin(request)
        }
    }

    @Test
    fun `validateWithLogin should throw UnknownException when API returns other HTTP error`() = runTest {
        val request = LoginRequest("user", "pass", "token")
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 403
            coEvery { message() } returns "Forbidden"
        }
        coEvery { api.validateWithLogin(request) } throws httpException

        assertFailsWith<NetworkException.UnknownException> {
            dataSource.validateWithLogin(request)
        }
    }

    @Test
    fun `validateWithLogin should throw UnknownException when unexpected exception occurs`() = runTest {
        val request = LoginRequest("user", "pass", "token")
        coEvery { api.validateWithLogin(request) } throws RuntimeException("Something went wrong")

        assertFailsWith<NetworkException.UnknownException> {
            dataSource.validateWithLogin(request)
        }
    }

    @Test
    fun `createGuestSession should return expected GuestSessionDto when API call is successful`() = runTest {
        val expected = GuestSessionDto(success = true, guestSessionId = "guest_id_123", expiresAt = "2025-07-23 10:11:19 UTC")
        coEvery { api.createGuestSession() } returns expected

        val result = dataSource.createGuestSession()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `createGuestSession should throw UnauthenticatedException when API returns 401`() = runTest {
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 401
            coEvery { message() } returns "Unauthorized"
        }
        coEvery { api.createGuestSession() } throws httpException

        assertFailsWith<NetworkException.UnauthenticatedException> {
            dataSource.createGuestSession()
        }
    }

    @Test
    fun `createGuestSession should throw ServerException when API returns 500`() = runTest {
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 500
            coEvery { message() } returns "Server Error"
        }
        coEvery { api.createGuestSession() } throws httpException

        assertFailsWith<NetworkException.ServerException> {
            dataSource.createGuestSession()
        }
    }

    @Test
    fun `createGuestSession should throw UnknownException when API returns other HTTP error`() = runTest {
        val httpException = mockk<HttpException> {
            coEvery { code() } returns 403
            coEvery { message() } returns "Forbidden"
        }
        coEvery { api.createGuestSession() } throws httpException

        assertFailsWith<NetworkException.UnknownException> {
            dataSource.createGuestSession()
        }
    }

    @Test
    fun `createGuestSession should throw UnknownException when unexpected exception occurs`() = runTest {
        coEvery { api.createGuestSession() } throws RuntimeException("Something went wrong")

        assertFailsWith<NetworkException.UnknownException> {
            dataSource.createGuestSession()
        }
    }
}