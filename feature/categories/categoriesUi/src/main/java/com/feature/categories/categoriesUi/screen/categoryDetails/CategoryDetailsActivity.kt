package com.feature.categories.categoriesUi.screen.categoryDetails

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.categories.categoriesUi.screen.main.InstallSavedAppLanguage
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.fromJsonToCategoryUiState
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsUseCase: SettingsUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val category = intent.getStringExtra("category")
            ?.fromJsonToCategoryUiState()
            ?: CategoryUiState.getDefault()

        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                CategoryDetailsScreen(
                    category = category,
                )
            }
        }
    }
}
