package com.feature.authentication.authenticationUi.screen.forgotPassword

import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris_2.domain.authentication.usecase.GetForgetPasswordUrlUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForgotPasswordViewModelTest {
    private val navigator: AuthenticationNavigator = mockk(relaxed = true)

    @Test
    fun `init sets resetPasswordUrl correctly`() {
        val getForgetPasswordUrlUseCase = mockk<GetForgetPasswordUrlUseCase>()
        every { getForgetPasswordUrlUseCase() } returns "https://www.themoviedb.org/reset-password"

        val viewModel = ForgotPasswordViewModel(navigator,getForgetPasswordUrlUseCase)
        val state = viewModel.screenState.value
        Assertions.assertEquals("https://www.themoviedb.org/reset-password", state.resetPasswordUrl)
    }

    @Test
    fun `onNavigateBack calls navigateUp`() {
        val getForgetPasswordUrlUseCase = mockk<GetForgetPasswordUrlUseCase>()
        every { getForgetPasswordUrlUseCase() } returns "https://www.themoviedb.org/reset-password"

        val viewModel = spyk(ForgotPasswordViewModel(navigator,getForgetPasswordUrlUseCase))
        viewModel.onNavigateBack()
        verify { viewModel.onNavigateBack() }
    }
}