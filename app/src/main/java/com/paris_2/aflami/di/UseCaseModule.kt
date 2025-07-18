package com.paris_2.aflami.di

import com.domain.mediaDetails.useCases.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.tvShows.AddTvShowToFavoriteUseCase
import com.domain.mediaDetails.useCases.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCases.tvShows.GetTvShowsProductionCompaniesUseCase
import com.domain.search.useCases.AutoCompleteCountryUseCase
import com.domain.search.useCases.ClearAllRecentSearchesUseCase
import com.domain.search.useCases.ClearRecentSearchUseCase
import com.domain.search.useCases.FilterByListOfCategoriesUseCase
import com.domain.search.useCases.FilterMediaByRatingUseCase
import com.domain.search.useCases.GetAllCategoriesUseCase
import com.domain.search.useCases.GetAllRecentSearchesUseCase
import com.domain.search.useCases.GetCountryCodeByNameUseCase
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCases.IncrementCategoryInteractionUseCase
import com.domain.search.useCases.SearchByQueryUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAllRecentSearchesUseCase(get()) }
    factory { ClearRecentSearchUseCase(get()) }
    factory { ClearAllRecentSearchesUseCase(get()) }
    factory { AutoCompleteCountryUseCase(get()) }
    factory { GetCountryCodeByNameUseCase(get()) }
    factory { FilterByListOfCategoriesUseCase() }
    factory { FilterMediaByRatingUseCase() }
    factory { GetAllCategoriesUseCase(get()) }
    factory { GetMediaByActorNameUseCase(get()) }
    factory { GetMoviesOnlyByCountryNameUseCase(get()) }
    factory { SearchByQueryUseCase(get()) }
    factory { IncrementCategoryInteractionUseCase(get()) }
    factory { SortingMediaByCategoriesInteractionUseCase(get()) }
    factoryOf(::GetAllRecentSearchesUseCase)
    factoryOf(::ClearRecentSearchUseCase)
    factoryOf(::ClearAllRecentSearchesUseCase)
    factoryOf(::AutoCompleteCountryUseCase)
    factoryOf(::GetCountryCodeByNameUseCase)
    factoryOf(::FilterByListOfCategoriesUseCase)
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
}

