package com.feature.profile.profileUi.screen

import androidx.lifecycle.viewModelScope
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.navigation.ProfileDestinations
import com.paris_2.domain.user.usecase.DeleteSessionIdUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val authenticationFeatureAPI: AuthenticationFeatureAPI,
    private val deleteSessionIdUseCase: DeleteSessionIdUseCase,
) :
    BaseViewModel<ProfileScreenUiState>(ProfileScreenUiState()), InterActionListener {


    init {
        checkUserLoggedIn()
        getUserName()
        getRestriction()
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

    private fun getRestriction() {
        viewModelScope.launch {
            val restriction = settingsUseCase.getRestriction()
            updateState(
                screenState.value.copy(
                    profile = screenState.value.profile.copy(
                        contentRestriction = ContentRestriction.valueOf(restriction)
                    )
                )
            )
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

        viewModelScope.launch {
            settingsUseCase.isDarkTheme().collectLatest {isDark ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            isThemeDialogOpen = true,
                            theme = if (isDark) Appearance.DARK else Appearance.LIGHT
                        )
                    )
                )
            }

        }
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
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    isContentRestrictionDialogOpen = !screenState.value.profile.isContentRestrictionDialogOpen
                )
            )
        )
    }

    override fun onAppearanceApplyClicked(appearance: Appearance) {
        updateState(
            screenState.value.copy(
                profile = screenState.value.profile.copy(
                    theme = appearance
                )
            )
        )
        viewModelScope.launch {
            settingsUseCase.setTheme(appearance == Appearance.DARK)
        }

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

    override fun onRestrictionSelected(contentRestriction: ContentRestriction) {
            viewModelScope.launch {
                settingsUseCase.setRestriction(contentRestriction.name)
            }
    }

    override fun onWatchHistoryClicked() {
        navigate(ProfileDestinations.WatchHistoryScreen)
    }

    override fun onMyRatingClicked() {
        navigate(ProfileDestinations.MyRatingScreen)
    }
}