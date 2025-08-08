package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingsUseCase(private val settingRepository: SettingRepository) {
    fun getLanguage(): Flow<String> = settingRepository.getLanguage()
    suspend fun setLanguage(language: String) = settingRepository.setLanguage(language)
}