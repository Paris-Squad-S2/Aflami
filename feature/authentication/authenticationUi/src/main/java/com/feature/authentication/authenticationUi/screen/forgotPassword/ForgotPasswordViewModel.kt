package com.feature.authentication.authenticationUi.screen.forgotPassword

import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.domain.authentication.usecases.GetForgetPasswordUrlUseCase

class ForgotPasswordViewModel(
    getForgetPasswordUrlUseCase: GetForgetPasswordUrlUseCase
) : BaseViewModel<ForgotPasswordUIState>(ForgotPasswordUIState()) {

    init {
        emitState(
            screenState.value.copy(
                resetPasswordUrl = getForgetPasswordUrlUseCase()
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }

}