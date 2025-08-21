package com.feature.onboarding.onboardingUi.ui.main

import androidx.lifecycle.ViewModel
import com.paris.domain.user.usecase.ManageSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val manageSettingsUseCase: ManageSettingsUseCase,
) : ViewModel(){

    fun getLastSelectedAppLanguage() =
        manageSettingsUseCase.getLanguage()
}