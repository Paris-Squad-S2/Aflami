package com.feature.authentication.authenticationUi.screen.forgotPassword

import com.feature.authentication.authenticationUi.comon.BaseViewModel

class ForgotPasswordViewModel : BaseViewModel<ForgotPasswordUIState>(ForgotPasswordUIState()) {

    init {
        emitState(
            screenState.value.copy(
                resetPasswordUrl = "https://www.themoviedb.org/reset-password"
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }

}