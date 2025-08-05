package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.LanguageRepository
import java.util.Locale

class GetLanguageUseCase(private val languageRepository: LanguageRepository) {
    operator fun invoke(): Locale = languageRepository.getLanguage()
}