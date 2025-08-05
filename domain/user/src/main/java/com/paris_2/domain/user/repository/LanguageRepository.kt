package com.paris_2.domain.user.repository

import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface LanguageRepository {
    fun getLanguage(): Flow<Locale>
    suspend fun setLanguage(language: Locale)
}