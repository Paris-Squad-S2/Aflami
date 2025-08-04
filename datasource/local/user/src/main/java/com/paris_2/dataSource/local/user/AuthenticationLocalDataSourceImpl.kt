package com.paris_2.dataSource.local.user

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext

class AuthenticationLocalDataSourceImpl(
    @ApplicationContext context: Context
) : AuthenticationLocalDataSource {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveSessionId(sessionId: String) {
        prefs.edit { putString(KEY_SESSION_ID, sessionId) }
    }

    override fun getSessionId(): String? {
        return prefs.getString(KEY_SESSION_ID, null)
    }
    override fun isLoggedIn(): Boolean {
        val session = getSessionId()
        val isGuest = prefs.getBoolean(KEY_IS_GUEST, false)
        return !session.isNullOrBlank() && !isGuest
    }

    override fun setIsGuest(isGuest: Boolean) {
        prefs.edit { putBoolean(KEY_IS_GUEST, isGuest) }
    }

    override fun isGuest(): Boolean {
        return prefs.getBoolean(KEY_IS_GUEST, false)
    }

    override fun hasAnySession(): Boolean {
        return !getSessionId().isNullOrBlank()
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_IS_GUEST = "is_guest"
    }
}