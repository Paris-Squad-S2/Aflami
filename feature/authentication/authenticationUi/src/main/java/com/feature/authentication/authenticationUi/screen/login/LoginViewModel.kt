package com.feature.authentication.authenticationUi.screen.login

import androidx.annotation.StringRes
import androidx.navigation.NavOptions
import com.feature.authentication.authenticationApi.AuthenticationDestinations
import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.authentication.usecases.GuestLoginUseCase
import com.paris_2.domain.authentication.usecases.LoginUseCase

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val buttonState: ButtonState = ButtonState.Normal
)

class LoginViewModel(
    private val appNavigator: AppNavigator,
    private val loginUseCase: LoginUseCase,
    private val guestLoginUseCase: GuestLoginUseCase,
) : BaseViewModel<LoginUIState>(LoginUIState()), LoginScreenInteractionListener {

    init {
        updateStateButton()
    }

    override fun onUsernameChange(username: String) {
        emitState(newState = screenState.value.copy(username = username))
        updateStateButton()
    }

    override fun onPasswordChange(password: String) {
        val isError = password.length < 4
        emitState(
            screenState.value.copy(
                password = password,
                errorMessage = if (isError) R.string.password_should_be_4_characters_or_more else null
            )
        )
        updateStateButton()
    }

    override fun onShowPasswordChange(showPassword: Boolean) {
        emitState(newState = screenState.value.copy(showPassword = !showPassword))
    }

    override fun onClickLogin() {
        tryToExecute(
            execute = {
                loginUseCase(
                    username = screenState.value.username,
                    password = screenState.value.password
                )
            },
            onSuccess = { loginSuccess ->
                if (!loginSuccess) {
                    emitState(
                        screenState.value.copy(
                            errorMessage = R.string.incorrect_password,
                            buttonState = ButtonState.Disabled
                        )
                    )
                } else {
                    navigateToHome()
                }
            },
            onError = {
                // TODO: Handle error by showing snack bar
            }
        )
    }

    override fun onClickLoginAsGuest() {
        tryToExecute(
            execute = {
                guestLoginUseCase()
            },
            onSuccess = { guestLoginSuccess ->
                if (guestLoginSuccess) {
                    navigateToHome()
                } else {
                    // TODO: Handle guest login failure by showing snack bar
                }
            },
            onError = {
                // TODO: Handle error by showing snack bar
            }
        )
    }

    override fun onClickForgotPassword() {
        navigate(
            AuthenticationDestinations.ForgotPasswordWebViewScreen
        )
    }

    override fun onClickCreateAccount() {
        navigate(
            AuthenticationDestinations.RegisterWebViewScreen
        )
    }


    private fun updateStateButton() {
        val newButtonState = if (screenState.value.username.isEmpty() ||
            screenState.value.password.isEmpty() ||
            screenState.value.errorMessage != null
        ) {
            ButtonState.Disabled
        } else {
            ButtonState.Normal
        }
        emitState(screenState.value.copy(buttonState = newButtonState))
    }

    private suspend fun navigateToHome() {
        appNavigator.navigate(
            AppDestinations.HomeFeature(),
            NavOptions.Builder().apply {
                setPopUpTo(
                    AppDestinations.AuthenticationFeature(),
                    inclusive = true
                )
            }.build()
        )
    }

}