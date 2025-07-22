package com.feature.authentication.authenticationUi.screen.register

import com.feature.authentication.authenticationUi.comon.BaseViewModel
import com.paris_2.domain.authentication.usecases.GetRegisterUrlUseCase

class RegisterViewModel(
    getRegisterUrlUseCase: GetRegisterUrlUseCase
) : BaseViewModel<RegisterUIState>(RegisterUIState()) {

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