package com.feature.mediaDetails.mediaDetailsUi.di

import com.domain.mediaDetails.useCases.movie.GetMovieMediaUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.AddMovieToListUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase

import com.domain.mediaDetails.useCases.tvShows.AddTvShowToFavoriteUseCase
import com.domain.mediaDetails.useCases.tvShows.AddTvShowToListUseCase
import com.domain.mediaDetails.useCases.tvShows.GetSeasonsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowMediaUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowsProductionCompaniesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetMovieMediaUseCase() }
    factory { GetMovieDetailsUseCase() }
    factory { GetMovieCastUseCase() }
    factory { AddMovieToListUseCase() }
    factory { GetMoviesProductionCompaniesUseCase() }
    factory { AddMovieToFavoriteUseCase() }
    factory { GetMovieReviewsUseCase() }

    factory { AddTvShowToFavoriteUseCase() }
    factory { AddTvShowToListUseCase() }
    factory { GetSeasonsUseCase() }
    factory { GetTvShowCastUseCase() }
    factory { GetTvShowDetailsUseCase() }
    factory { GetTvShowMediaUseCase() }
    factory { GetTvShowReviewsUseCase() }
    factory { GetTvShowsProductionCompaniesUseCase() }
}