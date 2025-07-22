package com.feature.authentication.authenticationUi.screen.register

import com.feature.authentication.authenticationUi.comon.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterUIState>(RegisterUIState()) {

    init {
        emitState(
            screenState.value.copy(
                registrationUrl = "https://www.themoviedb.org/signup"
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }

}