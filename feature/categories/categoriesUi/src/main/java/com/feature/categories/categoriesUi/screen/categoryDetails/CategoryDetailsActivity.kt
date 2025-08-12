package com.feature.categories.categoriesUi.screen.categoryDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.fromJsonToCategoryUiState
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val category = intent.getStringExtra("category")
            ?.fromJsonToCategoryUiState()
            ?: CategoryUiState.getDefault()


        setContent {
            AflamiTheme {
                CategoryDetailsScreen(
                    category = category,
                )
            }
        }
    }
}