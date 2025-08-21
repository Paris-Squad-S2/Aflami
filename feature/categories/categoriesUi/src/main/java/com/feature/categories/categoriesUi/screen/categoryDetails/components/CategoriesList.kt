package com.feature.categories.categoriesUi.screen.categoryDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun CategoriesList(
    categories: List<CategoryUiState>,
    selectedCategory: CategoryUiState,
    onCategorySelected: (CategoryUiState) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Theme.colors.surface)
            .padding(horizontal = 16.dp)
            .zIndex(1f)
    ) {
        items(categories) { category ->
            Chips(
                title = stringResource(category.name),
                icon = ImageVector.vectorResource(
                    CategoryResourceMapper.getResourceId(
                        category.category
                    )
                ),
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
            )
        }
    }
}