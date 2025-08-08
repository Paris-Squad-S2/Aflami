package com.paris_2.repository.user.dataSource.local

import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface LanguageLocalDataSourceRepository {
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
}