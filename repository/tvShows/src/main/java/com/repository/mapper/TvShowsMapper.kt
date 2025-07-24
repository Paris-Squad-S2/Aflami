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
import com.domain.mediaDetails.model.TvShowVideo
import com.repository.model.local.CastEntity
import com.repository.model.local.EpisodeEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.GenreEntity
import com.repository.model.local.ImageEntity
import com.repository.model.local.ProductionCompanyEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity
import com.repository.model.remote.TvShowCastDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowEpisodeDto
import com.repository.model.remote.TvShowGenreDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowPosterDto
import com.repository.model.remote.TvShowProductionCompanyDto
import com.repository.model.remote.TvShowReviewDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarDto
import com.repository.model.remote.TvShowVideoResultDto
import com.repository.util.toImageUrl
import kotlinx.datetime.LocalDate


fun TvShowDto.toLocalDto(language: String,tvShowId : Int): TvShowEntity {
    return TvShowEntity(
        id = tvShowId,
        title = this.name.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        description = this.overview.orEmpty(),
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        genres = this.genres?.map { it.toLocalDto() } ?: emptyList(),
        seasons = this.seasonDto?.map { it.toLocalDto(tvShowId) } ?: emptyList(),
        releaseDate = this.firstAirDate.orEmpty(),
        runtime = this.episodeRunTime?.firstOrNull() ?: 0,
        country = this.originCountry?.firstOrNull().orEmpty(),
        productionCompanies = this.productionCompanies?.map { it.toLocalDto() } ?: emptyList(),
        language = language
    )
}

fun TvShowSeasonDto.toLocalDto(tvShowId: Int): SeasonEntity {
    return SeasonEntity(
        id = this.id ?: 0,
        tvShowId = tvShowId,
        name = this.name.orEmpty(),
        episodeCount = this.episodeCount ?: 0,
        seasonNumber = this.seasonNumber ?: 0,
        episodes = this.episodesDto?.map { it.toLocalDto(this.posterPath.orEmpty()) }
            ?: emptyList()
    )
}

private fun TvShowEpisodeDto.toLocalDto(posterUrl: String): EpisodeEntity {
    val airDate = try {
        LocalDate.parse(this.airDate.orEmpty())
    }
    catch (_: Exception) {
        LocalDate(9999, 1, 1)
    }
    return EpisodeEntity(
        id = this.id ?: 0,
        episodeNumber = this.episodeNumber ?: 0,
        posterUrl = posterUrl,
        voteAverage = this.voteAverage ?: 0.0,
        airDate = airDate,
        runtime = this.runtime ?: 0,
        description = this.overview.orEmpty(),
        stillUrl = this.stillPath.toImageUrl().orEmpty()
    )
}

fun TvShowEntity.toEntity(): TvShow {
    return TvShow(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        description = this.description,
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        genres = this.genres.map { it.toEntity() },
        releaseDate = LocalDate.parse(this.releaseDate),
        runtime = this.runtime,
        country = this.country,
        productionCompanies = this.productionCompanies.map { it.toEntity() },
        seasons = this.seasons.map { it.toEntity() }
    )
}

fun CastEntity.toEntity(): Cast {
    return Cast(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUri.toImageUrl().orEmpty()
    )
}

fun  TvShowVideoResultDto.toEntity(): TvShowVideo {
    return TvShowVideo(
        key = this.key.orEmpty(),
        name = this.name.orEmpty(),
        site = this.site.orEmpty(),
        type = this.type.orEmpty(),
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
        logoPath = this.logoPath.toImageUrl().orEmpty(),
        name = this.name.orEmpty(),
        originCountry = this.originCountry.orEmpty()
    )
}

fun ProductionCompanyEntity.toEntity(): ProductionCompany {
    return ProductionCompany(
        id = this.id,
        logoPath = this.logoPath.toImageUrl().orEmpty(),
        name = this.name,
        originCountry = this.originCountry
    )
}


fun TvShowCastDto.toLocalDto(language: String,tvShowId: Int): CastEntity {
    return CastEntity(
        tvShowId = tvShowId,
        name = this.name.orEmpty(),
        id = this.id ?: 0,
        imageUri = this.profilePath.orEmpty(),
        language = language
    )
}


fun TvShowSimilarEntity.toEntity(): TvShowSimilar {
    return TvShowSimilar(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
    )
}

fun TvShowSimilarDto.toLocalDto(tvShowId: Int,language: String,page: Int): TvShowSimilarEntity {
    return TvShowSimilarEntity(
        id = this.id ?: 0,
        tvShowId = tvShowId,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.toImageUrl().orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        language = language,
        page = page

    )
}

fun TvShowImagesDto.toLocalDto(tvShowId: Int): GalleryEntity{
    return GalleryEntity(
        id = this.id ?: 0,
        images = this.posters?.map { it.toLocalDto() } ?: emptyList(),
        tvShowId = tvShowId
    )
}

private fun TvShowPosterDto.toLocalDto(): ImageEntity{
    return ImageEntity(
        id = 0,
        url = this.filePath.toImageUrl().orEmpty()
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
        url = this.url.toImageUrl().orEmpty()
    )
}


fun TvShowReviewDto.toLocalDto(tvShowId: Int,language: String): ReviewEntity{
    val createdAt = try {
        LocalDate.parse(this.createdAt.orEmpty().substring(0,10))
    }
    catch (_: Exception) {
        LocalDate(9999, 1, 1)
    }
    return ReviewEntity(
        id = 0,
        name = this.authorDetails?.name.orEmpty(),
        createdAt = createdAt,
        avatarUrl = this.authorDetails?.avatarPath.toImageUrl().orEmpty(),
        username = this.authorDetails?.username.orEmpty(),
        rating = this.authorDetails?.rating ?: 0.0,
        tvShowId = tvShowId,
        description = this.content.orEmpty(),
        language = language
    )
}

fun SeasonEntity.toEntity(): Season {
    return Season(
        id = this.id,
        name = this.name,
        seasonNumber = this.seasonNumber,
        episodeCount = this.episodeCount,
        episodes = this.episodes.map { it.toEntity() }
    )
}

private fun EpisodeEntity.toEntity(): Episode{
    return Episode(
        id = this.id,
        episodeNumber = this.episodeNumber,
        posterUrl = this.posterUrl.toImageUrl().orEmpty(),
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
        avatarUrl = this.avatarUrl.toImageUrl().orEmpty(),
        username = this.username,
        rating = this.rating,
        description = this.description
    )
}
