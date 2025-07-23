package com.paris_2.dataSource.local.authentication

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource

class AuthenticationLocalDataSourceImpl(context: Context) : AuthenticationLocalDataSource {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveSessionId(sessionId: String) {
        prefs.edit { putString(KEY_SESSION_ID, sessionId) }
    }

    override fun getSessionId(): String? {
        return prefs.getString(KEY_SESSION_ID, null)
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_SESSION_ID = "session_id"
    }
}