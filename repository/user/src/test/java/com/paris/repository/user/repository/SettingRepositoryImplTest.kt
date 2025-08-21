package com.paris.repository.user.repository

import com.google.common.truth.Truth.assertThat
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
    fun `isOnboardingCompleted should return true`() = runTest{
        coEvery { localDataSource.isOnboardingCompleted() } returns true

        val result = repository.isOnboardingCompleted()

        assertTrue(result)
    }

    @Test
    fun `isOnboardingCompleted should return false`() = runTest {
        coEvery { localDataSource.isOnboardingCompleted() } returns false

        val result = repository.isOnboardingCompleted()

        assertFalse(result)
    }

    @Test
    fun `setTheme should call local data source with true`() = runTest {
        coEvery { localDataSource.setTheme(true) } just Runs

        repository.setTheme(true)

        coVerify(exactly = 1) { localDataSource.setTheme(true) }
    }

    @Test
    fun `setTheme should call local data source with false`() = runTest {
        coEvery { localDataSource.setTheme(false) } just Runs

        repository.setTheme(false)

        coVerify(exactly = 1) { localDataSource.setTheme(false) }
    }

    @Test
    fun `getTheme should return true`() = runTest {
        every { localDataSource.getTheme() } returns flowOf(true)

        val result = repository.getTheme()

        assertTrue(result.first())
    }

    @Test
    fun `getTheme should return false`() = runTest {
        every { localDataSource.getTheme() } returns flowOf(false)

        val result = repository.getTheme()

        assertFalse(result.first())
    }

    @Test
    fun `setRestriction should call local data source with value`() = runTest {
        coEvery { localDataSource.setRestriction("+18") } just Runs

        repository.setRestriction("+18")

        coVerify(exactly = 1) { localDataSource.setRestriction("+18") }
    }

    @Test
    fun `getRestriction should return value from local data source`() = runTest {
        coEvery { localDataSource.getRestriction() } returns "18+"

        val result = repository.getRestriction()

        assertThat(result).isEqualTo("18+")
    }
}