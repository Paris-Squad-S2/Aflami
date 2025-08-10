package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.SettingRepository
import com.paris_2.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    private val userRepository: UserRepository,
) {
    fun getLanguage(): Flow<String> = settingRepository.getLanguage()
    suspend fun setLanguage(language: String) = settingRepository.setLanguage(language)
    fun getUserName(): String = userRepository.getUserName()
    fun isDarkTheme(): Boolean = settingRepository.getTheme()
    fun setTheme(isDarkTheme: Boolean) = settingRepository.setTheme(isDarkTheme)
}