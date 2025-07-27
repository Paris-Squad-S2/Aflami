package com.paris_2.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.paris_2.aflami.appnavigation.AppNavigationAPI
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.authentication.usecase.HasAnySessionUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var authenticationFeatureAPI: AuthenticationFeatureAPI
    @Inject lateinit var hasAnySessionUseCase: HasAnySessionUseCase
    @Inject lateinit var appNavigationAPI: AppNavigationAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                if (hasAnySessionUseCase()){
                    appNavigationAPI()
                } else {
                    authenticationFeatureAPI()
                }
            }
        }
    }
}