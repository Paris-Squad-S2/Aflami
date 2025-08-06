package com.paris_2.domain.user.repository

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
}