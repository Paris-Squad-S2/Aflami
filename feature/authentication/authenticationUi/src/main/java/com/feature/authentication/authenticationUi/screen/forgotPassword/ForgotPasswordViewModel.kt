package com.feature.authentication.authenticationUi.screen.forgotPassword

import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris_2.domain.authentication.usecase.GetForgetPasswordUrlUseCase
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