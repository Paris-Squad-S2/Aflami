package com.paris_2.aflami.di

import com.domain.home.usecase.AddMediaToLocalUseCase
import com.domain.home.usecase.FilterUpComingMediaByCategoriesUseCase
import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.domain.home.usecase.GetMoviesCategoriesUseCase
import com.domain.home.usecase.GetPopularMediaUseCase
import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.domain.home.usecase.GetUpComingMediaUseCase
import com.domain.mediaDetails.useCase.movie.AddRatingToMovieUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieGalleryUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieRecommendationsUseCase
import com.domain.mediaDetails.useCase.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.domain.mediaDetails.useCase.tvShows.AddRatingToTvShowUseCase
import com.domain.mediaDetails.useCase.tvShows.GetEpisodeVideoUseCase
import com.domain.mediaDetails.useCase.tvShows.GetSeasonDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowCastUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowDetailsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowGalleryUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowReviewsUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowVideoUseCase
import com.domain.mediaDetails.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieVideoUseCase
import com.domain.search.useCase.AutoCompleteCountryUseCase
import com.domain.search.useCase.ClearAllRecentSearchesUseCase
import com.domain.search.useCase.ClearRecentSearchUseCase
import com.domain.search.useCase.FilterMediaByRatingUseCase
import com.domain.search.useCase.FilterMediaUseCase
import com.domain.search.useCase.GetAllCategoriesUseCase
import com.domain.search.useCase.GetAllRecentSearchesUseCase
import com.domain.search.useCase.GetCountryCodeByNameUseCase
import com.domain.search.useCase.GetMediaByActorNameUseCase
import com.domain.search.useCase.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCase.IncrementCategoryInteractionUseCase
import com.domain.search.useCase.SearchByQueryUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import com.paris_2.domain.authentication.usecase.GetForgetPasswordUrlUseCase
import com.paris_2.domain.authentication.usecase.GetRegisterUrlUseCase
import com.paris_2.domain.authentication.usecase.GetSessionIdUseCase
import com.paris_2.domain.authentication.usecase.GuestLoginUseCase
import com.paris_2.domain.authentication.usecase.HasAnySessionUseCase
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
import com.paris_2.domain.authentication.usecase.LoginUseCase
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
    factoryOf(::GetMovieCastUseCase)
    factoryOf(::GetMovieDetailsUseCase)
    factoryOf(::GetMovieGalleryUseCase)
    factoryOf(::GetMovieRecommendationsUseCase)
    factoryOf(::GetMovieReviewsUseCase)
    factoryOf(::GetMoviesProductionCompaniesUseCase)
    factoryOf(::GetSeasonDetailsUseCase)
    factoryOf(::GetTvShowCastUseCase)
    factoryOf(::GetTvShowDetailsUseCase)
    factoryOf(::GetTvShowGalleryUseCase)
    factoryOf(::GetTvShowRecommendationsUseCase)
    factoryOf(::GetTvShowReviewsUseCase)
    factoryOf(::GetTvShowsProductionCompaniesUseCase)
    factoryOf(::IncrementCategoryInteractionUseCase)
    factoryOf(::SortingMediaByCategoriesInteractionUseCase)
    factoryOf(::GetTopRatingMediaUseCase)
    factoryOf(::GetPopularMediaUseCase)
    factoryOf(::GetMoviesCategoriesUseCase)
    factoryOf(::GetUpComingMediaUseCase)
    factoryOf(::FilterUpComingMediaByCategoriesUseCase)
    factoryOf(::AddMediaToLocalUseCase)
    factoryOf(::GetMediaFromLocalUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::GuestLoginUseCase)
    factoryOf(::GetForgetPasswordUrlUseCase)
    factoryOf(::GetRegisterUrlUseCase)
    factoryOf(::GetMovieVideoUseCase)
    factoryOf(::GetTvShowVideoUseCase)
    factoryOf(::IsLoggedInUseCase)
    factoryOf(::HasAnySessionUseCase)
    factoryOf(::AddRatingToMovieUseCase)
    factoryOf(::AddRatingToTvShowUseCase)
    factoryOf(::GetEpisodeVideoUseCase)
    factoryOf(::GetSessionIdUseCase)
}

