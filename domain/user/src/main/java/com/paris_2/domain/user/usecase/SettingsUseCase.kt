package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

class SettingsUseCase(private val languageRepository: LanguageRepository) {
    fun getLanguage(): Flow<String> = languageRepository.getLanguage()
    suspend fun setLanguage(language: String) = languageRepository.setLanguage(language)
}