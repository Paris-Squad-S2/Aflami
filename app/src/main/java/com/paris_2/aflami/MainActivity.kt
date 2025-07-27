package com.paris_2.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /* val authenticationFeatureAPI: AuthenticationFeatureAPI = get()
         val hasAnySessionUseCase : HasAnySessionUseCase = get()
         val appNavigationAPI : AppNavigationAPI = get()
         setContent {
             AflamiTheme {
                 if (hasAnySessionUseCase()){
                     appNavigationAPI()
                 } else {
                     authenticationFeatureAPI()
                 }
             }
         }*/
    }
}