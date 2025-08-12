package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SettingLocalDataSource {

    companion object {
        private const val PREF_NAME = "settings"
        private const val LANGUAGE_KEY = "language_code"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_IS_DARK_THEME = "is_dark_theme"
    }

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val _languageFlow = MutableStateFlow(getLanguageSync())
    override fun getLanguage(): Flow<String> = _languageFlow.asStateFlow()

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == LANGUAGE_KEY) {
                val newLanguage = sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
                _languageFlow.value = newLanguage
            }
        }

    init {
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override suspend fun setLanguage(language: String) {
        prefs.edit { putString(LANGUAGE_KEY, language) }
    }

    override fun setOnboardingCompleted() {
        prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, true) }
    }

    override fun isOnboardingCompleted(): Boolean {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    override fun setTheme(isDarkTheme: Boolean) {
        prefs.edit { putBoolean(KEY_IS_DARK_THEME, isDarkTheme) }
    }

    override fun getTheme(): Boolean {
       return prefs.getBoolean(KEY_IS_DARK_THEME, true)
    }

    private fun getLanguageSync(): String {
        return prefs.getString(LANGUAGE_KEY, "en") ?: "en"
    }

}
