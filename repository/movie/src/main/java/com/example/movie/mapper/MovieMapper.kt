package com.example.movie.mapper

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Country
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Genre
import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.example.movie.models.remote.MovieCastDto
import com.example.movie.models.remote.MovieDto
import com.example.movie.models.remote.MovieGenreDto
import com.example.movie.models.remote.MovieImagesDto
import com.example.movie.models.remote.MovieLogoDto
import com.example.movie.models.remote.MovieProductionCompanyDto
import com.example.movie.models.remote.MovieReviewDto
import com.example.movie.models.remote.MovieSimilarDto
import kotlinx.datetime.LocalDate

fun MovieDto.toEntity(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        genres = this.movieGenreDto?.map { it.toEntity() } ?: emptyList(),
        releaseDate = this.releaseDate.orEmpty(),
        runtime = this.runtime ?: 0,
        country = Country(
            // TODO: get country name from local
            countryCode = this.originCountry?.firstOrNull().orEmpty(),
            countryNameEN = "en",
            countryNameAR = "ar"
        ),
        productionCompanies = this.productionCompanies?.map { it.toEntity() } ?: emptyList(),
    )
}

fun MovieGenreDto.toEntity(): Genre {
    return Genre(
        id = this.id ?: 0,
        name = this.name.orEmpty()
    )
}

fun MovieProductionCompanyDto.toEntity(): ProductionCompany {
    return ProductionCompany(
        id = this.id ?: 0,
        logoPath = this.logoPath.orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun MovieCastDto.toEntity(): Cast {
    return Cast(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        imageUrl = this.profilePath.orEmpty()
    )
}

fun MovieSimilarDto.toEntity(): Movie {
    return Movie(
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

fun MovieImagesDto.toEntity(): Gallery {
    return Gallery(
        images = this.logos?.map { it.toEntity(id = this.id ?: 0) } ?: emptyList()
    )
}

fun MovieLogoDto.toEntity(id: Int): Image {
    return Image(
        id = id,
        url = this.filePath.orEmpty()
    )
}

fun MovieReviewDto.toEntity(): Review {
    return Review(
        id = this.id.orEmpty(),
        name = this.authorDetails?.name.orEmpty(),
        createdAt = LocalDate.parse(this.createdAt ?: "2025-01-01"),
        avatarUrl = this.authorDetails?.avatarPath.orEmpty(),
        username = this.authorDetails?.username.orEmpty(),
        rating = this.authorDetails?.rating ?: 0.0
    )
}

private fun Int.toEntity(): Genre {
    return Genre(
        id = this,
        name = ""
    )
}