package com.feature.authentication.authenticationUi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavGraph
import com.feature.authentication.authenticationUi.screen.main.InstallSavedAppLanguage
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.ManageSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    @Inject
    lateinit var manageSettingsUseCase: ManageSettingsUseCase

    @Inject
    lateinit var navigator: AuthenticationNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(manageSettingsUseCase.isDarkTheme()) {
                AuthenticationNavGraph(navigator = navigator)
            }
        }
    }

}