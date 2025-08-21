package com.feature.authentication.authenticationUi.screen.login

import androidx.lifecycle.viewModelScope
import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.feature.authentication.authenticationUi.navigation.AuthenticationDestinations
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.domain.user.usecase.GuestLoginUseCase
import com.paris.domain.user.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val bottomNavBarAPI : BottomNavBarAPI,
    private val loginUseCase: LoginUseCase,
    private val guestLoginUseCase: GuestLoginUseCase,
    navigator: AuthenticationNavigator,
) : BaseViewModel<LoginUIState>(LoginUIState(), navigator), LoginScreenInteractionListener {

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
                            snackBarMessage = R.string.incorrect_password_or_username,
                            loginButtonState = ButtonState.Disabled,
                            showSnackBar = true,
                        )
                    )
                    hideSnackBar()
                } else {
                    navigateToHome()
                }
            },
            onInvalidCredentials = { localizedMessage ->
                updateState(
                    screenState.value.copy(
                        snackBarMessage = R.string.incorrect_password_or_username,
                        loginButtonState = ButtonState.Disabled,
                        showSnackBar = true,
                    )
                )
                hideSnackBar()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        loginButtonState = ButtonState.Normal,
                        showSnackBar = true,
                        snackBarMessage = R.string.login_failed
                    )
                )
                hideSnackBar()
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
                    hideSnackBar()
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
                hideSnackBar()
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

    private fun navigateToHome() {
        bottomNavBarAPI()
    }

    override fun onHideSnackBar() {
        updateState(screenState.value.copy(showSnackBar = false))
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBar) {
                delay(3000)
                updateState(screenState.value.copy(showSnackBar = false))
            }
        }
    }

}