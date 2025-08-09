package com.feature.home.homeUi.mapper

import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.feature.home.homeUi.screen.home.components.SliderMediaTypeUi
import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import kotlinx.datetime.LocalDate

fun List<Media>.toSliderMediaList() = this.map { it.toSliderMedia() }

fun Media.toSliderMedia(): SliderMedia{
    return SliderMedia(
        imageUri = this.imageUri,
        rating =  this.rating?.toFloat(),
        title = this.title,
        id = this.id,
        type = this.type.toSliderMediaTypeUi(),
        categories = this.genres.map { it.displayName },
        yearOfRelease = this.yearOfRelease.toString()
    )
}
fun MediaType.toSliderMediaTypeUi(): SliderMediaTypeUi{
    return when(this){
        MediaType.TVSHOW  -> SliderMediaTypeUi.TvShow
        MediaType.MOVIE -> SliderMediaTypeUi.Movie
    }
}

fun SliderMedia.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        imageUri = this.imageUri,
        rating = this.rating?.toDouble(),
        type = this.type.toMediaType(),
        genres = this.categories.mapNotNull { name ->
            Genre.entries.find { it.displayName == name }
        },
        yearOfRelease = LocalDate.parse(this.yearOfRelease)
    )
}

fun SliderMediaTypeUi.toMediaType(): MediaType {
    return when (this) {
        SliderMediaTypeUi.Movie -> MediaType.MOVIE
        SliderMediaTypeUi.TvShow -> MediaType.TVSHOW
    }
}

fun List<Media>.toMediaUiStateList() = this.map { it.toUiState() }

fun Media.toUiState(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toUiState(),
        categories = this.genres.map { it.displayName },
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
        genres = this.categories.mapNotNull { name ->
            Genre.entries.find { it.displayName == name }
        },
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}


fun MediaType.toUiState(): MediaTypeUi{
    return when(this){
        MediaType.TVSHOW -> MediaTypeUi.TVSHOW
        MediaType.MOVIE -> MediaTypeUi.MOVIE
    }
}
fun MediaTypeUi.toMediaType(): MediaType{
    return when(this){
        MediaTypeUi.TVSHOW -> MediaType.TVSHOW
        MediaTypeUi.MOVIE -> MediaType.MOVIE
    }
}
