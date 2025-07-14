package com.feature.mediaDetails.mediaDetailsUi.di

import com.domain.mediaDetails.useCases.*
import com.domain.mediaDetails.useCases.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.tvShows.AddTvShowToFavoriteUseCase
import com.domain.mediaDetails.useCases.tvShows.GetSeasonsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowsProductionCompaniesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { AddMovieToFavoriteUseCase(get()) }
    factory { GetMovieCastUseCase(get()) }
    factory { GetMovieDetailsUseCase(get()) }
    factory { GetMovieGalleryUseCase(get()) }
    factory { GetMovieRecommendationsUseCase(get()) }
    factory { GetMovieReviewsUseCase(get()) }
    factory { GetMoviesProductionCompaniesUseCase(get()) }
    factory { AddTvShowToFavoriteUseCase(get()) }
    factory { GetSeasonsUseCase(get()) }
    factory { GetTvShowCastUseCase(get()) }
    factory { GetTvShowDetailsUseCase(get()) }
    factory { GetTvShowGalleryUseCase(get()) }
    factory { GetTvShowRecommendationsUseCase(get()) }
    factory { GetTvShowReviewsUseCase(get()) }
    factory { GetTvShowsProductionCompaniesUseCase(get()) }
}