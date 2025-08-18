package com.feature.authentication.authenticationUi.screen.login

import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.navigation.AuthenticationDestinations
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris_2.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.user.usecase.GuestLoginUseCase
import com.paris_2.domain.user.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.io.IOException

class LoginViewModelTest {

    private val navigator: AuthenticationNavigator = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var guestLoginUseCase: GuestLoginUseCase
    private lateinit var bottomNavBarAPI: BottomNavBarAPI

    @BeforeEach
    fun setup() {
        loginUseCase = mockk()
        guestLoginUseCase = mockk()
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

    @Test
    @Order(6)
    fun `onClickLogin with valid data sets Loading state`() {
        // Arrange
        viewModel.onUsernameChange("testuser")
        viewModel.onPasswordChange("1234")
        coEvery { loginUseCase(any(), any()) } returns true

        // Act
        viewModel.onClickLogin()

        // Assert
        verify {
            viewModel.updateState(
                match {
                    it.loginButtonState == ButtonState.Loading
                }
            )
        }
        verify(exactly = 1) { bottomNavBarAPI.invoke() }
    }

    @Test
    @Order(7)
    fun `onClickLogin with invalid credentials shows error snackbar`() {
        // Arrange
        viewModel.onUsernameChange("testuser")
        viewModel.onPasswordChange("1234")

        coEvery { loginUseCase(any(), any()) } returns false

        // Act
        viewModel.onClickLogin()

        // Assert
        verify {
            viewModel.updateState(
                match {
                    it.loginButtonState == ButtonState.Disabled &&
                            it.snackBarMessage == R.string.incorrect_password_or_username && it.showSnackBar
                }
            )
        }
    }

    @Test
    @Order(9)
    fun `onClickLoginAsGuest shows Loading and navigates on success`() {
        // Arrange
        coEvery { guestLoginUseCase() } returns true

        // Act
        viewModel.onClickLoginAsGuest()

        // Assert
        verify {
            viewModel.updateState(
                match { it.guestButtonState == ButtonState.Loading }
            )
        }
    }
    @Test
    @Order(10)
    fun `onClickLoginAsGuest when login fails shows error snackbar`() {
        // Arrange
        coEvery { guestLoginUseCase() } returns false

        // Act
        viewModel.onClickLoginAsGuest()

        // Assert
        verify {
            viewModel.updateState(
                match {
                    it.guestButtonState == ButtonState.Normal &&
                            it.snackBarMessage == R.string.guest_login_failed && it.showSnackBar
                }
            )
        }
    }

    @Test
    @Order(11)
    fun `onClickLoginAsGuest when useCase throws error shows snackbar`() {
        // Arrange
        coEvery { guestLoginUseCase() } throws Exception("Guest login error")

        // Act
        viewModel.onClickLoginAsGuest()

        // Assert
        verify {
            viewModel.updateState(
                match {
                    it.guestButtonState == ButtonState.Normal &&
                            it.snackBarMessage == R.string.guest_login_failed && it.showSnackBar
                }
            )
        }
    }

    @Test
    @Order(12)
    fun `onClickForgotPassword navigates to ForgotPasswordWebViewScreen`() {
        // Act
        viewModel.onClickForgotPassword()

        // Assert
        coVerify(exactly = 1) {
            navigator.navigate(AuthenticationDestinations.ForgotPasswordWebViewScreen)
        }
    }

    @Test
    @Order(13)
    fun `onClickCreateAccount navigates to RegisterWebViewScreen`() {
        // Act
        viewModel.onClickCreateAccount()

        // Assert
        coVerify(exactly = 1) {
            navigator.navigate(AuthenticationDestinations.RegisterWebViewScreen)
        }
    }

    @Test
    @Order(14)
    fun `onHideSnackBar sets showSnackBar to false`() {
        viewModel.updateState(viewModel.screenState.value.copy(showSnackBar = true))

        viewModel.onHideSnackBar()

        verify {
            viewModel.updateState(
                match { !it.showSnackBar }
            )
        }
    }
}
