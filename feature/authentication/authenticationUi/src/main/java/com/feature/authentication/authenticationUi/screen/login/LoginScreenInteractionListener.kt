package com.feature.authentication.authenticationUi.screen.login

interface LoginScreenInteractionListener {
    fun onUsernameChange(username: String)
    fun onPasswordChange(password: String)
    fun onShowPasswordChange(showPassword: Boolean)
    fun onClickLogin()
    fun onClickLoginAsGuest()
    fun onClickForgotPassword()
    fun onClickCreateAccount()
    fun onHideSnackBar()
}
