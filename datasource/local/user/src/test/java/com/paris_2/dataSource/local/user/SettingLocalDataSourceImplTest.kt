package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SettingLocalDataSourceImpTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataSource: SettingLocalDataSourceImpl
    private lateinit var tempFile: File
    private val context = mockk<Context>()

    @BeforeEach
    fun setUp() {
        tempFile = File.createTempFile("test-datastore-${System.nanoTime()}", ".preferences_pb")
        if (tempFile.exists()) tempFile.delete()
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { tempFile }
        )
        dataSource = SettingLocalDataSourceImpl(dataStore, context)
    }

    @AfterEach
    fun tearDown() {
        if (tempFile.exists()) {
            tempFile.delete()
        }
    }

    @Test
    fun `setLanguage should save language code to DataStore`() = runTest {
        dataSource.setLanguage("ar")
        val language = dataSource.getLanguage().first()
        assertEquals("ar", language)
    }

    @Test
    fun `getLanguage should emit initial value from DataStore`() = runTest {
        val language = dataSource.getLanguage().first()
        assertEquals("en", language)
    }

    @Test
    fun `setOnboardingCompleted should save true`() = runTest {
        dataSource.setOnboardingCompleted()
        val completed = dataSource.isOnboardingCompleted()
        assertTrue(completed)
    }

    @Test
    fun `isOnboardingCompleted should return stored value`() = runTest {
        assertFalse(dataSource.isOnboardingCompleted())
        dataSource.setOnboardingCompleted()
        assertTrue(dataSource.isOnboardingCompleted())
    }

    @Test
    fun `setTheme should save dark mode preference to DataStore`() = runTest {
        dataSource.setTheme(true)


        val isDarkTheme = dataSource.getTheme().first()

        assertTrue(isDarkTheme)
    }

    @Test
    fun `setRestriction should save restriction level to DataStore`() = runTest {
        // When
        dataSource.setRestriction("Moderate")

        // Then
        val restriction = dataSource.getRestriction()
        assertEquals("Moderate", restriction)
    }

    @Test
    fun `getRestriction should return default STRICT if not set`() = runTest {
        // When
        val restriction = dataSource.getRestriction()

        // Then
        assertEquals("Strict", restriction)
    }
}
