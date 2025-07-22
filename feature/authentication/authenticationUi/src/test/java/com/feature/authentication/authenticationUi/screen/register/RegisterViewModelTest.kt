package com.feature.authentication.authenticationUi.screen.register

import com.paris_2.domain.authentication.usecases.GetRegisterUrlUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegisterViewModelTest {

    @Test
    fun `init sets registrationUrl correctly`() {
        val getRegisterUrlUseCase = mockk<GetRegisterUrlUseCase>()
        every { getRegisterUrlUseCase() } returns "https://www.themoviedb.org/signup"

        val viewModel = spyk(RegisterViewModel(getRegisterUrlUseCase))
        val state = viewModel.screenState.value
        Assertions.assertEquals("https://www.themoviedb.org/signup", state.registrationUrl)
    }

    @Test
    fun `onNavigateBack calls navigateUp`() {
        val getRegisterUrlUseCase = mockk<GetRegisterUrlUseCase>()
        every { getRegisterUrlUseCase() } returns "https://www.themoviedb.org/signup"

        val viewModel = spyk(RegisterViewModel(getRegisterUrlUseCase))
        viewModel.onNavigateBack()
        verify { viewModel.onNavigateBack() }
    }
}