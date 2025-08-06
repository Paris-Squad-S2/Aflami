package com.feature.profile.profileUi.screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.feature.profile.profileUi.utils.BaseViewModel
import com.paris_2.domain.user.usecase.GetLanguageUseCase
import com.paris_2.domain.user.usecase.SetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import java.util.Locale

@Suppress("DEPRECATION")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
) :
    BaseViewModel<ProfileScreenUiState>(ProfileScreenUiState()), InterActionListener {


    init {
        viewModelScope.launch {
            getLanguageUseCase.invoke().collect {
                Log.d("TAG", "ViewModel:$it ")
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            language = it.toLanguage()
                        )
                    )
                )
                Log.d("TAG", "ViewModelState:${screenState.value.profile.language} ")
            }
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
            Log.d("TAG", "onLanguageApplyClicked: $language")
            setLanguageUseCase.invoke(Locale(language.name))
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