package com.feature.mediaDetails.mediaDetailsUi.ui.mapper

import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.Episode
import com.paris_2.domain.media.entity.EpisodeVideo
import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.entity.Movie
import com.paris_2.domain.media.entity.MovieSimilar
import com.paris_2.domain.media.entity.MovieVideo
import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.entity.Season
import com.paris_2.domain.media.entity.TvShow
import com.paris_2.domain.media.entity.TvShowSimilar
import com.paris_2.domain.media.entity.TvShowVideo
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieVideoUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeVideoUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.SeasonUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowVideoUi
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

fun Movie.toUi(): MovieUi {
    return MovieUi(
        id = this.id,
        posterUrl = this.posterPath,
        rating = this.voteAverage?.toFloat(),
        title = this.title,
        genres = this.categories.map { it.toDisplayName() },
        releaseDate = this.releaseDate.formatToUi(),
        runtime = this.runtime.toHoursMinutes(),
        country = this.country,
        description = this.description,
        productionCompanies = this.productionCompanies.map { it.toUi() }
    )
}

fun TvShow.toUi(): TvShowUi {
    return TvShowUi(
        id = this.id,
        posterUrl = this.posterPath,
        rating = this.voteAverage?.toFloat(),
        title = this.title,
        genres = this.categories.map { it.toDisplayName() },
        releaseDate = this.releaseDate.formatToUi(),
        runtime = this.runtime.toHoursMinutes(),
        country = this.country,
        seasons = this.seasons.toListOfSeasonUi(),
        description = this.description,
        productionCompanies = this.productionCompanies.map { it.toUi() }
    )
}


fun ProductionCompany.toUi(): ProductionCompanyUi {
    return ProductionCompanyUi(
        logoUrl = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}

fun Cast.toUi(): CastUi {
    return CastUi(
        name = this.name,
        imageUrl = this.imageUrl
    )
}

fun List<Cast>.toListOfCastUi(): List<CastUi> {
    return this.map { it.toUi() }
}


fun Review.toUi(): ReviewUi {
    return ReviewUi(
        avatarUrl = this.avatarUrl,
        username = this.username,
        name = this.name,
        rating = this.rating,
        createdAt = this.createdAt.formatToUi(),
        description = this.description
    )
}

fun Season.toUi(): SeasonUi {
    return SeasonUi(
        id = this.id,
        name = this.name,
        seasonNumber = this.seasonNumber,
        episodeCount = this.episodeCount,
        episodes = this.episodes.map { it.toUi() }
    )
}

fun Episode.toUi(): EpisodeUi {
    return EpisodeUi(
        episodeNumber = this.episodeNumber,
        posterUrl = this.posterUrl,
        voteAverage = this.voteAverage,
        airDate = this.airDate.formatToUi(),
        runtime = "${this.runtime} min",
        description = this.description,
        stillUrl = this.stillUrl
    )
}


fun LocalDate.formatToUi(): String {
    if (this.year == 9999) {
        return ""
    }
    val day = this.day.toString().padStart(2, '0')
    val month = month.number.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day-$month-$year"
}

fun Int.toHoursMinutes(): String {
    val hours = this / 60
    val minutes = this % 60
    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}min"
        hours > 0 -> "${hours}h"
        else -> "${minutes}m"
    }
}

fun List<Image>.toUi(): List<String> {
    return this.map { it.url }
}

fun MovieSimilar.toUi(): SimilarMediaUI {
    return SimilarMediaUI(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}

fun TvShowSimilar.toUi(): SimilarMediaUI {
    return SimilarMediaUI(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}

fun List<MovieSimilar>.toListOfMovieSimilarUI(): List<SimilarMediaUI> {
    return this.map { it.toUi() }
}

fun List<TvShowSimilar>.toListOfMTvShowSimilarUI(): List<SimilarMediaUI> {
    return this.map { it.toUi() }
}

fun List<Review>.toListOfReviewUi(): List<ReviewUi> {
    return this.map { it.toUi() }
}

fun List<ProductionCompany>.toListOfProductionCompanyUi(): List<ProductionCompanyUi> {
    return this.map { it.toUi() }
}

fun List<Season>.toListOfSeasonUi(): List<SeasonUi> {
    return this.map { it.toUi() }
}

fun List<Episode>.toListOfEpisodeUi(): List<EpisodeUi> {
    return this.map { it.toUi() }
}

fun MovieVideo.toUi(): MovieVideoUi{
    return MovieVideoUi(
        key = this.key,
        name = this.name,
        site = this.site
    )
}

fun TvShowVideo.toUi(): TvShowVideoUi{
    return TvShowVideoUi(
        key = this.key,
        name = this.name,
        site = this.site
    )
}

fun EpisodeVideo.toUi(): EpisodeVideoUi{
    return EpisodeVideoUi(
        key = this.key,
        name = this.name,
        site = this.site
    )
}

fun Movie.toMedia(): Media {
    return Media(
        id = id,
        imageUri = posterPath,
        title = title,
        type = MediaType.Movie,
        categories = categories,
        yearOfRelease = releaseDate,
        rating = voteAverage
    )
}

fun TvShow.toMedia(): Media {
    return Media(
        id = id,
        imageUri = posterPath,
        title = title,
        type = MediaType.TvShow,
        categories = categories,
        yearOfRelease = releaseDate,
        rating = voteAverage
    )
}