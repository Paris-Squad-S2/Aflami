package com.feature.profile.profileUi.screen

import androidx.lifecycle.viewModelScope
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.navigation.ProfileDestinations
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.paris_2.domain.user.usecase.DeleteSessionIdUseCase
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
    private val authenticationFeatureAPI: AuthenticationFeatureAPI,
    private val deleteSessionIdUseCase: DeleteSessionIdUseCase,
    navigator: ProfileNavigator,
) :
    BaseViewModel<ProfileScreenUiState>(ProfileScreenUiState(), navigator), InterActionListener {


    init {
        checkUserLoggedIn()
        getUserName()
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

    private fun getUserName() {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile
                    .copy(
                        name = settingsUseCase.getUserName()
                    )
            )
        )
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
                    isThemeDialogOpen = true
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
                    isLogoutDialogOpen = true,
                    isSettingDialogOpen = false
                )
            )
        )
    }

    override fun onContentRestrictionClicked() {
        TODO("Not yet implemented")
    }

    override fun onAppearanceApplyClicked(appearance: Appearance) {
        TODO("Not yet implemented")
    }


    override fun onLanguageApplyClicked(language: Language) {
        viewModelScope.launch {
            settingsUseCase.setLanguage(language.local)
        }
    }

    override fun onLogoutApplyClicked() {
        tryToExecute(
            onSuccess = {
                authenticationFeatureAPI()
            },
            onError = {},
            execute = {
                deleteSessionIdUseCase()
            }
        )
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
                    isThemeDialogOpen = false
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

    override fun onWatchHistoryClicked() {
        navigate(ProfileDestinations.WatchHistoryScreen)
    }

    override fun onMyRatingClicked() {
        navigate(ProfileDestinations.MyRatingScreen)
    }
}