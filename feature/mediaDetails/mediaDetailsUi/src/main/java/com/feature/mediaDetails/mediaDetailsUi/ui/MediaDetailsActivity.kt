package com.feature.mediaDetails.mediaDetailsUi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.fromJsonToMediaDetailsDestination
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavGraph
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigatorEntryPoint
import javax.inject.Inject
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI

@AndroidEntryPoint
class MediaDetailsActivity: ComponentActivity() {
    @Inject
    lateinit var authenticationFeatureAPI: AuthenticationFeatureAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mediaDetailsDestination = intent.getStringExtra("mediaDetailsDestination")
            ?.fromJsonToMediaDetailsDestination()
            ?: MediaDetailsDestinations.MovieDetailsScreen(0)

        val navigator = EntryPointAccessors.fromApplication(
            applicationContext,
            MediaDetailsNavigatorEntryPoint::class.java
        ).navigator()

        setContent {
            AflamiTheme {
                MediaDetailsNavGraph(
                    navigator = navigator,
                    authenticationFeatureAPI = authenticationFeatureAPI,
                    mediaDetailsDestination = mediaDetailsDestination
                )
            }
        }
    }
}