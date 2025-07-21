package com.feature.authentication.authenticationUi.screen.login

import androidx.annotation.StringRes
import androidx.navigation.NavOptions
import com.feature.authentication.authenticationApi.AuthenticationDestinations
import com.feature.authentication.authenticationUi.R
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.ButtonState

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val buttonState: ButtonState = ButtonState.Normal
)

class LoginViewModel(
    val appNavigator: AppNavigator
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
                appNavigator.navigate(
                    AppDestinations.HomeFeature(),
                    NavOptions.Builder().apply {
                        setPopUpTo(
                            AppDestinations.AuthenticationFeature(),
                            inclusive = true
                        )
                    }.build()
                )
                //TODO handle login
            },
            onError = {
                emitState(
                    screenState.value.copy(
                        errorMessage = R.string.incorrect_password,
                        buttonState = ButtonState.Disabled
                    )
                )
            }
        )
    }

    override fun onClickLoginAsGuest() {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    AppDestinations.HomeFeature(),
                    NavOptions.Builder().apply {
                        setPopUpTo(
                            AppDestinations.AuthenticationFeature(),
                            inclusive = true
                        )
                    }.build()
                )
                //TODO handle guest login
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

}