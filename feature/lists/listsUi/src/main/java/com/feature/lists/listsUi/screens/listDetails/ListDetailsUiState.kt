package com.feature.lists.listsUi.screens.listDetails

import androidx.paging.PagingData
import com.paris.domain.lists.entity.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate

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
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val showDeleteDialog: Boolean = false
)

fun Media.toUiState(): MediaUiState {
    return MediaUiState(
        id = this.id,
        title = this.title,
        imageUrl = this.posterPath,
        rating = this.voteAverage.toFloat(),
        yearOfRelease = this.releaseDate
    )
}