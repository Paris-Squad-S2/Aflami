package com.feature.profile.profileUi.screen.profile

import com.paris.aflami.designsystem.R

data class ProfileScreenUiState(
    val profile: ProfileUIState = ProfileUIState(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isLogin: Boolean = false,
)

data class ProfileUIState(
    val name: String = "",
    val points: Int = 0,
    val theme: Appearance = Appearance.DARK,
    val isThemeDialogOpen: Boolean = false,
    val language: Language = Language.ENGLISH,
    val isLanguageDialogOpen: Boolean = false,
    val isSettingDialogOpen: Boolean = false,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val isContentRestrictionDialogOpen: Boolean = false,
    val isLogoutDialogOpen: Boolean = false,
    val accountId: Int = -1,
)

enum class Appearance(val display: Int) {
    DARK(R.string.dark),
    LIGHT(R.string.light)
}

enum class Language(val local: String) {
    ENGLISH("en"),
    ARABIC("ar")
}

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}

fun String.toLanguage(): Language {
    return if (this == "en") Language.ENGLISH else Language.ARABIC
}