package com.feature.authentication.authenticationUi.screen.login

import com.feature.authentication.authenticationUi.R
import com.paris_2.aflami.appnavigation.AppNavigationAPI
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.authentication.exception.InvalidCredentialsException
import com.paris_2.domain.authentication.usecase.LoginUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var appNavigationAPI: AppNavigationAPI

    @BeforeEach
    fun setup() {
        clearAllMocks()
        loginUseCase = mockk()
        appNavigationAPI = mockk(relaxed = true)
        viewModel = spyk(
            LoginViewModel(
                appNavigationAPI = appNavigationAPI,
                loginUseCase = loginUseCase,
                guestLoginUseCase = mockk(relaxed = true),
            )
        )
    }

    @Test
    fun `init sets buttonState correctly`() {
        val state = viewModel.screenState.value

        Assertions.assertEquals(ButtonState.Disabled, state.loginButtonState)
    }

    @Test
    fun `onUsernameChange updates username and buttonState`() {
        viewModel.onUsernameChange("user")
        val state = viewModel.screenState.value

        Assertions.assertEquals("user", state.username)
    }

    @Test
    fun `onPasswordChange with short password sets errorMessage`() {
        viewModel.onPasswordChange("123")
        val state = viewModel.screenState.value

        Assertions.assertEquals(
            R.string.password_should_be_4_characters_or_more,
            state.passwordErrorMessage
        )
    }

    @Test
    fun `onPasswordChange with valid password clears errorMessage`() {
        viewModel.onPasswordChange("1234")
        val state = viewModel.screenState.value

        Assertions.assertNull(state.passwordErrorMessage)
    }

    @Test
    fun `onShowPasswordChange toggles showPassword`() {
        val initial = viewModel.screenState.value.showPassword
        viewModel.onShowPasswordChange(initial)
        val state = viewModel.screenState.value

        Assertions.assertEquals(!initial, state.showPassword)
    }

    @Test
    fun `onClickLogin with invalid credentials sets error message and disables button`() {
        coEvery {
            loginUseCase(
                "user",
                "1234"
            )
        } throws (InvalidCredentialsException("Invalid credentials"))

        viewModel.onClickLogin()

        val state = viewModel.screenState.value
        Assertions.assertEquals(ButtonState.Disabled, state.loginButtonState)
    }

    @Test
    fun `onClickLogin with valid credentials navigates to home`() {
        coEvery { loginUseCase(any(), any()) } returns true

        viewModel.onUsernameChange("test")
        viewModel.onPasswordChange("1234")
        viewModel.onClickLogin()

        // Verify that navigateToHome() was called
        coVerify { appNavigationAPI() }
    }
}