package com.feature.profile.profileUi.mapper

import com.feature.profile.profileUi.screen.watchHistory.MediaTypeUi
import com.feature.profile.profileUi.screen.watchHistory.MediaUiState
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType


fun List<Media>.toMediaUiStateList() = this.map { it.toUiState() }

fun Media.toUiState(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toUiState(),
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}

fun MediaType.toUiState(): MediaTypeUi {
    return when(this){
        MediaType.TVSHOW -> MediaTypeUi.TVSHOW
        MediaType.MOVIE -> MediaTypeUi.MOVIE
    }
}

fun MediaTypeUi.toMediaType(): MediaType {
    return when (this) {
        MediaTypeUi.MOVIE -> MediaType.MOVIE
        MediaTypeUi.TVSHOW -> MediaType.TVSHOW
    }
}