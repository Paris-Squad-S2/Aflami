package com.paris.domain.user.usecase

import com.paris.domain.user.repository.SettingRepository
import com.paris.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ManageSettingsUseCase(
    private val settingRepository: SettingRepository,
    private val userRepository: UserRepository,
) {
    fun getLanguage(): Flow<String> = settingRepository.getLanguage()
    suspend fun setLanguage(language: String) = settingRepository.setLanguage(language)
    fun getUserName(): String = userRepository.getUserName()
    fun isDarkTheme(): Flow<Boolean> = settingRepository.getTheme()
    suspend fun setTheme(isDarkTheme: Boolean) = settingRepository.setTheme(isDarkTheme)
    suspend fun setRestriction(restriction: String) = settingRepository.setRestriction(restriction)
    suspend fun getRestriction() = settingRepository.getRestriction()
}