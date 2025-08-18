package com.feature.home.homeUi.screen.main

import com.paris_2.domain.user.usecase.SettingsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest {

    private val settingsUseCase: SettingsUseCase = mockk()
    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MainViewModel(settingsUseCase = settingsUseCase)
    }

    @Test
    fun `getLastSelectedAppLanguage returns value from useCase`() = runTest {
        // Arrange
        val expectedLanguage = "ar"
        coEvery { settingsUseCase.getLanguage() } returns flowOf(expectedLanguage)

        // Act
        val result = viewModel.getLastSelectedAppLanguage().first()

        // Assert
        assertEquals(expectedLanguage, result)
        coVerify(exactly = 1) { settingsUseCase.getLanguage() }
    }

}