package com.feature.home.homeUi.mapper

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.SliderMedia

fun List<MediaUiState>.toSliderMediaList() = this.map { it.toSliderMedia() }

fun MediaUiState.toSliderMedia(): SliderMedia{
    return SliderMedia(
        imageUri = this.imageUri,
        rating =  this.rating.toFloat()
    )
}

fun List<Media>.toMediaUiStateList() = this.map { it.toUiState() }

fun Media.toUiState(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.posterPath,
        title = this.title,
        type = this.type.toUiState(),
        categories = this.genreIds.map { it.genreToName() },
        yearOfRelease = this.yearOfRelease,
        rating = this.voteAverage,
    )
}

fun MediaType.toUiState(): MediaTypeUi{
    return when(this){
        MediaType.TV_SHOW -> MediaTypeUi.TVSHOW
        MediaType.MOVIE -> MediaTypeUi.MOVIE
    }
}
