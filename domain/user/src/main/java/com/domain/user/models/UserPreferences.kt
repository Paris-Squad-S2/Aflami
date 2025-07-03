package com.domain.user.models

data class UserPreferences(
    val language: Language,
    val isDarkModeEnabled: ThemeMode,
    val isFirstLaunch: Boolean
)

enum class Language(languagePrefix: String) {
    ENGLISH("en"),
    ARABIC("ar"),
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}