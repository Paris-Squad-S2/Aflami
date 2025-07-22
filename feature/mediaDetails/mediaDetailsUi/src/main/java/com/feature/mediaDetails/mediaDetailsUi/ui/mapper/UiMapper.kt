package com.feature.mediaDetails.mediaDetailsUi.ui.mapper

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.SeasonUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowUi
import com.paris_2.domain.movie.model.Movie
import com.paris_2.domain.movie.model.MovieCast
import com.paris_2.domain.movie.model.MovieGallery
import com.paris_2.domain.movie.model.MovieProductionCompany
import com.paris_2.domain.movie.model.MovieReview
import com.paris_2.domain.movie.model.MovieSimilar
import com.paris_2.domain.tvshow.model.Episode
import com.paris_2.domain.tvshow.model.Season
import com.paris_2.domain.tvshow.model.TvShow
import com.paris_2.domain.tvshow.model.TvShowCast
import com.paris_2.domain.tvshow.model.TvShowGallery
import com.paris_2.domain.tvshow.model.TvShowProductionCompany
import com.paris_2.domain.tvshow.model.TvShowReview
import com.paris_2.domain.tvshow.model.TvShowSimilar
import kotlinx.datetime.LocalDate

fun Movie.toUi(): MovieUi {
    return MovieUi(
        id = this.id,
        posterUrl = this.posterPath,
        rating = this.voteAverage.toFloat(),
        title = this.title,
        genres = this.movieGenres.map { it.name },
        releaseDate = this.releaseDate,
        runtime = "${this.runtime} min",
        country = this.country,
        description = this.description,
        productionCompanies = this.productionCompanies.map { it.toUi() }
    )
}

fun TvShow.toUi(): TvShowUi {
    return TvShowUi(
        id = this.id,
        posterUrl = this.posterPath,
        rating = this.voteAverage.toFloat(),
        title = this.title,
        genres = this.tvShowGenres.map { it.name },
        releaseDate = this.releaseDate,
        runtime = "${this.runtime} min",
        country = this.country,
        seasons = this.seasons.toListOfSeasonUi(),
        description = this.description,
        productionCompanies = this.productionCompanies.map { it.toUi() }
    )
}


fun MovieProductionCompany.toUi(): ProductionCompanyUi {
    return ProductionCompanyUi(
        logoUrl = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}

fun TvShowProductionCompany.toUi(): ProductionCompanyUi {
    return ProductionCompanyUi(
        logoUrl = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}

fun MovieCast.toUi(): CastUi {
    return CastUi(
        name = this.name,
        imageUrl = this.imageUrl
    )
}

@JvmName("movieCastToUi")
fun List<MovieCast>.toListOfCastUi(): List<CastUi> {
    return this.map { it.toUi() }
}

fun TvShowCast.toUi(): CastUi {
    return CastUi(
        name = this.name,
        imageUrl = this.imageUrl
    )
}

@JvmName("tvShowCastToUi")
fun List<TvShowCast>.toListOfCastUi(): List<CastUi> {
    return this.map { it.toUi() }
}


fun MovieReview.toUi(): ReviewUi {
    return ReviewUi(
        avatarUrl = this.avatarUrl,
        username = this.username,
        name = this.name,
        rating = this.rating,
        createdAt = this.createdAt.formatToUi(),
        description = this.description
    )
}

fun TvShowReview.toUi(): ReviewUi {
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
        episodes = this.tvShowEpisodes.map { it.toUi() }
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
    val month = this.month.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day-$month-$year"
}

fun MovieGallery.toUi(): List<String> {
    return this.movieImages.map { it.url }
}

fun TvShowGallery.toUi(): List<String> {
    return this.tvShowImages.map { it.url }
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

@JvmName("movieReviewToUi")
fun List<MovieReview>.toListOfReviewUi(): List<ReviewUi> {
    return this.map { it.toUi() }
}

@JvmName("tvShowReviewToUi")
fun List<TvShowReview>.toListOfReviewUi(): List<ReviewUi> {
    return this.map { it.toUi() }
}

@JvmName("movieProductionToUi")
fun List<MovieProductionCompany>.toListOfProductionCompanyUi(): List<ProductionCompanyUi> {
    return this.map { it.toUi() }
}

@JvmName("tvShowProductionToUi")
fun List<TvShowProductionCompany>.toListOfProductionCompanyUi(): List<ProductionCompanyUi> {
    return this.map { it.toUi() }
}

fun List<Season>.toListOfSeasonUi(): List<SeasonUi> {
    return this.map { it.toUi() }
}

fun List<Episode>.toListOfEpisodeUi(): List<EpisodeUi> {
    return this.map { it.toUi() }
}