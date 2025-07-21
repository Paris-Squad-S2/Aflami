package com.feature.authentication.authenticationUi.screen.login

import androidx.navigation.NavOptions
import com.feature.authentication.authenticationApi.AuthenticationDestinations
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.ButtonState

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isErrorPassword: Boolean = false,
    val buttonState: ButtonState = ButtonState.Normal
)

class LoginViewModel(
    val appNavigator : AppNavigator
) : BaseViewModel<LoginUIState>(LoginUIState()), LoginScreenInteractionListener {

    init {
        updateStateButton()
    }
    override fun onUsernameChange(username: String) {
        emitState(newState = screenState.value.copy(username = username))
        updateStateButton()
    }

    override fun onPasswordChange(password: String) {
        emitState(newState = screenState.value.copy(password = password))
        updateStateButton()
    }

    override fun onShowPasswordChange(showPassword: Boolean) {
        emitState(newState = screenState.value.copy(showPassword = !showPassword))
    }

    override fun onClickLogin() {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun onClickCreateAccount() {
        navigate(
            AuthenticationDestinations.RegisterWebViewScreen
        )
    }


    private fun updateStateButton() {
        val newButtonState = if (screenState.value.username.isEmpty() ||
            screenState.value.password.isEmpty() ||
            screenState.value.isErrorPassword) {
            ButtonState.Disabled
        } else {
            ButtonState.Normal
        }
        emitState(screenState.value.copy(buttonState = newButtonState))
    }

}