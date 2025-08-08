package com.paris_2.repository.user.dataSource.local

import kotlinx.coroutines.flow.Flow

interface SettingLocalDataSource {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
    fun setOnboardingCompleted()
    fun isOnboardingCompleted(): Boolean
}