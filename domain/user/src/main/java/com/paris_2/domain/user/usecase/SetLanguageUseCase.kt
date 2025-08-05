package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.LanguageRepository
import java.util.Locale

class SetLanguageUseCase(private val languageRepository: LanguageRepository) {
    suspend operator fun invoke(language: Locale) {
        languageRepository.setLanguage(language)
    }
}