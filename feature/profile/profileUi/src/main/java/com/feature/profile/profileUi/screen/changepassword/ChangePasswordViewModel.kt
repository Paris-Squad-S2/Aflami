package com.feature.profile.profileUi.screen.changepassword

import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.feature.profile.profileUi.utils.BaseViewModel
import com.paris_2.domain.user.usecase.GetForgetPasswordUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    getForgetPasswordUrlUseCase: GetForgetPasswordUrlUseCase,
    navigator: ProfileNavigator,
) : BaseViewModel<ChangePasswordUiState>(
    initialState = ChangePasswordUiState(),
    navigator = navigator
) {

    init {
        updateState(
            screenState.value.copy(
                resetPasswordUrl = getForgetPasswordUrlUseCase()
            )
        )
    }

}