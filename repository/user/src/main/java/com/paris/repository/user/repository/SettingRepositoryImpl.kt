package com.paris.repository.user.repository

import com.paris.domain.user.repository.SettingRepository
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl(
    private val settingLocalDataSource: SettingLocalDataSource,
) : SettingRepository {
    override fun getLanguage(): Flow<String> {
        return settingLocalDataSource.getLanguage().map { language ->
            language
        }
    }

    override suspend fun setLanguage(language: String) =
        settingLocalDataSource.setLanguage(language = language)

    override suspend fun setOnboardingCompleted() = settingLocalDataSource.setOnboardingCompleted()

    override suspend fun isOnboardingCompleted() = settingLocalDataSource.isOnboardingCompleted()

    override suspend fun setTheme(isDarkTheme: Boolean) =
        settingLocalDataSource.setTheme(isDarkTheme)

    override fun getTheme() = settingLocalDataSource.getTheme()

    override suspend fun setRestriction(restriction: String) =
        settingLocalDataSource.setRestriction(restriction)

    override suspend fun getRestriction() = settingLocalDataSource.getRestriction()

}