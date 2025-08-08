package com.paris_2.domain.user.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
    suspend fun setOnboardingCompleted()
    fun isOnboardingCompleted(): Boolean
}