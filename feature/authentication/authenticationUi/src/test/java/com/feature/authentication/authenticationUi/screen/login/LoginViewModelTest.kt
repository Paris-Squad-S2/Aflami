package com.feature.authentication.authenticationUi.screen.login

import com.paris_2.domain.user.exception.InvalidCredentialsException
import com.paris_2.domain.user.usecase.LoginUseCase
import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris_2.aflami.bottomNavBar.AppNavigationAPI
import com.paris_2.aflami.designsystem.components.ButtonState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class LoginViewModelTest {

    private val navigator: AuthenticationNavigator = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var appNavigationAPI: AppNavigationAPI

    @BeforeEach
    fun setup() {
        loginUseCase = mockk()
        appNavigationAPI = mockk(relaxed = true)
        viewModel = spyk(
            LoginViewModel(
                appNavigationAPI = appNavigationAPI,
                loginUseCase = loginUseCase,
                guestLoginUseCase = mockk(relaxed = true),
                navigator = navigator
            )
        )
    }



    @Test
    @Order(1)
    fun `init sets buttonState correctly`() {
        val state = viewModel.screenState.value

        Assertions.assertEquals(ButtonState.Disabled, state.loginButtonState)
    }

    @Test
    @Order(2)
    fun `onUsernameChange updates username and buttonState`() {
        viewModel.onUsernameChange("user")
        val state = viewModel.screenState.value

        Assertions.assertEquals("user", state.username)
    }

    @Test
    @Order(3)
    fun `onPasswordChange with short password sets errorMessage`() {
        viewModel.onPasswordChange("123")
        val state = viewModel.screenState.value

        Assertions.assertEquals(
            R.string.password_should_be_4_characters_or_more,
            state.passwordErrorMessage
        )
    }

    @Test
    @Order(4)
    fun `onPasswordChange with valid password clears errorMessage`() {
        viewModel.onPasswordChange("1234")
        val state = viewModel.screenState.value

        Assertions.assertNull(state.passwordErrorMessage)
    }

    @Test
    @Order(5)
    fun `onShowPasswordChange toggles showPassword`() {
        val initial = viewModel.screenState.value.showPassword
        viewModel.onShowPasswordChange(initial)
        val state = viewModel.screenState.value

        Assertions.assertEquals(!initial, state.showPassword)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Order(6)
    fun `onClickLogin with invalid credentials sets error message and disables button`() = runTest {
        coEvery {
            loginUseCase(
                "user",
                "1234"
            )
        } throws (InvalidCredentialsException("Invalid credentials"))

        viewModel.onClickLogin()
        runCurrent()

        val state = viewModel.screenState.value
        Assertions.assertEquals(ButtonState.Disabled, state.loginButtonState)
    }
}
