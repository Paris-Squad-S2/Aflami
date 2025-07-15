package com.example.tvshow.mapper

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Country
import com.domain.mediaDetails.model.Episode
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Genre
import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.example.tvshow.model.remote.TvShowCastDto
import com.example.tvshow.model.remote.TvShowDto
import com.example.tvshow.model.remote.TvShowEpisodeDto
import com.example.tvshow.model.remote.TvShowGenreDto
import com.example.tvshow.model.remote.TvShowImagesDto
import com.example.tvshow.model.remote.TvShowLogoDto
import com.example.tvshow.model.remote.TvShowProductionCompanyDto
import com.example.tvshow.model.remote.TvShowReviewDto
import com.example.tvshow.model.remote.TvShowSeasonDto
import com.example.tvshow.model.remote.TvShowSimilarDto
import kotlinx.datetime.LocalDate

fun TvShowDto.toEntity(): TvShow {
    return TvShow(
        id = this.id ?: 0,
        title = this.name.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        genres = this.genres?.map { it.toEntity() } ?: emptyList(),
        releaseDate = this.firstAirDate.orEmpty(),
        runtime = this.episodeRunTime?.firstOrNull() ?: 0,
        country = Country(
            // TODO: get country name from local
            countryCode = this.originCountry?.firstOrNull().orEmpty(),
            countryNameEN = "en",
            countryNameAR = "ar"
        ),
        productionCompanies = this.productionCompanies?.map { it.toEntity() } ?: emptyList(),
    )
}

fun TvShowGenreDto.toEntity(): Genre {
    return Genre(
        id = this.id ?: 0,
        name = this.name.orEmpty()
    )
}

fun TvShowProductionCompanyDto.toEntity(): ProductionCompany {
    return ProductionCompany(
        id = this.id ?: 0,
        logoPath = this.logoPath.orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun TvShowCastDto.toEntity(): Cast {
    return Cast(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        imageUrl = this.profilePath.orEmpty()
    )
}

fun TvShowSimilarDto.toEntity(): TvShow {
    return TvShow(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        genres = this.genreIds?.map { it.toEntity() } ?: emptyList(),
        runtime = 0,
        country = Country(
            // TODO: get country name from local
            countryCode = "",
            countryNameEN = "en",
            countryNameAR = "ar"
        ),
        productionCompanies = emptyList(),
    )
}

fun TvShowImagesDto.toEntity(): Gallery {
    return Gallery(
        images = this.logos?.map { it.toEntity(id = this.id ?: 0) } ?: emptyList()
    )
}

fun TvShowLogoDto.toEntity(id: Int): Image {
    return Image(
        id = id,
        url = this.filePath.orEmpty()
    )
}

fun TvShowReviewDto.toEntity(): Review {
    return Review(
        id = this.id.orEmpty(),
        name = this.authorDetails?.name.orEmpty(),
        createdAt = LocalDate.parse(this.createdAt ?: "2025-01-01"),
        avatarUrl = this.authorDetails?.avatarPath.orEmpty(),
        username = this.authorDetails?.username.orEmpty(),
        rating = this.authorDetails?.rating ?: 0.0
    )
}

fun TvShowSeasonDto.toEntity(): Season {
    return Season(
        id = this.id.toString(),
        name = this.name.orEmpty(),
        episodes = this.episodesDto?.map { it.toEntity(this.posterPath.orEmpty()) } ?: emptyList()
    )
}

fun TvShowEpisodeDto.toEntity(posterPath: String): Episode {
    return Episode(
        id = this.id ?: 0,
        episodeNumber = this.episodeNumber ?: 0,
        posterUrl = posterPath,
        voteAverage = this.voteAverage ?: 0.0,
        airDate = LocalDate.parse(this.airDate.orEmpty()),
        runtime = this.runtime ?: 0,
        description = this.overview.orEmpty(),
        stillUrl = this.stillPath.orEmpty()
    )
}

private fun Int.toEntity(): Genre {
    return Genre(
        id = this,
        name = ""
    )
}