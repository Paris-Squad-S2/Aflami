package com.paris_2.domain.authentication.usecases

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.authentication.repository.AuthenticationRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private lateinit var repository: AuthenticationRepository
    private lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `invoke should return true when repository login returns true`() = runTest {
        coEvery { repository.login("user", "pass") } returns true
        val result = useCase.invoke("user", "pass")
        assertThat(result).isTrue()
    }

    @Test
    fun `invoke should return false when repository login returns false`() = runTest {
        coEvery { repository.login("user", "pass") } returns false
        val result = useCase.invoke("user", "pass")
        assertThat(result).isFalse()
    }

    @Test
    fun `saveSessionId should call repository saveSessionId`() {
        every { repository.saveSessionId("session123") } returns Unit
        useCase.saveSessionId("session123")
        verify { repository.saveSessionId("session123") }
    }
}
