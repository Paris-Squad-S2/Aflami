package com.feature.profile.profileUi.screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.navigation.Destination
import com.feature.profile.profileUi.navigation.navigateDestination
import com.paris_2.domain.game.usecases.GetUserPointUseCase
import com.paris_2.domain.user.usecase.DeleteSessionIdUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val authenticationFeatureAPI: AuthenticationFeatureAPI,
    private val deleteSessionIdUseCase: DeleteSessionIdUseCase,
    private val getUserPointsUseCase: GetUserPointUseCase,
    ) :
    BaseViewModel<ProfileScreenUiState>(ProfileScreenUiState()), InterActionListener {


    init {
        checkUserLoggedIn()
        getUserName()
        getRestriction()
        getUserPoints()
        viewModelScope.launch {
            settingsUseCase.getLanguage().collect {
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            language = it.toLanguage(),
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
    private fun getUserPoints() {
        tryToCollect(
            flow = getUserPointsUseCase(),
            onEach = { points ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            points = points
                        )
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error
                    )
                )
            }
        )
    }

    private fun getUserName() {
        viewModelScope.launch{
            updateState(
                screenState.value.copy(
                    profile = screenState.value.profile.copy(
                            name = settingsUseCase.getUserName()
                        )
                )
            )
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

        tryToExecute(
            execute = { settingsUseCase.isDarkTheme() },
            onSuccess = ::onChooseAppearanceClickedSuccess,
            onError = ::onChooseAppearanceClickedError
        )
        viewModelScope.launch {
            settingsUseCase.isDarkTheme().collectLatest { isDark ->
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

    private fun onChooseAppearanceClickedSuccess(isDark: Flow<Boolean>) {
        viewModelScope.launch {
            isDark.collectLatest { isDark ->
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

    private fun onChooseAppearanceClickedError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
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
        tryToExecute(
            execute = { settingsUseCase.setTheme(appearance == Appearance.DARK) },
            onError = ::onAppearanceApplyClickedError
        )
    }

    private fun onAppearanceApplyClickedError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
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
        tryToExecute(
            execute = { settingsUseCase.setRestriction(contentRestriction.name) },
            onError = ::onRestrictionSelectedError
        )
    }

    private fun onRestrictionSelectedError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error
            )
        )
    }

    override fun onWatchHistoryClicked(context: Context) {
        navigateDestination(context, Destination.WatchHistoryScreen)
    }

    override fun onMyRatingClicked(context: Context) {
        navigateDestination(context, Destination.MyRatingScreen)
    }
}