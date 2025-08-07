package com.feature.profile.profileUi.screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.feature.profile.profileUi.utils.BaseViewModel
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
) :
    BaseViewModel<ProfileScreenUiState>(ProfileScreenUiState()), InterActionListener {


    init {
        checkUserLoggedIn()
        Log.d("TAG", ": isLoggedIn ${isLoggedInUseCase()}")
        viewModelScope.launch {
            settingsUseCase.getLanguage().collect {
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            language = it.toLanguage()
                        )
                    )
                )
            }


        }
    }

    private fun checkUserLoggedIn() {
        updateState(
            screenState.value.copy(
                isLogin = isLoggedInUseCase()
            )
        )
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