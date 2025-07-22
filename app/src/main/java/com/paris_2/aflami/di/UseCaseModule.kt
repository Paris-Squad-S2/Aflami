package com.paris_2.aflami.di

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
import com.paris_2.domain.movie.useCases.AddMovieToFavoriteUseCase
import com.paris_2.domain.movie.useCases.GetMovieCastUseCase
import com.paris_2.domain.movie.useCases.GetMovieDetailsUseCase
import com.paris_2.domain.movie.useCases.GetMovieGalleryUseCase
import com.paris_2.domain.movie.useCases.GetMovieRecommendationsUseCase
import com.paris_2.domain.movie.useCases.GetMovieReviewsUseCase
import com.paris_2.domain.movie.useCases.GetMoviesProductionCompaniesUseCase
import com.paris_2.domain.tvshow.useCases.AddTvShowToFavoriteUseCase
import com.paris_2.domain.tvshow.useCases.GetSeasonDetailsUseCase
import com.paris_2.domain.tvshow.useCases.GetTvShowCastUseCase
import com.paris_2.domain.tvshow.useCases.GetTvShowDetailsUseCase
import com.paris_2.domain.tvshow.useCases.GetTvShowGalleryUseCase
import com.paris_2.domain.tvshow.useCases.GetTvShowRecommendationsUseCase
import com.paris_2.domain.tvshow.useCases.GetTvShowReviewsUseCase
import com.paris_2.domain.tvshow.useCases.GetTvShowsProductionCompaniesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
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
    factoryOf(::IncrementCategoryInteractionUseCase)
    factoryOf(::SortingMediaByCategoriesInteractionUseCase)
}

