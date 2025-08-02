package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.AuthenticationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    fun `getSessionIdUseCase should return session ID when repository returns a valid session`() =
        runTest {
        val expectedSessionId = "abc123"
            coEvery { authenticationRepository.getSessionId() } returns expectedSessionId

        val actual = getSessionIdUseCase()

        assertEquals(expectedSessionId, actual)
    }

    @Test
    fun `getSessionIdUseCase should return null when session ID is not available`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns null

        val actual = getSessionIdUseCase()

        assertEquals(null, actual)
    }
}