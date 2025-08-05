package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.LanguageRepository
import java.util.Locale
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(private val languageRepository: LanguageRepository) {
    suspend operator fun invoke(language: Locale) {
        languageRepository.setLanguage(language)
    }
}