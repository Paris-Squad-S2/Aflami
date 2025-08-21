package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository
import com.paris.domain.user.usecase.auth.GuestLoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GuestLoginUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var guestLoginUseCase: GuestLoginUseCase

    @Before
    fun setUp() {
        userRepository = mockk(relaxed = true)
        guestLoginUseCase = GuestLoginUseCase(userRepository)
    }

    @Test
    fun `guest login returns true on success`() = runBlocking {
        coEvery { userRepository.guestLogin() } returns true
        val result = guestLoginUseCase()
        assertTrue(result)
        coVerify { userRepository.guestLogin() }
    }

    @Test
    fun `guest login returns false on failure`() = runBlocking {
        coEvery { userRepository.guestLogin() } returns false
        val result = guestLoginUseCase()
        assertFalse(result)
        coVerify { userRepository.guestLogin() }
    }
}

