package com.feature.mediaDetails.mediaDetailsUi.ui.mapper

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Episode
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.EpisodeUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.SeasonUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowUi
import kotlinx.datetime.LocalDate

fun Movie.toUi(): MovieUi {
    return MovieUi(
        id=this.id,
        posterUrl = this.posterPath,
        rating = this.voteAverage.toString(),
        title = this.title,
        genres = this.genres.map { it.name },
        releaseDate = this.releaseDate,
        runtime = "${this.runtime} min",
        country = this.country,
        description = this.description,
        productionCompanies = this.productionCompanies.map { it.toUi() }
    )
}

fun TvShow.toUi(): TvShowUi {
    return TvShowUi(
        id= this.id,
        posterUrl = this.posterPath,
        rating = this.voteAverage.toString(),
        title = this.title,
        genres = this.genres.map { it.name },
        releaseDate = this.releaseDate,
        runtime = "${this.runtime} min",
        country = this.country,
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


fun Review.toUi(): ReviewUi {
    return ReviewUi(
        avatarUrl = this.avatarUrl,
        username = this.username,
        name = this.name,
        rating = this.rating,
        createdAt = this.createdAt.formatToUi()
    )
}

fun Season.toUi(): SeasonUi {
    return SeasonUi(
        id = this.id,
        name = this.name,
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
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.month.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day-$month-$year"
}



