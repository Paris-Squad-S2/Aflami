package com.feature.mediaDetails.mediaDetailsUi.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavGraph
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.fromJsonToMediaDetailsDestination
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.main.InstallSavedAppLanguage
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MediaDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var authenticationFeatureAPI: AuthenticationFeatureAPI

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    @Inject
    lateinit var navigator: MediaDetailsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mediaDetailsDestination = intent.getStringExtra(MediaDetailsFeatureAPIImpl.MEDIA_DETAILS_DESTINATION)
            ?.fromJsonToMediaDetailsDestination()
            ?: MediaDetailsDestinations.MovieDetailsScreen(0)


        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                MediaDetailsNavGraph(
                    navigator = navigator,
                    authenticationFeatureAPI = authenticationFeatureAPI,
                    mediaDetailsDestination = mediaDetailsDestination
                )
            }
        }
    }

}