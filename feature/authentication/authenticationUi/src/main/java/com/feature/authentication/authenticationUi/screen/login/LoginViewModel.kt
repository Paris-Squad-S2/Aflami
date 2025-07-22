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
    @StringRes val passwordErrorMessage: Int? = null,
    val loginButtonState: ButtonState = ButtonState.Normal,
    val guestButtonState: ButtonState = ButtonState.Normal,
    @StringRes val snackBarMessage: Int = R.string.login_failed,
    val showSnackBar: Boolean = false
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
                passwordErrorMessage = if (isError) R.string.password_should_be_4_characters_or_more else null
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
                emitState(
                    screenState.value.copy(
                        loginButtonState = ButtonState.Loading,
                        passwordErrorMessage = null
                    )
                )
                loginUseCase(
                    username = screenState.value.username,
                    password = screenState.value.password
                )
            },
            onSuccess = { loginSuccess ->
                if (!loginSuccess) {
                    emitState(
                        screenState.value.copy(
                            passwordErrorMessage = R.string.incorrect_password,
                            loginButtonState = ButtonState.Disabled
                        )
                    )
                } else {
                    navigateToHome()
                }
            },
            onError = {
                emitState(
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
                emitState(
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
                    emitState(
                        screenState.value.copy(
                            loginButtonState = ButtonState.Normal,
                            showSnackBar = true,
                            snackBarMessage = R.string.guest_login_failed
                        )
                    )
                }
            },
            onError = {
                emitState(
                    screenState.value.copy(
                        loginButtonState = ButtonState.Normal,
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
        emitState(screenState.value.copy(loginButtonState = newButtonState))
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
        emitState(screenState.value.copy(showSnackBar = false))
    }

}