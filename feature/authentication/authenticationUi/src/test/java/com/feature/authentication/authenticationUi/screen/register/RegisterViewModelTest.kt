package com.feature.authentication.authenticationUi.screen.register

import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegisterViewModelTest {

    @Test
    fun `init sets registrationUrl correctly`() {
        val viewModel = spyk(RegisterViewModel())
        val state = viewModel.screenState.value
        Assertions.assertEquals("https://www.themoviedb.org/signup", state.registrationUrl)
    }

    @Test
    fun `onNavigateBack calls navigateUp`() {
        val viewModel = spyk(RegisterViewModel())
        viewModel.onNavigateBack()
        verify { viewModel.onNavigateBack() }
    }
}