package com.feature.categories.categoriesUi.navigation

import android.content.Context
import android.content.Intent
import com.feature.categories.categoriesUi.screen.categories.CategoryUiState
import com.feature.categories.categoriesUi.screen.categories.toJson
import com.feature.categories.categoriesUi.screen.categoryDetails.CategoryDetailsActivity

fun navigateToMediaDetails(
    context: Context,
    category: CategoryUiState
) {
    val intent = Intent(context, CategoryDetailsActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtra("category", category.toJson())
    context.startActivity(intent)
}