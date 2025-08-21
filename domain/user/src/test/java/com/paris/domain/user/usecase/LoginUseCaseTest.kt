package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import com.paris.domain.user.usecase.auth.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private lateinit var repository: UserRepository
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
}
