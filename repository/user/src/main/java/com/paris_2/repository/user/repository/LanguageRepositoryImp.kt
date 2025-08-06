package com.paris_2.repository.user.repository

import android.util.Log
import com.paris_2.domain.user.repository.LanguageRepository
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguageRepositoryImp(
    private val languageLocalDataSource: LanguageLocalDataSourceRepository,
) : LanguageRepository {
    override fun getLanguage(): Flow<String> {
        return languageLocalDataSource.getLanguage().map { language ->
            language
        }
    }

    override suspend fun setLanguage(language: String) {
        Log.d("TAG", "setLanguageRepositoryImp:${language} ")
        languageLocalDataSource.setLanguage(language = language)
    }

}