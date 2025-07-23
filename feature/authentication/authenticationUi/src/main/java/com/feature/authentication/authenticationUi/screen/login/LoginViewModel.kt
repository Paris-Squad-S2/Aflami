package com.feature.authentication.authenticationUi.screen.login

import androidx.navigation.NavOptions
import com.feature.authentication.authenticationApi.AuthenticationDestinations
import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.domain.authentication.usecases.GuestLoginUseCase
import com.paris_2.domain.authentication.usecases.LoginUseCase

class LoginViewModel(
    private val appNavigator: AppNavigator,
    private val loginUseCase: LoginUseCase,
    private val guestLoginUseCase: GuestLoginUseCase,
) : BaseViewModel<LoginUIState>(LoginUIState()), LoginScreenInteractionListener {

    init {
        updateStateButton()
    }

    override fun onUsernameChange(username: String) {
        updateState(newState = screenState.value.copy(username = username, passwordErrorMessage = null))
        updateStateButton()
    }

    override fun onPasswordChange(password: String) {
        val isError = password.length < 4
        updateState(
            screenState.value.copy(
                password = password,
                passwordErrorMessage = if (isError) R.string.password_should_be_4_characters_or_more else null
            )
        )
        updateStateButton()
    }

    override fun onShowPasswordChange(showPassword: Boolean) {
        updateState(newState = screenState.value.copy(showPassword = !showPassword))
    }

    override fun onClickLogin() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        loginButtonState = ButtonState.Loading,
                        passwordErrorMessage = null,
                    )
                )
                loginUseCase(
                    username = screenState.value.username,
                    password = screenState.value.password
                )
            },
            onSuccess = { loginSuccess ->
                if (!loginSuccess) {
                    updateState(
                        screenState.value.copy(
                            snackBarMessage = R.string.incorrect_password,
                            loginButtonState = ButtonState.Disabled,
                            showSnackBar = true,
                        )
                    )
                } else {
                    navigateToHome()
                }
            },
            onInvalidCredentials = { localizedMessage ->
                updateState(
                    screenState.value.copy(
                        snackBarMessage = R.string.invalid_username_or_password,
                        loginButtonState = ButtonState.Disabled,
                        showSnackBar = true,
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        loginButtonState = ButtonState.Normal,
                        showSnackBar = true,
                        snackBarMessage = R.string.login_failed
                    )
                )
            }
        )
    }

    override fun onClickLoginAsGuest() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        guestButtonState = ButtonState.Loading,
                        passwordErrorMessage = null
                    )
                )
                guestLoginUseCase()
            },
            onSuccess = { guestLoginSuccess ->
                if (guestLoginSuccess) {
                    navigateToHome()
                } else {
                    updateState(
                        screenState.value.copy(
                            guestButtonState = ButtonState.Normal,
                            showSnackBar = true,
                            snackBarMessage = R.string.guest_login_failed
                        )
                    )
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        guestButtonState = ButtonState.Normal,
                        showSnackBar = true,
                        snackBarMessage = R.string.guest_login_failed
                    )
                )
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
            screenState.value.passwordErrorMessage != null
        ) {
            ButtonState.Disabled
        } else {
            ButtonState.Normal
        }
        updateState(screenState.value.copy(loginButtonState = newButtonState))
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

    override fun onHideSnackBar() {
        updateState(screenState.value.copy(showSnackBar = false))
    }

}