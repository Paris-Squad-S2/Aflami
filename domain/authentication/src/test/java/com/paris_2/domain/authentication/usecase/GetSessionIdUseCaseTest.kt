package com.paris_2.domain.authentication.usecase

import com.paris_2.domain.authentication.repository.AuthenticationRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSessionIdUseCaseTest {

    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var getSessionIdUseCase: GetSessionIdUseCase

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk()
        getSessionIdUseCase = GetSessionIdUseCase(authenticationRepository)
    }

    @Test
    fun `invoke returns session ID`() {
        val expectedSessionId = "abc123"
        every { authenticationRepository.getSessionId() } returns expectedSessionId

        val actual = getSessionIdUseCase()

        assertEquals(expectedSessionId, actual)
    }

    @Test
    fun `invoke returns null if no session ID`() {
        every { authenticationRepository.getSessionId() } returns null

        val actual = getSessionIdUseCase()

        assertEquals(null, actual)
    }
}