package com.feature.profile.profileUi.screen

import android.content.Context
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
        getTheme()
        checkUserLoggedIn()
        getUserName()
        getRestriction()
        getUserPoints()
        getLanguage()
    }

    private fun getTheme() {
        tryToCollect(
            flow = settingsUseCase.isDarkTheme(),
            onEach = { isDark ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            theme = if (isDark) Appearance.DARK else Appearance.LIGHT
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    private fun getLanguage() {
        tryToCollect(
            flow = settingsUseCase.getLanguage(),
            onEach = { language ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            language = language.toLanguage(),
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            },
        )
    }

    private fun getRestriction() {
        tryToExecute(
            execute = settingsUseCase::getRestriction,
            onSuccess = { restriction ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            contentRestriction = ContentRestriction.valueOf(restriction)
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
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
        tryToExecute(
            execute = settingsUseCase::getUserName,
            onSuccess = { userName ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            name = userName
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )

    }

    private fun checkUserLoggedIn() {
        tryToExecute(
            execute = isLoggedInUseCase::invoke,
            onSuccess = { isLoggedIn ->
                updateState(
                    screenState.value.copy(
                        isLogin = isLoggedIn
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
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

        tryToCollect(
            flow = settingsUseCase.isDarkTheme(),
            onEach = { isDark ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            isThemeDialogOpen = true,
                            theme = if (isDark) Appearance.DARK else Appearance.LIGHT
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            },
        )
    }

    private fun onChooseAppearanceClickedSuccess(isDark: Flow<Boolean>) {
        tryToCollect(
            flow = isDark,
            onEach = { isDark ->
                updateState(
                    screenState.value.copy(
                        profile = screenState.value.profile.copy(
                            isThemeDialogOpen = true,
                            theme = if (isDark) Appearance.DARK else Appearance.LIGHT
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            },
        )
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
        tryToExecute(
            execute = { settingsUseCase.setLanguage(language.local) },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onLogoutApplyClicked() {
        tryToExecute(
            execute = {
                deleteSessionIdUseCase()
            },
            onSuccess = {
                authenticationFeatureAPI()
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
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