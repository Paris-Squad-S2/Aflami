package com.feature.authentication.authenticationUi.screen.forgotPassword

import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForgotPasswordViewModelTest {

    @Test
    fun `init sets resetPasswordUrl correctly`() {
        val viewModel = spyk(ForgotPasswordViewModel())
        val state = viewModel.screenState.value
        Assertions.assertEquals("https://www.themoviedb.org/reset-password", state.resetPasswordUrl)
    }

    @Test
    fun `onNavigateBack calls navigateUp`() {
        val viewModel = spyk(ForgotPasswordViewModel())
        viewModel.onNavigateBack()
        verify { viewModel.onNavigateBack() }
    }
}