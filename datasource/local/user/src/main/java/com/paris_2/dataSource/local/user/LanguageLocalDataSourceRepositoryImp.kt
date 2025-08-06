package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.SharedPreferences
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LanguageLocalDataSourceRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
) : LanguageLocalDataSourceRepository {

    companion object {
        private const val PREF_NAME = "settings"
        private const val LANGUAGE_KEY = "language_code"
    }

    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val languageFlow = MutableStateFlow(getLanguageSync())

    override fun getLanguage(): Flow<String> = languageFlow

    override suspend fun setLanguage(language: String) {
        prefs.edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun getLanguageSync(): String {
        return prefs.getString(LANGUAGE_KEY, "en") ?: "en"
    }

}
