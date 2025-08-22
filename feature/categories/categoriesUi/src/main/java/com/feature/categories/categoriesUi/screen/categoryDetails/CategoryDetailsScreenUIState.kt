package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.paging.PagingData
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.Status
import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.paris.aflami.designsystem.R as RDesignSystem

data class CategoryDetailsScreenUIState(
    val categoryDetailsUIState: CategoryDetailsUIState = CategoryDetailsUIState(),
)

data class CategoryDetailsUIState(
    val selectedTabIndex: Int = 0,
    val mediaVisibility: Boolean = false,
    val categories: List<CategoryUiState> = CategoryUiState.getMoviesCategories(),
    val title: Int = RDesignSystem.string.movies,
    val media: Flow<PagingData<MediaUI>> = flowOf(PagingData.empty()),
    val selectedCategory: CategoryUiState = CategoryUiState.getDefault(),
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

data class MediaUI(
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val posterPath: String,
    val releaseDate: String,
    val type: MediaType
)

fun Media.toMediaUI(): MediaUI {
    return MediaUI(
        id = this.id,
        title = this.title,
        voteAverage = this.rating,
        posterPath = this.imageUri,
        releaseDate = this.yearOfRelease.toString(),
        type = this.type
    )
}

fun List<Media>.toMediaUIList(): List<MediaUI> {
    return this.map { it.toMediaUI() }
}

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}