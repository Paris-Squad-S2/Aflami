package com.paris_2.aflami.di

import com.domain.mediaDetails.useCase.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCase.tvShows.AddTvShowToFavoriteUseCase
import com.domain.mediaDetails.useCase.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.domain.search.useCase.AutoCompleteCountryUseCase
import com.domain.search.useCase.ClearAllRecentSearchesUseCase
import com.domain.search.useCase.ClearRecentSearchUseCase
import com.domain.search.useCase.FilterMediaUseCase
import com.domain.search.useCase.FilterMediaByRatingUseCase
import com.domain.search.useCase.GetAllCategoriesUseCase
import com.domain.search.useCase.GetAllRecentSearchesUseCase
import com.domain.search.useCase.GetCountryCodeByNameUseCase
import com.domain.search.useCase.GetMediaByActorNameUseCase
import com.domain.search.useCase.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCase.IncrementCategoryInteractionUseCase
import com.domain.search.useCase.SearchByQueryUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetAllRecentSearchesUseCase)
    factoryOf(::ClearRecentSearchUseCase)
    factoryOf(::ClearAllRecentSearchesUseCase)
    factoryOf(::AutoCompleteCountryUseCase)
    factoryOf(::GetCountryCodeByNameUseCase)
    factoryOf(::FilterMediaUseCase)
    factoryOf(::FilterMediaByRatingUseCase)
    factoryOf(::GetAllCategoriesUseCase)
    factoryOf(::GetMediaByActorNameUseCase)
    factoryOf(::GetMoviesOnlyByCountryNameUseCase)
    factoryOf(::SearchByQueryUseCase)
    factoryOf(::AddMovieToFavoriteUseCase)
    factoryOf(::GetMovieCastUseCase)
    factoryOf(::GetMovieDetailsUseCase)
    factoryOf(::GetMovieGalleryUseCase)
    factoryOf(::GetMovieRecommendationsUseCase)
    factoryOf(::GetMovieReviewsUseCase)
    factoryOf(::GetMoviesProductionCompaniesUseCase)
    factoryOf(::AddTvShowToFavoriteUseCase)
    factoryOf(::GetSeasonDetailsUseCase)
    factoryOf(::GetTvShowCastUseCase)
    factoryOf(::GetTvShowDetailsUseCase)
    factoryOf(::GetTvShowGalleryUseCase)
    factoryOf(::GetTvShowRecommendationsUseCase)
    factoryOf(::GetTvShowReviewsUseCase)
    factoryOf(::GetTvShowsProductionCompaniesUseCase)
    factoryOf(::IncrementCategoryInteractionUseCase)
    factoryOf(::SortingMediaByCategoriesInteractionUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::GuestLoginUseCase)
    factoryOf(::GetForgetPasswordUrlUseCase)
    factoryOf(::GetRegisterUrlUseCase)
}

