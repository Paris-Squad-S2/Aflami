package com.feature.home.homeUi.mapper

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.feature.home.homeUi.screen.home.components.SliderMediaTypeUi
import kotlinx.datetime.LocalDate

fun List<Media>.toSliderMediaList() = this.map { it.toSliderMedia() }

fun Media.toSliderMedia(): SliderMedia{
    return SliderMedia(
        imageUri = this.imageUri,
        rating =  this.rating?.toFloat(),
        title = this.title,
        id = this.id,
        type = this.type.toSliderMediaTypeUi(),
        categories = this.categories.map { it.toDisplayName() },
        yearOfRelease = this.yearOfRelease.toString()
    )
}
fun MediaType.toSliderMediaTypeUi(): SliderMediaTypeUi{
    return when(this){
        MediaType.TvShow  -> SliderMediaTypeUi.TvShow
        MediaType.Movie -> SliderMediaTypeUi.Movie
    }
}

fun SliderMedia.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        imageUri = this.imageUri,
        rating = this.rating?.toDouble(),
        type = this.type.toMediaType(),
        categories = this.categories.map { it.toCategory() },
        yearOfRelease = LocalDate.parse(this.yearOfRelease)
    )
}

fun SliderMediaTypeUi.toMediaType(): MediaType {
    return when (this) {
        SliderMediaTypeUi.Movie -> MediaType.Movie
        SliderMediaTypeUi.TvShow -> MediaType.TvShow
    }
}

fun List<Media>.toMediaUiStateList() = this.map { it.toUiState() }

fun Media.toUiState(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toUiState(),
        categories = this.categories.map { it.toDisplayName() },
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}

fun MediaUiState.toMedia():Media{
    return Media(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toMediaType(),
        categories = this.categories.map { it.toCategory() },
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}


fun MediaType.toUiState(): MediaTypeUi{
    return when(this){
        MediaType.TvShow -> MediaTypeUi.TV_SHOW
        MediaType.Movie -> MediaTypeUi.MOVIE
    }
}
fun MediaTypeUi.toMediaType(): MediaType{
    return when(this){
        MediaTypeUi.TV_SHOW -> MediaType.TvShow
        MediaTypeUi.MOVIE -> MediaType.Movie
    }
}

