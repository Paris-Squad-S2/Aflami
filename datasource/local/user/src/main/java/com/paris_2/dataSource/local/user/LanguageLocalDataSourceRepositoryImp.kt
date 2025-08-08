package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageLocalDataSourceRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
) : LanguageLocalDataSourceRepository {

    companion object {
        private const val PREF_NAME = "settings"
        private const val LANGUAGE_KEY = "language_code"
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

    private fun getLanguageSync(): String {
        return prefs.getString(LANGUAGE_KEY, "en") ?: "en"
    }


}
