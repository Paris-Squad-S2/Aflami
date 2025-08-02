package com.feature.authentication.authenticationUi.screen.register

import com.domain.user.usecase.GetRegisterUrlUseCase
import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    getRegisterUrlUseCase: GetRegisterUrlUseCase,
    navigator: AuthenticationNavigator
) : BaseViewModel<RegisterUIState>(RegisterUIState(), navigator) {

    init {
        updateState(
            screenState.value.copy(
                registrationUrl = getRegisterUrlUseCase()
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }
}