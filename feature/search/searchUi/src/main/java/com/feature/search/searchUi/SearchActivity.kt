package com.feature.search.searchUi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.search.searchUi.navigation.SearchNavGraph
import com.feature.search.searchUi.screen.main.InstallSavedAppLanguage
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.ManageSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var manageSettingsUseCase: ManageSettingsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(manageSettingsUseCase.isDarkTheme()) {
                SearchNavGraph()
            }
        }
    }
}