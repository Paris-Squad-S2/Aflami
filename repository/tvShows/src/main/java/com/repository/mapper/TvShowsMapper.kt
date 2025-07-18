package com.repository.mapper

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Episode
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Genre
import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowSimilar
import com.repository.model.local.CastEntity
import com.repository.model.local.EpisodeEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.GenreEntity
import com.repository.model.local.ImageEntity
import com.repository.model.local.ProductionCompanyEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.remote.TvShowCastDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowEpisodeDto
import com.repository.model.remote.TvShowGenreDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowLogoDto
import com.repository.model.remote.TvShowProductionCompanyDto
import com.repository.model.remote.TvShowReviewDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarDto
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
        country = this.originCountry?.firstOrNull().orEmpty(),
        productionCompanies = this.productionCompanies?.map { it.toEntity() } ?: emptyList(),
        seasons = this.seasonDto?.map { it.toEntity() } ?: emptyList()
    )
}

fun TvShowDto.toLocalDto(): TvShowEntity {
    return TvShowEntity(
        id = this.id ?: 0,
        title = this.name.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        genres = this.genres?.map { it.toLocalDto() } ?: emptyList(),
        seasons = this.seasonDto?.map { it.toLocalDto() } ?: emptyList(),
        releaseDate = this.firstAirDate.orEmpty(),
        runtime = this.episodeRunTime?.firstOrNull() ?: 0,
        country = this.originCountry?.firstOrNull().orEmpty(),
        productionCompanies = this.productionCompanies?.map { it.toLocalDto() } ?: emptyList()
    )
}

fun TvShowSeasonDto.toLocalDto(): SeasonEntity {
    return SeasonEntity(
        id = 0,
        tvShowId = this.id ?: 0,
        name = this.name.orEmpty(),
        episodes = this.episodesDto?.map { it.toLocalDto(this.posterPath.orEmpty()) }
            ?: emptyList()
    )
}

private fun TvShowEpisodeDto.toLocalDto(posterUrl: String): EpisodeEntity {
    return EpisodeEntity(
        id = this.id ?: 0,
        episodeNumber = this.episodeNumber ?: 0,
        posterUrl = posterUrl,
        voteAverage = this.voteAverage ?: 0.0,
        airDate = LocalDate.parse(this.airDate.orEmpty()),
        runtime = this.runtime ?: 0,
        description = this.overview.orEmpty(),
        stillUrl = this.stillPath.orEmpty()
    )
}

fun TvShowEntity.toEntity(): TvShow {
    return TvShow(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        description = this.description,
        posterPath = this.posterPath,
        genres = this.genres.map { it.toEntity() },
        releaseDate = this.releaseDate,
        runtime = this.runtime,
        country = this.country,
        productionCompanies = this.productionCompanies.map { it.toEntity() },
        seasons = this.seasons.map { it.toEntity() }
    )
}

fun Cast.toLocalDto(): CastEntity {
    return CastEntity(
        tvShowId = this.id,
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

private fun TvShowGenreDto.toLocalDto(): GenreEntity {
    return GenreEntity(
        id = this.id ?: 0,
        name = this.name.orEmpty()
    )
}

private fun GenreEntity.toEntity(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

private fun TvShowProductionCompanyDto.toLocalDto(): ProductionCompanyEntity {
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

fun TvShowSimilarDto.toEntity(): TvShowSimilar {
    return TvShowSimilar(
        id = this.id ?: 0,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
    )
}

fun TvShowImagesDto.toEntity(): Gallery {
    return Gallery(
        images = this.logos?.map { it.toEntity(id = this.id ?: 0) } ?: emptyList()
    )
}

fun Image.toLocalDto(): ImageEntity {
    return ImageEntity(
        id = this.id,
        url = this.url
    )
}

fun GalleryEntity.toEntity(): Gallery {
    return Gallery(
        images = this.images.map { it.toEntity() }
    )
}

fun ImageEntity.toEntity(): Image {
    return Image(
        id = this.id,
        url = this.url
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

fun Review.toLocalDto(): ReviewEntity {
    return ReviewEntity(
        id = 0,
        name = this.name,
        createdAt = this.createdAt,
        avatarUrl = this.avatarUrl,
        username = this.username,
        rating = this.rating,
        tvShowId = this.id
    )
}

fun TvShowSeasonDto.toEntity(): Season {
    return Season(
        id = this.id.toString(),
        name = this.name.orEmpty(),
        episodes = this.episodesDto?.map { it.toEntity(this.posterPath.orEmpty()) } ?: emptyList()
    )
}
fun SeasonEntity.toEntity(): Season {
    return Season(
        id = this.id.toString(),
        name = this.name,
        episodes = this.episodes.map { it.toEntity() }
    )
}

private fun EpisodeEntity.toEntity(): Episode{
    return Episode(
        id = this.id,
        episodeNumber = this.episodeNumber,
        posterUrl = this.posterUrl,
        voteAverage = this.voteAverage,
        airDate = this.airDate,
        runtime = this.runtime,
        description = this.description,
        stillUrl = this.stillUrl
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