package com.repository.media.mapper

import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.entity.MediaVideo
import com.paris_2.domain.media.entity.Movie
import com.paris_2.domain.media.entity.MovieSimilar
import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.entity.Review
import com.repository.media.models.local.moive.MovieCastEntity
import com.repository.media.models.local.moive.MovieEntity
import com.repository.media.models.local.moive.MovieGalleryEntity
import com.repository.media.models.local.moive.MovieGenreEntity
import com.repository.media.models.local.moive.MovieImageEntity
import com.repository.media.models.local.moive.MovieProductionCompanyEntity
import com.repository.media.models.local.moive.MovieReviewEntity
import com.repository.media.models.local.moive.MovieSimilarEntity
import com.repository.media.models.remote.movie.MovieCastDto
import com.repository.media.models.remote.movie.MovieDto
import com.repository.media.models.remote.movie.MovieGenreDto
import com.repository.media.models.remote.movie.MovieImagesDto
import com.repository.media.models.remote.movie.MovieLogoDto
import com.repository.media.models.remote.movie.MovieProductionCompanyDto
import com.repository.media.models.remote.movie.MovieReviewDto
import com.repository.media.models.remote.movie.MovieSimilarDto
import com.repository.media.models.remote.movie.MovieVideoResultDto
import kotlinx.datetime.LocalDate

fun MovieGenreDto.toEntity(): Category {
    return id?.toGenre() ?: Category.Unknown
}

private fun MovieGenreDto.toLocalDto(): MovieGenreEntity {
    return MovieGenreEntity(
        id = this.id ?: 0,
        name = this.name.orEmpty()
    )
}

fun MovieProductionCompanyDto.toEntity(): ProductionCompany {
    return ProductionCompany(
        id = this.id ?: 0,
        logoPath = this.logoPath.toImageUrl().orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun MovieCastDto.toEntity(): Cast {
    return Cast(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        imageUrl = this.profilePath.toImageUrl().orEmpty()
    )
}

fun Cast.toLocalDto(movieIds: Int, language: String): MovieCastEntity {
    return MovieCastEntity(
        movieId = movieIds,
        name = this.name,
        id = this.id,
        imageUri = this.imageUrl.toImageUrl().orEmpty(),
        language = language
    )
}

fun MovieCastEntity.toEntity(): Cast {
    return Cast(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUri
    )
}

fun MovieImagesDto.toEntity(): List<Image> {
    return  this.logos?.map { it.toEntity(id = this.id ?: 0) } ?: emptyList()
}

private fun MovieLogoDto.toEntity(id: Int): Image {
    return Image(
        id = id,
        url = this.filePath.toImageUrl().orEmpty()
    )
}

fun MovieReviewDto.toEntity(): Review {
    return Review(
        createdAt = try {
            LocalDate.parse(this.createdAt.orEmpty().substring(0,10))
        } catch (_: Exception) {
            LocalDate(9999, 1, 1)
        },
        id = this.id.orEmpty(),
        name = this.authorDetails?.name.orEmpty(),
        avatarUrl = this.authorDetails?.avatarPath.toImageUrl().orEmpty(),
        username = this.authorDetails?.username.orEmpty(),
        rating = this.authorDetails?.rating,
        description = this.content.orEmpty()
    )
}

fun MovieDto.toLocalDto(language: String): MovieEntity {
    return MovieEntity(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        voteAverage = this.voteAverage,
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
        categories = this.genres.map { it.toEntity() },
        releaseDate = LocalDate.parse(this.releaseDate),
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
        voteAverage = this.voteAverage,
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        language = language,
        page = page
    )
}

fun Image.toLocalDto(): MovieImageEntity {
    return MovieImageEntity(
        id = this.id,
        url = this.url
    )
}

fun MovieImageEntity.toEntity(): Image {
    return Image(
        id = this.id,
        url = this.url
    )
}

fun MovieGalleryEntity.toEntity(): List<Image> {
    return this.images.map { it.toEntity() }
}

fun Review.toLocalDto(movieId: Int, language: String): MovieReviewEntity {
    return MovieReviewEntity(
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

fun MovieReviewEntity.toEntity(): Review {
    return Review(
        id = this.id.toString(),
        name = this.name,
        createdAt = this.createdAt,
        avatarUrl = this.avatarUrl,
        username = this.username,
        rating = this.rating,
        description = this.description
    )
}

fun MovieVideoResultDto.toEntity(): MediaVideo {
    return MediaVideo(
        key = this.key.orEmpty(),
        name = this.name.orEmpty(),
        site = this.site.orEmpty(),
        type = this.type.orEmpty(),
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

fun MovieGenreEntity.toEntity(): Category {
    return id.toGenre()
}

fun MovieProductionCompanyDto.toLocalDto(): MovieProductionCompanyEntity {
    return MovieProductionCompanyEntity(
        id = this.id ?: 0,
        logoPath = this.logoPath.toImageUrl().orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun MovieProductionCompanyEntity.toEntity(): ProductionCompany {
    return ProductionCompany(
        id = this.id,
        logoPath = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}

private val idToCategoryMap = mapOf(
    28 to Category.Action,
    12 to Category.Adventure,
    16 to Category.Animation,
    35 to Category.Comedy,
    80 to Category.Crime,
    99 to Category.Documentary,
    18 to Category.Drama,
    10751 to Category.Family,
    14 to Category.Fantasy,
    36 to Category.History,
    27 to Category.Horror,
    10402 to Category.Music,
    9648 to Category.Mystery,
    10749 to Category.Romance,
    878 to Category.ScienceFiction,
    10770 to Category.TvMovie,
    53 to Category.Thriller,
    10752 to Category.War,
    37 to Category.Western,
    10759 to Category.ActionAdventure,
    10762 to Category.Kids,
    10763 to Category.News,
    10764 to Category.Reality,
    10765 to Category.ScifiFantasy,
    10766 to Category.Soap,
    10767 to Category.Talk,
    10768 to Category.WarPolitics
)