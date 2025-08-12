package com.feature.lists.listsUi.screens.listDetails

import androidx.paging.PagingData
import com.paris.domain.lists.entity.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate

data class ListDetailsUiState(
    val id: Int,
    val name: String,
    val mediaItems: List<MediaUiState>
)

data class MediaUiState(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val yearOfRelease: LocalDate
)

data class ListDetailsScreenState(
    val listTitle: String = "",
    val mediaItems: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    val listDetails: ListDetailsUiState? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDeleteDialog: Boolean = false,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}

fun Media.toUiState(): MediaUiState = MediaUiState(
    id = this.id,
    title = this.title,
    imageUrl = this.imageUrl,
    rating = this.voteAverage.toFloat(),
    yearOfRelease = this.releaseDate
)