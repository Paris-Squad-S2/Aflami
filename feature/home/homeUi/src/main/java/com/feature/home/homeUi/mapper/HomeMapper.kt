package com.feature.home.homeUi.mapper

import com.domain.home.model.Category
import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.feature.home.homeUi.screen.home.CategoryUiState
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.SliderMedia
import com.paris_2.aflami.designsystem.components.SliderMediaTypeUi
import kotlinx.datetime.LocalDate

fun List<Media>.toSliderMediaList() = this.map { it.toSliderMedia() }

fun Media.toSliderMedia(): SliderMedia{
    return SliderMedia(
        imageUri = this.posterPath,
        rating =  this.voteAverage.toFloat(),
        title = this.title,
        id = this.id,
        type = this.type.toSliderMediaTypeUi(),
        categories = this.genreIds.map { it.genreToName() },
        yearOfRelease = this.yearOfRelease.toString()
    )
}
fun MediaType.toSliderMediaTypeUi(): SliderMediaTypeUi{
    return when(this){
        MediaType.TV_SHOW  -> SliderMediaTypeUi.TvShow
        MediaType.MOVIE -> SliderMediaTypeUi.Movie
    }
}

fun SliderMedia.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        posterPath = this.imageUri,
        voteAverage = this.rating.toDouble(),
        type = this.type.toMediaType(),
        genreIds = this.categories.map { it.nameToGenreId() },
        yearOfRelease = LocalDate.parse(this.yearOfRelease)
    )
}

fun SliderMediaTypeUi.toMediaType(): MediaType {
    return when (this) {
        SliderMediaTypeUi.Movie -> MediaType.MOVIE
        SliderMediaTypeUi.TvShow -> MediaType.TV_SHOW
    }
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

fun MediaUiState.toMedia():Media{
    return Media(
        id = this.id,
        posterPath = this.imageUri,
        title = this.title,
        type = this.type.toMediaType(),
        genreIds = this.categories.map { it.nameToGenreId() },
        yearOfRelease = this.yearOfRelease,
        voteAverage = this.rating,
    )
}


fun MediaType.toUiState(): MediaTypeUi{
    return when(this){
        MediaType.TV_SHOW -> MediaTypeUi.TVSHOW
        MediaType.MOVIE -> MediaTypeUi.MOVIE
    }
}
fun MediaTypeUi.toMediaType(): MediaType{
    return when(this){
        MediaTypeUi.TVSHOW -> MediaType.TV_SHOW
        MediaTypeUi.MOVIE -> MediaType.MOVIE
    }
}

fun Category.toCategoryUIState():CategoryUiState{
    return CategoryUiState(
        id = this.id,
        name = this.name
    )
}

fun List<Category>.toCategoryUiList() = this.map { it.toCategoryUIState() }

