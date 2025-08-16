package com.paris_2.dataSource.local.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
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

    @BeforeEach
    fun setUp() {
        tempFile = File.createTempFile("test-datastore-${System.nanoTime()}", ".preferences_pb")
        if (tempFile.exists()) tempFile.delete()
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { tempFile }
        )
        dataSource = SettingLocalDataSourceImpl(dataStore)
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
}
