package com.feature.authentication.authenticationUi.screen.login

import com.feature.authentication.authenticationUi.R
import com.paris_2.aflami.designsystem.components.ButtonState
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @org.junit.jupiter.api.BeforeEach
    fun setup() {
        viewModel = spyk(LoginViewModel(appNavigator = mockk(relaxed = true)))
    }

    @Test
    fun `init sets buttonState correctly`() {
        val state = viewModel.screenState.value

        Assertions.assertEquals(ButtonState.Disabled, state.buttonState)
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
            state.errorMessage
        )
    }

    @Test
    fun `onPasswordChange with valid password clears errorMessage`() {
        viewModel.onPasswordChange("1234")
        val state = viewModel.screenState.value

        Assertions.assertNull(state.errorMessage)
    }

    @Test
    fun `onShowPasswordChange toggles showPassword`() {
        val initial = viewModel.screenState.value.showPassword
        viewModel.onShowPasswordChange(initial)
        val state = viewModel.screenState.value

        Assertions.assertEquals(!initial, state.showPassword)
    }
}