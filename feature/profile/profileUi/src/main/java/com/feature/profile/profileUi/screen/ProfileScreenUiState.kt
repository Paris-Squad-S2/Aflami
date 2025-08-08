package com.feature.profile.profileUi.screen

import com.paris_2.aflami.designsystem.R

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
    val contentRestriction: ContentRestriction = ContentRestriction.STRICT,
    val isContentRestrictionDialogOpen: Boolean = false,
    val isLogoutDialogOpen: Boolean = false,
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
    STRICT,
    MODERATE,
    OFF
}

fun String.toLanguage(): Language {
    return if (this == "en") Language.ENGLISH else Language.ARABIC
}