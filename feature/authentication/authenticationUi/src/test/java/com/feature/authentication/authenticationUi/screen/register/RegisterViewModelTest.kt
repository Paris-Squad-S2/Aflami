package com.feature.authentication.authenticationUi.screen.register

import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.domain.user.usecase.GetRegisterUrlUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegisterViewModelTest {
    private val navigator: AuthenticationNavigator = mockk(relaxed = true)

    @Test
    fun `init sets registrationUrl correctly`() {
        val getRegisterUrlUseCase = mockk<GetRegisterUrlUseCase>()
        every { getRegisterUrlUseCase() } returns "https://www.themoviedb.org/signup"

        val viewModel = spyk(RegisterViewModel(getRegisterUrlUseCase, navigator))
        val state = viewModel.screenState.value
        Assertions.assertEquals("https://www.themoviedb.org/signup", state.registrationUrl)
    }

    @Test
    fun `onNavigateBack calls navigateUp`() {
        val getRegisterUrlUseCase = mockk<GetRegisterUrlUseCase>()
        every { getRegisterUrlUseCase() } returns "https://www.themoviedb.org/signup"

        val viewModel = spyk(RegisterViewModel(getRegisterUrlUseCase, navigator))
        viewModel.onNavigateBack()
        verify { viewModel.onNavigateBack() }
    }
}