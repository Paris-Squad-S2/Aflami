package com.feature.profile.profileUi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.profile.profileUi.main.InstallSavedAppLanguage
import com.feature.profile.profileUi.navigation.Destination
import com.feature.profile.profileUi.navigation.fromJsonToDestination
import com.feature.profile.profileUi.screen.myRating.MyRatingScreen
import com.feature.profile.profileUi.screen.watchHistory.WatchHistoryScreen
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileDestinationActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val destination = intent.getStringExtra("destination")
            ?.fromJsonToDestination()
            ?: Destination.WatchHistoryScreen

        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                when (destination) {
                    Destination.WatchHistoryScreen -> WatchHistoryScreen()
                    Destination.MyRatingScreen -> MyRatingScreen()
                }
            }
        }
    }
}
