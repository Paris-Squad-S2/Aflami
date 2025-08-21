package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSessionIdUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getSessionIdUseCase: GetSessionIdUseCase

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        getSessionIdUseCase = GetSessionIdUseCase(userRepository)
    }

    @Test
    fun `getSessionIdUseCase should return session ID when repository returns a valid session`() =
        runTest {
        val expectedSessionId = "abc123"
            coEvery { userRepository.getSessionId() } returns expectedSessionId

        val actual = getSessionIdUseCase()

        assertEquals(expectedSessionId, actual)
    }

    @Test
    fun `getSessionIdUseCase should return null when session ID is not available`() = runTest {
        coEvery { userRepository.getSessionId() } returns null

        val actual = getSessionIdUseCase()

        assertEquals(null, actual)
    }
}