package com.paris_2.aflami.appnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.paris_2.aflami.designsystem.theme.AflamiTheme

class AppNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                AppScaffold()
            }
        }
    }
}