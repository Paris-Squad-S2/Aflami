package com.paris_2.domain.user.repository

import java.util.Locale


interface LanguageRepository {
    fun getLanguage(): Locale
   suspend fun setLanguage(language: Locale)
}