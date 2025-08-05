package com.feature.profile.profileUi.screen

import com.paris_2.aflami.designsystem.R

data class ProfileScreenUiState(
    val profile: ProfileUIState = ProfileUIState(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isLogin: Boolean = true,
)

data class ProfileUIState(
    val name: String = "",
    val points: Int = 0,
    val appearance: Appearance = Appearance.DARK,
    val isAppearanceDialogOpen: Boolean = false,
    val language: Language = Language.ENGLISH,
    val isLanguageDialogOpen: Boolean = false,
    val contentRestriction: ContentRestriction = ContentRestriction.STRICT,
    val isContentRestrictionDialogOpen: Boolean = false,
    val isLogoutDialogOpen: Boolean = false,
)

enum class Appearance(val display: Int) {
    DARK(R.string.dark),
    LIGHT(R.string.light)
}

enum class Language(val display: Int) {
    ENGLISH(R.string.english),
    ARABIC(R.string.arabic)
}

enum class ContentRestriction() {
    STRICT,
    MODERATE,
    OFF
}