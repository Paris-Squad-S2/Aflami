package com.paris_2.repository.user.repository

import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class SettingRepositoryImplTest {

    private lateinit var localDataSource: SettingLocalDataSource
    private lateinit var repository: SettingRepositoryImpl

    @BeforeEach
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        repository = SettingRepositoryImpl(localDataSource)
    }

    @Test
    fun `getLanguage should return flow value`() = runTest {
        every { localDataSource.getLanguage() } returns flowOf("ar")

        val result = repository.getLanguage()

        assertEquals("ar", result.first())
    }

    @Test
    fun `setLanguage should call local data source`() = runTest {
        coEvery { localDataSource.setLanguage("fr") } just Runs

        repository.setLanguage("fr")

        coVerify { localDataSource.setLanguage("fr") }
    }

    @Test
    fun `setOnboardingCompleted should call local data source`() = runTest {
        coEvery { localDataSource.setOnboardingCompleted() } just Runs

        repository.setOnboardingCompleted()

        coVerify { localDataSource.setOnboardingCompleted() }
    }

    @Test
    fun `isOnboardingCompleted should return true`() {
        every { localDataSource.isOnboardingCompleted() } returns true

        val result = repository.isOnboardingCompleted()

        assertTrue(result)
    }

    @Test
    fun `isOnboardingCompleted should return false`() {
        every { localDataSource.isOnboardingCompleted() } returns false

        val result = repository.isOnboardingCompleted()

        assertFalse(result)
    }
}