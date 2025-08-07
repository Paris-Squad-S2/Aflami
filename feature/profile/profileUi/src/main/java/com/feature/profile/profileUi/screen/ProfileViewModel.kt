package com.feature.profile.profileUi.screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.feature.profile.profileUi.utils.BaseViewModel
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    navigator: ProfileNavigator
) :
    BaseViewModel<ProfileScreenUiState>(ProfileScreenUiState(),navigator), InterActionListener {


    init {
        viewModelScope.launch {
            settingsUseCase.getLanguage().collect {
                Log.d("TAG", ": In Init view mode from use case $it")
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            language = it.toLanguage()
                        )
                    )
                )
            }
            Log.d(
                "TAG",
                ": In Init view mode from ViewModel ${screenState.value.profile.language.local}"
            )
        }
    }

    override fun onChooseLanguageClicked() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isLanguageDialogOpen = true
                )
            )
        )
    }

    override fun onChooseAppearanceClicked() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isAppearanceDialogOpen = true
                )
            )
        )
    }

    override fun onSettingClicked() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isSettingDialogOpen = true
                )
            )
        )
    }

    override fun onLogoutClicked() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isLogoutDialogOpen = true
                )
            )
        )
    }

    override fun onContentRestrictionClicked() {
        TODO("Not yet implemented")
    }

    override fun onAppearanceApplyClicked() {
        TODO("Not yet implemented")
    }

    override fun onLanguageApplyClicked(language: Language) {
        viewModelScope.launch {
            settingsUseCase.setLanguage(language.local)
        }
    }

    override fun onLogoutApplyClicked() {
        TODO("Not yet implemented")
    }

    override fun onChangePasswordClicked() {
        TODO("Not yet implemented")
    }

    override fun onSaveContentRestrictionClicked() {
        TODO("Not yet implemented")
    }

    override fun onDismissAppearanceDialog() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isAppearanceDialogOpen = false
                )
            )
        )
    }

    override fun onDismissLanguageDialog() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isLanguageDialogOpen = false
                )
            )
        )
    }

    override fun onDismissSettingDialog() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isSettingDialogOpen = false
                )
            )
        )
    }

    override fun onDismissLogoutDialog() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isLogoutDialogOpen = false
                )
            )
        )
    }

    override fun onDismissContentRestrictionDialog() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isContentRestrictionDialogOpen = false
                )
            )
        )
    }

    override fun onLanguageSelected(language: Language) {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    language = language,
                )
            )
        )
    }
}