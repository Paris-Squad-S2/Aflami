package com.paris_2.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authenticationFeatureAPI: AuthenticationFeatureAPI = get()
        setContent {
            AflamiTheme {
                authenticationFeatureAPI()
            }
        }
    }
}
