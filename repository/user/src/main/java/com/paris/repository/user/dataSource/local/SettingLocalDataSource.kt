package com.paris.repository.user.dataSource.local

import kotlinx.coroutines.flow.Flow

interface SettingLocalDataSource {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
    suspend fun setOnboardingCompleted()
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setTheme(isDarkTheme: Boolean)
    fun getTheme(): Flow<Boolean>
    suspend fun setRestriction(restriction: String)
    suspend fun getRestriction(): String
}