package com.paris.aflami.main

import androidx.lifecycle.ViewModel
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
) : ViewModel(){

    fun getLastSelectedAppLanguage() =
        settingsUseCase.getLanguage()
}