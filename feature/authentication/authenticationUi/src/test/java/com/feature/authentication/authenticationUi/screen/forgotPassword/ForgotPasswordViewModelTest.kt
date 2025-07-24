package com.feature.authentication.authenticationUi.screen.forgotPassword

import com.paris_2.domain.authentication.usecase.GetForgetPasswordUrlUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForgotPasswordViewModelTest {

    @Test
    fun `init sets resetPasswordUrl correctly`() {
        val getForgetPasswordUrlUseCase = mockk<GetForgetPasswordUrlUseCase>()
        every { getForgetPasswordUrlUseCase() } returns "https://www.themoviedb.org/reset-password"

        val viewModel = ForgotPasswordViewModel(getForgetPasswordUrlUseCase)
        val state = viewModel.screenState.value
        Assertions.assertEquals("https://www.themoviedb.org/reset-password", state.resetPasswordUrl)
    }

    @Test
    fun `onNavigateBack calls navigateUp`() {
        val getForgetPasswordUrlUseCase = mockk<GetForgetPasswordUrlUseCase>()
        every { getForgetPasswordUrlUseCase() } returns "https://www.themoviedb.org/reset-password"

        val viewModel = spyk(ForgotPasswordViewModel(getForgetPasswordUrlUseCase))
        viewModel.onNavigateBack()
        verify { viewModel.onNavigateBack() }
    }
}