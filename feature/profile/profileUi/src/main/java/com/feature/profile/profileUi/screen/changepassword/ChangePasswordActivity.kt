package com.feature.profile.profileUi.screen.changepassword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AflamiTheme { ChangePasswordWebViewScreen() }
        }
    }
}