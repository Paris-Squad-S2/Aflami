package com.paris.dataSource.local.user

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class SettingLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) : SettingLocalDataSource {

    override fun getLanguage(): Flow<String> {
        return dataStore.data.mapLatest {
            it[LANGUAGE_KEY] ?: ENGLISH
        }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.setValue(LANGUAGE_KEY, language)
    }

    override suspend fun setOnboardingCompleted() {
        dataStore.setValue(KEY_ONBOARDING_COMPLETED, true)
    }

    override suspend fun isOnboardingCompleted(): Boolean {
        return dataStore.data.mapLatest {
            it[KEY_ONBOARDING_COMPLETED] ?: false
        }.first()
    }

    override suspend fun setTheme(isDarkTheme: Boolean) {
        dataStore.setValue(KEY_IS_DARK_THEME, isDarkTheme)
    }

    override fun getTheme(): Flow<Boolean> {
        return dataStore.data.mapLatest {
            it[KEY_IS_DARK_THEME] ?: true
        }
    }

    override suspend fun setRestriction(restriction: String) {
        dataStore.setValue(RESTRICTION, restriction)
    }

    override suspend fun getRestriction(): String {
        return dataStore.data.mapLatest {
            it[RESTRICTION] ?: STRICT
        }.first()
    }

    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T,
    ) {
        this.edit { preferences ->
            preferences[key] = value
        }
    }

    private companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language_code")
        val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val RESTRICTION = stringPreferencesKey("restriction")
        val KEY_IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        const val ENGLISH = "en"
        const val STRICT = "Strict"
    }

}