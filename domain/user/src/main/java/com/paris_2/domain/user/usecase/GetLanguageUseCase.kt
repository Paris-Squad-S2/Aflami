package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class GetLanguageUseCase (private val languageRepository: LanguageRepository) {
    operator fun invoke(): Flow<Locale> = languageRepository.getLanguage()
}