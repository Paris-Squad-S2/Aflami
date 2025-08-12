package com.feature.profile.profileUi.screen.changepassword

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.feature.profile.profileUi.screen.main.InstallSavedAppLanguage
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme { ChangePasswordWebViewScreen() }
        }
    }
}