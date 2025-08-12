package com.paris_2.dataSource.local.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SettingLocalDataSourceImpTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataSource: SettingLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { File.createTempFile("test-datastore", ".preferences_pb") }
        )
        dataSource = SettingLocalDataSourceImpl(dataStore)
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
