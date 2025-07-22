package com.paris_2.domain.authentication.usecase

import com.paris_2.domain.authentication.repository.AuthenticationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GuestLoginUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var guestLoginUseCase: GuestLoginUseCase

    @Before
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)
        guestLoginUseCase = GuestLoginUseCase(authenticationRepository)
    }

    @Test
    fun `guest login returns true on success`() = runBlocking {
        coEvery { authenticationRepository.guestLogin() } returns true
        val result = guestLoginUseCase()
        assertTrue(result)
        coVerify { authenticationRepository.guestLogin() }
    }

    @Test
    fun `guest login returns false on failure`() = runBlocking {
        coEvery { authenticationRepository.guestLogin() } returns false
        val result = guestLoginUseCase()
        assertFalse(result)
        coVerify { authenticationRepository.guestLogin() }
    }
}

