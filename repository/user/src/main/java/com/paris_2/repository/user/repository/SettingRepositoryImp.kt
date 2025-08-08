package com.paris_2.repository.user.repository

import com.paris_2.domain.user.repository.SettingRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingRepositoryImp(
    private val languageLocalDataSource: SettingLocalDataSource,
) : SettingRepository {
    override fun getLanguage(): Flow<String> {
        return languageLocalDataSource.getLanguage().map { language ->
            language
        }
    }

    override suspend fun setLanguage(language: String) {
        languageLocalDataSource.setLanguage(language = language)
    }

}