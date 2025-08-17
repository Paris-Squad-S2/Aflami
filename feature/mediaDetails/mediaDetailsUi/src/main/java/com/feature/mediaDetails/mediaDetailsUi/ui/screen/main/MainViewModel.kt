package com.feature.mediaDetails.mediaDetailsUi.ui.screen.main

import androidx.lifecycle.ViewModel
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
) : ViewModel(){

    fun getLastSelectedAppLanguage() =
        settingsUseCase.getLanguage()
}