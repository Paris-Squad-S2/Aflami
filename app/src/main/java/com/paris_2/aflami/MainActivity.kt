package com.paris_2.aflami

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.authentication.usecases.GuestLoginUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val guestLoginUseCase: GuestLoginUseCase by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                AppScaffold()
            }
        }
        lifecycleScope.launch {
            try {
                val session = guestLoginUseCase()
                Log.d("AuthTest", "Session: $session")
            } catch (e: Exception) {
                Log.e("AuthTest", "Login failed: ${e.message}")
            }
        }
    }
}
