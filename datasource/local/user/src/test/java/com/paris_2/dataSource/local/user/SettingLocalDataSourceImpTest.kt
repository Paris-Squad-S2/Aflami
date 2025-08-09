package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SettingLocalDataSourceImpTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dataSource: SettingLocalDataSourceImp

    @BeforeEach
    fun setUp() {
        context = mockk()
        sharedPreferences = mockk(relaxed = true)
        editor = mockk(relaxed = true)

        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences

        // This is the real method, no lambda
        every { sharedPreferences.edit() } returns editor

        // Also mock putString/putBoolean chain
        every { editor.putString(any(), any()) } returns editor
        every { editor.putBoolean(any(), any()) } returns editor

        // Default stubbing for getString/getBoolean
        every { sharedPreferences.getString("language_code", any()) } returns "en"
        every { sharedPreferences.getBoolean("onboarding_completed", any()) } returns false

        dataSource = SettingLocalDataSourceImp(context)
    }

    @Test
    fun `setLanguage should save language code to SharedPreferences`() = runTest {
        dataSource.setLanguage("ar")

        verify {
            editor.putString("language_code", "ar")
            editor.apply()
        }
    }

    @Test
    fun `getLanguage should emit initial value from SharedPreferences`() = runTest {
        every { sharedPreferences.getString("language_code", any()) } returns "ar"

        val newDataSource = SettingLocalDataSourceImp(context)

        assertEquals("ar", newDataSource.getLanguage().first())
    }

    @Test
    fun `setOnboardingCompleted should save true`() {
        dataSource.setOnboardingCompleted()

        verify {
            editor.putBoolean("onboarding_completed", true)
            editor.apply()
        }
    }

    @Test
    fun `isOnboardingCompleted should return stored value`() {
        every { sharedPreferences.getBoolean("onboarding_completed", false) } returns true
        assertTrue(dataSource.isOnboardingCompleted())

        every { sharedPreferences.getBoolean("onboarding_completed", false) } returns false
        assertFalse(dataSource.isOnboardingCompleted())
    }
}
