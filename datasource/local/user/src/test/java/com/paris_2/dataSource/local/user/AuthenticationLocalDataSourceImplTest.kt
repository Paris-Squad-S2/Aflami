package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthenticationLocalDataSourceImplTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dataSource: AuthenticationLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        context = mockk()
        sharedPreferences = mockk()
        editor = mockk(relaxed = true)

        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.edit() } answers {
            val action = args[0] as SharedPreferences.Editor.() -> Unit
            editor.action()
            editor
        }
        every { sharedPreferences.edit() } returns editor

        dataSource = AuthenticationLocalDataSourceImpl(context)
    }

    @Test
    fun `saveSessionId should save session id in SharedPreferences`() {
        val sessionId = "test_session_id"
        every { editor.putString("session_id", sessionId) } returns editor
        dataSource.saveSessionId(sessionId)
        verify { editor.putString("session_id", sessionId) }
    }

    @Test
    fun `getSessionId should return session id from SharedPreferences`() {
        val sessionId = "test_session_id"
        every { sharedPreferences.getString("session_id", null) } returns sessionId
        val result = dataSource.getSessionId()
        assertEquals(sessionId, result)
    }

    @Test
    fun `isLoggedIn should return true when sessionId exists and isGuest is false`() {
        every { sharedPreferences.getString("session_id", null) } returns "real_session"
        every { sharedPreferences.getBoolean("is_guest", false) } returns false

        val result = dataSource.isLoggedIn()

        assertEquals(true, result)
    }

    @Test
    fun `isLoggedIn should return false when sessionId is null`() {
        every { sharedPreferences.getString("session_id", null) } returns null
        every { sharedPreferences.getBoolean("is_guest", false) } returns false

        val result = dataSource.isLoggedIn()

        assertEquals(false, result)
    }

    @Test
    fun `isLoggedIn should return false when user is guest`() {
        every { sharedPreferences.getString("session_id", null) } returns "guest_session"
        every { sharedPreferences.getBoolean("is_guest", false) } returns true

        val result = dataSource.isLoggedIn()

        assertEquals(false, result)
    }

    @Test
    fun `setIsGuest should save boolean in SharedPreferences`() {
        every { editor.putBoolean("is_guest", true) } returns editor
        dataSource.setIsGuest(true)
        verify { editor.putBoolean("is_guest", true) }
    }

    @Test
    fun `isGuest should return correct value from SharedPreferences`() {
        every { sharedPreferences.getBoolean("is_guest", false) } returns true
        val result = dataSource.isGuest()
        assertEquals(true, result)
    }

    @Test
    fun `hasAnySession should return true when sessionId is not null or blank`() {
        every { sharedPreferences.getString("session_id", null) } returns "some_session"
        val result = dataSource.hasAnySession()
        assertEquals(true, result)
    }

    @Test
    fun `hasAnySession should return false when sessionId is null`() {
        every { sharedPreferences.getString("session_id", null) } returns null
        val result = dataSource.hasAnySession()
        assertEquals(false, result)
    }

    @Test
    fun `hasAnySession should return false when sessionId is blank`() {
        every { sharedPreferences.getString("session_id", null) } returns " "
        val result = dataSource.hasAnySession()
        assertEquals(false, result)
    }

    @Test
    fun `saveUserName should save username in SharedPreferences`() {
        val username = "test_user"
        every { editor.putString("user_name", username) } returns editor

        dataSource.saveUserName(username)

        verify { editor.putString("user_name", username) }
    }

    @Test
    fun `getUserName should return username from SharedPreferences`() {
        every { sharedPreferences.getString("user_name", "") } returns "test_user"

        val result = dataSource.getUserName()

        assertEquals("test_user", result)
    }

    @Test
    fun `getUserName should return empty string when username is null`() {
        every { sharedPreferences.getString("user_name", "") } returns null

        val result = dataSource.getUserName()

        assertEquals("", result)
    }

    @Test
    fun `deleteSessionId should set session_id to null and return true if no session remains`() {

        every { editor.putString("session_id", null) } returns editor
        every { sharedPreferences.getString("session_id", null) } returns null


        val result = dataSource.deleteSessionId()


        assertTrue(result)
    }

    @Test
    fun `deleteSessionId should return false if session ID still exists after deletion attempt`() {
        every { editor.putString("session_id", null) } returns editor
        every { sharedPreferences.getString("session_id", null) } returns "leftover_session"

        val result = dataSource.deleteSessionId()

        assertFalse(result) 
    }
}
