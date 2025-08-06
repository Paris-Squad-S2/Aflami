package com.paris_2.dataSource.local.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageLocalDataSourceRepositoryImp @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LanguageLocalDataSourceRepository {
    private val LANGUAGE_KEY = stringPreferencesKey("language_code")

    override fun getLanguage(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: "en"
        }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
}
