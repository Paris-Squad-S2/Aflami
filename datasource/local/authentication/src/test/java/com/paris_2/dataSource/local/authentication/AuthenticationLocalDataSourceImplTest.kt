package com.paris_2.dataSource.local.authentication

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthenticationLocalDataSourceImplTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dataSource: AuthenticationLocalDataSourceImpl

    @Before
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
}