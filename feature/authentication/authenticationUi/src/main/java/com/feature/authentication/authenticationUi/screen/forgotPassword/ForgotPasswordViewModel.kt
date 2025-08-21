package com.feature.authentication.authenticationUi.screen.forgotPassword

import com.paris.domain.user.usecase.auth.GetForgetPasswordUrlUseCase
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    navigator: AuthenticationNavigator,
    getForgetPasswordUrlUseCase: GetForgetPasswordUrlUseCase
) : BaseViewModel<ForgotPasswordUIState>(
    initialState = ForgotPasswordUIState(),
    navigator = navigator
) {

    init {
        updateState(
            screenState.value.copy(
                resetPasswordUrl = getForgetPasswordUrlUseCase()
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }
}