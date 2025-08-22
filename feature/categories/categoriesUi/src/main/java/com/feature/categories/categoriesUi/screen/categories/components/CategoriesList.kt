package com.feature.categories.categoriesUi.screen.categories.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.paris.aflami.designsystem.components.CategoryCard

@Composable
fun CategoriesList(
    categories: List<CategoryUiState>,
    onCategoryClick: (Context, CategoryUiState) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(categories) { category ->
            CategoryCard(
                categoryName = stringResource(category.name),
                categoryImage = painterResource(category.icon),
                onCategoryClick = { onCategoryClick(context, category) },
            )
        }
    }
}