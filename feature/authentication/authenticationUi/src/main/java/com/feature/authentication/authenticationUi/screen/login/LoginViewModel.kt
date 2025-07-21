package com.feature.authentication.authenticationUi.screen.login

import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.aflami.designsystem.components.ButtonState

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isErrorPassword: Boolean = false,
    val buttonState: ButtonState = ButtonState.Normal
)

class LoginViewModel : BaseViewModel<LoginUIState>(LoginUIState()), LoginScreenInteractionListener {

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
        TODO("Not yet implemented")
    }

    override fun onClickForgotPassword() {
        TODO("Not yet implemented")
    }

    override fun onClickCreateAccount() {
        TODO("Not yet implemented")
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