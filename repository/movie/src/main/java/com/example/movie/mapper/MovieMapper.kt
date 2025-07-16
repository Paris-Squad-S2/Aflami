package com.example.movie.mapper

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Country
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Genre
import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.example.movie.models.local.CastEntity
import com.example.movie.models.local.CountryEntity
import com.example.movie.models.local.GalleryEntity
import com.example.movie.models.local.GenreEntity
import com.example.movie.models.local.ImageEntity
import com.example.movie.models.local.MovieEntity
import com.example.movie.models.local.ProductionCompanyEntity
import com.example.movie.models.local.ReviewEntity
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
            countryNameAR = "en"
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

private fun MovieGenreDto.toLocalDto(): GenreEntity {
    return GenreEntity(
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

fun Cast.toLocalDto(): CastEntity {
    return CastEntity(
        movieId = this.id,
        name = this.name,
        id = 0,
        imageUri = this.imageUrl
    )
}

fun CastEntity.toEntity(): Cast {
    return Cast(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUri
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

fun Movie.toLocalDto(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        description = this.description,
        posterPath = this.posterPath,
        genres = this.genres.map { it.toLocalDto() },
        releaseDate = this.releaseDate,
        runtime = this.runtime,
        country = this.country.toLocalDto(),
        productionCompanies = this.productionCompanies.map { it.toLocalDto() }
    )
}

fun MovieDto.toLocalDto(): MovieEntity {
    return MovieEntity(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        genres = this.movieGenreDto?.map { it.toLocalDto() } ?: emptyList(),
        releaseDate = this.releaseDate.orEmpty(),
        runtime = this.runtime ?: 0,
        country = CountryEntity(
            // TODO: get country name from local
            countryCode = this.originCountry?.firstOrNull().orEmpty(),
            name = "en",
        ),
        productionCompanies = this.productionCompanies?.map { it.toLocalDto() } ?: emptyList(),
    )
}

fun MovieEntity.toEntity(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        description = this.description,
        posterPath = this.posterPath,
        genres = this.genres.map { it.toEntity() },
        releaseDate = this.releaseDate,
        runtime = this.runtime,
        country = this.country.toEntity(),
        productionCompanies = this.productionCompanies.map { it.toEntity() }
    )
}

fun Image.toLocalDto(): ImageEntity {
    return ImageEntity(
        id = this.id,
        url = this.url
    )
}

fun ImageEntity.toEntity(): Image {
    return Image(
        id = this.id,
        url = this.url
    )
}

fun GalleryEntity.toEntity(): Gallery {
    return Gallery(
        images = this.images.map { it.toEntity() }
    )
}

fun Review.toLocalDto(): ReviewEntity {
    return ReviewEntity(
        id = 0,
        name = this.name,
        createdAt = this.createdAt,
        avatarUrl = this.avatarUrl,
        username = this.username,
        rating = this.rating,
        movieId = this.id.toInt()
    )
}

fun ReviewEntity.toEntity(): Review {
    return Review(
        id = this.id.toString(),
        name = this.name,
        createdAt = this.createdAt,
        avatarUrl = this.avatarUrl,
        username = this.username,
        rating = this.rating,
    )
}

fun Review.toRemoteDto(): MovieReviewDto {
    return MovieReviewDto(
         author = this.name,
         createdAt = this.createdAt.toString(),
         id = this.id,
         updatedAt = this.createdAt.toString(),
         url = this.avatarUrl
    )
}


private fun Genre.toLocalDto(): GenreEntity {
    return GenreEntity(
        id = this.id,
        name = this.name
    )
}

private fun GenreEntity.toEntity(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

private fun Country.toLocalDto(): CountryEntity {
    return CountryEntity(
        countryCode = this.countryCode,
        name = this.countryNameEN
    )
}

private fun CountryEntity.toEntity(): Country {
    return Country(
        countryCode = this.countryCode,
        countryNameEN = this.name,
        countryNameAR = this.name,
    )
}

private fun ProductionCompany.toLocalDto(): ProductionCompanyEntity {
    return ProductionCompanyEntity(
        id = this.id,
        logoPath = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}

private fun MovieProductionCompanyDto.toLocalDto(): ProductionCompanyEntity {
    return ProductionCompanyEntity(
        id = this.id ?: 0,
        logoPath = this.logoPath.orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

private fun ProductionCompanyEntity.toEntity(): ProductionCompany {
    return ProductionCompany(
        id = this.id,
        logoPath = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}
