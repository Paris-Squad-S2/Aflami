package com.feature.authentication.authenticationUi.screen.login

import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.domain.user.usecase.auth.LoginUseCase
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class LoginViewModelTest {

    private val navigator: AuthenticationNavigator = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var bottomNavBarAPI: BottomNavBarAPI

    @BeforeEach
    fun setup() {
        loginUseCase = mockk()
        bottomNavBarAPI = mockk(relaxed = true)
        viewModel = spyk(
            LoginViewModel(
                bottomNavBarAPI = bottomNavBarAPI,
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

/*    @OptIn(ExperimentalCoroutinesApi::class)
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
    }*/
}
