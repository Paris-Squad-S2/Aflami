package com.feature.home.homeUi.mapper

import com.domain.media.entity.Category
import com.domain.media.entity.Media
import com.domain.media.entity.MediaType
import com.feature.home.homeUi.screen.home.CategoryUiState
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.SliderMedia
import com.paris_2.aflami.designsystem.components.SliderMediaTypeUi
import kotlinx.datetime.LocalDate

fun List<Media>.toSliderMediaList() = this.map { it.toSliderMedia() }

fun Media.toSliderMedia(): SliderMedia{
    return SliderMedia(
        imageUri = this.imageUri,
        rating =  this.rating?.toFloat(),
        title = this.title,
        id = this.id,
        type = this.type.toSliderMediaTypeUi(),
        categories = this.categoryIds.map { it.genreToName() },
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
        categoryIds = this.categories.map { it.nameToGenreId() },
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
        categories = this.categoryIds.map { it.genreToName() },
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
        categoryIds = this.categories.map { it.nameToGenreId() },
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

fun Category.toCategoryUIState():CategoryUiState{
    return CategoryUiState(
        id = this.id,
        name = this.name
    )
}

fun List<Category>.toCategoryUiList() = this.map { it.toCategoryUIState() }

