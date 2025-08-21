package com.feature.profile.profileUi.screen.changepassword

import com.feature.profile.profileUi.common.BaseViewModel
import com.paris.domain.user.usecase.auth.GetForgetPasswordUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    getForgetPasswordUrlUseCase: GetForgetPasswordUrlUseCase,
) : BaseViewModel<ChangePasswordUiState>(
    initialState = ChangePasswordUiState()
) {

    init {
        updateState(
            screenState.value.copy(
                resetPasswordUrl = getForgetPasswordUrlUseCase()
            )
        )
    }

}