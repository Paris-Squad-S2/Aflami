package com.repository.movie.mapper

import com.paris_2.domain.movie.model.MovieCast
import com.paris_2.domain.movie.model.MovieGallery
import com.paris_2.domain.movie.model.MovieGenre
import com.paris_2.domain.movie.model.MovieImage
import com.paris_2.domain.movie.model.Movie
import com.paris_2.domain.movie.model.MovieSimilar
import com.paris_2.domain.movie.model.MovieProductionCompany
import com.paris_2.domain.movie.model.MovieReview
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.GenreEntity
import com.repository.movie.models.local.ImageEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.ProductionCompanyEntity
import com.repository.movie.models.local.ReviewEntity
import com.repository.movie.models.remote.MovieCastDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieGenreDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieLogoDto
import com.repository.movie.models.remote.MovieProductionCompanyDto
import com.repository.movie.models.remote.MovieReviewDto
import com.repository.movie.models.remote.MovieSimilarDto
import com.repository.movie.util.toImageUrl
import kotlinx.datetime.LocalDate

fun MovieGenreDto.toEntity(): MovieGenre {
    return MovieGenre(
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

fun MovieProductionCompanyDto.toEntity(): MovieProductionCompany {
    return MovieProductionCompany(
        id = this.id ?: 0,
        logoPath = this.logoPath.toImageUrl().orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun MovieCastDto.toEntity(): MovieCast {
    return MovieCast(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        imageUrl = this.profilePath.toImageUrl().orEmpty()
    )
}

fun MovieCast.toLocalDto(movieIds: Int, language: String): CastEntity {
    return CastEntity(
        movieId = movieIds,
        name = this.name,
        id = this.id,
        imageUri = this.imageUrl.toImageUrl().orEmpty(),
        language = language
    )
}

fun CastEntity.toEntity(): MovieCast {
    return MovieCast(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUri
    )
}

fun MovieImagesDto.toEntity(): MovieGallery {
    return MovieGallery(
        movieImages = this.logos?.map { it.toEntity(id = this.id ?: 0) } ?: emptyList()
    )
}

private fun MovieLogoDto.toEntity(id: Int): MovieImage {
    return MovieImage(
        id = id,
        url = this.filePath.toImageUrl().orEmpty()
    )
}

fun MovieReviewDto.toEntity(): MovieReview {
    return MovieReview(
        createdAt = try {
            LocalDate.parse(this.createdAt.orEmpty().substring(0,10))
        } catch (_: Exception) {
            LocalDate(9999, 1, 1)
        },
        id = this.id.orEmpty(),
        name = this.authorDetails?.name.orEmpty(),
        avatarUrl = this.authorDetails?.avatarPath.toImageUrl().orEmpty(),
        username = this.authorDetails?.username.orEmpty(),
        rating = this.authorDetails?.rating ?: 0.0,
        description = this.content.orEmpty()
    )
}

fun MovieDto.toLocalDto(language: String): MovieEntity {
    return MovieEntity(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        genres = this.movieGenreDto?.map { it.toLocalDto() } ?: emptyList(),
        releaseDate = this.releaseDate.orEmpty(),
        runtime = this.runtime ?: 0,
        country = this.originCountry?.firstOrNull().orEmpty(),
        productionCompanies = this.productionCompanies?.map { it.toLocalDto() } ?: emptyList(),
        language = language
    )
}

fun MovieEntity.toEntity(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        description = this.description,
        posterPath = this.posterPath,
        movieGenres = this.genres.map { it.toEntity() },
        releaseDate = this.releaseDate,
        runtime = this.runtime,
        country = this.country,
        productionCompanies = this.productionCompanies.map { it.toEntity() }
    )
}

fun MovieSimilarDto.toLocalDto(movieId: Int, page: Int, language: String): MovieSimilarEntity {
    return MovieSimilarEntity(
        id = this.id ?: 0,
        movieId = movieId,
        title = this.title.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        language = language,
        page = page
    )
}

fun MovieImage.toLocalDto(): ImageEntity {
    return ImageEntity(
        id = this.id,
        url = this.url
    )
}

fun ImageEntity.toEntity(): MovieImage {
    return MovieImage(
        id = this.id,
        url = this.url
    )
}

fun GalleryEntity.toEntity(): MovieGallery {
    return MovieGallery(
        movieImages = this.images.map { it.toEntity() }
    )
}

fun MovieReview.toLocalDto(movieId: Int, language: String): ReviewEntity {
    return ReviewEntity(
        id = 0,
        name = this.name,
        createdAt = this.createdAt,
        avatarUrl = this.avatarUrl,
        username = this.username,
        rating = this.rating,
        movieId = movieId,
        language = language,
        description = this.description
    )
}

fun ReviewEntity.toEntity(): MovieReview {
    return MovieReview(
        id = this.id.toString(),
        name = this.name,
        createdAt = this.createdAt,
        avatarUrl = this.avatarUrl,
        username = this.username,
        rating = this.rating,
        description = this.description
    )
}

fun MovieSimilarEntity.toEntity(): MovieSimilar {
    return MovieSimilar(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}

fun MovieSimilar.toLocalDto(movieId: Int, pager: Int, language: String): MovieSimilarEntity {
    return MovieSimilarEntity(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        movieId = movieId,
        language = language,
        page = pager
    )
}

fun GenreEntity.toEntity(): MovieGenre {
    return MovieGenre(
        id = this.id,
        name = this.name
    )
}

fun MovieProductionCompanyDto.toLocalDto(): ProductionCompanyEntity {
    return ProductionCompanyEntity(
        id = this.id ?: 0,
        logoPath = this.logoPath.toImageUrl().orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun ProductionCompanyEntity.toEntity(): MovieProductionCompany {
    return MovieProductionCompany(
        id = this.id,
        logoPath = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}
