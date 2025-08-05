package com.paris_2.repository.user.repository

import com.paris_2.domain.user.repository.LanguageRepository
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguageRepositoryImp(
    private val languageLocalDataSource: LanguageLocalDataSourceRepository,
) : LanguageRepository {
    override fun getLanguage(): Flow<Locale> {
        return languageLocalDataSource.getLanguage().map { language ->
            Locale(language)
        }
    }

    override suspend fun setLanguage(language: Locale) {
        languageLocalDataSource.setLanguage(language = language.language)
    }

}