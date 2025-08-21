package com.feature.authentication.authenticationUi.screen.login

import androidx.annotation.StringRes
import com.feature.authentication.authenticationUi.R
import com.paris.aflami.designsystem.components.ButtonState

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