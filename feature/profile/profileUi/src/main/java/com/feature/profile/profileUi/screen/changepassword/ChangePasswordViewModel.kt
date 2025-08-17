package com.feature.profile.profileUi.screen.changepassword

import com.feature.profile.profileUi.common.BaseViewModel
import com.paris_2.domain.user.usecase.GetForgetPasswordUrlUseCase
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