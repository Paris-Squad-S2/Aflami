package com.paris_2.repository.user.repository

import android.util.Log
import com.paris_2.domain.user.repository.LanguageRepository
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageRepositoryImp(
    private val languageLocalDataSource: LanguageLocalDataSourceRepository,
) : LanguageRepository {
    override fun getLanguage(): Flow<String> {
        var r = ""
        val l = languageLocalDataSource.getLanguage().map { language ->
           r = language
        }
        Log.d("TAG", "getLanguage:from LocalDS ${r} ")
        return languageLocalDataSource.getLanguage().map { language ->
            language
        }
    }

    override suspend fun setLanguage(language: String) {
        Log.d("TAG", "setLanguageRepositoryImp:${language} ")
        languageLocalDataSource.setLanguage(language = language)
    }

}