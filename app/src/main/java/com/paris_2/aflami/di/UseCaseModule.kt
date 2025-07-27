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
import com.paris_2.domain.authentication.usecase.GuestLoginUseCase
import com.paris_2.domain.authentication.usecase.HasAnySessionUseCase
import com.paris_2.domain.authentication.usecase.IsLoggedInUseCase
import com.paris_2.domain.authentication.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides fun provideGetAllRecentSearchesUseCase() = GetAllRecentSearchesUseCase()
    @Provides fun provideClearRecentSearchUseCase() = ClearRecentSearchUseCase()
    @Provides fun provideClearAllRecentSearchesUseCase() = ClearAllRecentSearchesUseCase()
    @Provides fun provideAutoCompleteCountryUseCase() = AutoCompleteCountryUseCase()
    @Provides fun provideGetCountryCodeByNameUseCase() = GetCountryCodeByNameUseCase()
    @Provides fun provideFilterMediaUseCase() = FilterMediaUseCase()
    @Provides fun provideFilterMediaByRatingUseCase() = FilterMediaByRatingUseCase()
    @Provides fun provideGetAllCategoriesUseCase() = GetAllCategoriesUseCase()
    @Provides fun provideGetMediaByActorNameUseCase() = GetMediaByActorNameUseCase()
    @Provides fun provideGetMoviesOnlyByCountryNameUseCase() = GetMoviesOnlyByCountryNameUseCase()
    @Provides fun provideSearchByQueryUseCase() = SearchByQueryUseCase()
    @Provides fun provideGetMovieCastUseCase() = GetMovieCastUseCase()
    @Provides fun provideGetMovieDetailsUseCase() = GetMovieDetailsUseCase()
    @Provides fun provideGetMovieGalleryUseCase() = GetMovieGalleryUseCase()
    @Provides fun provideGetMovieRecommendationsUseCase() = GetMovieRecommendationsUseCase()
    @Provides fun provideGetMovieReviewsUseCase() = GetMovieReviewsUseCase()
    @Provides fun provideGetMoviesProductionCompaniesUseCase() = GetMoviesProductionCompaniesUseCase()
    @Provides fun provideGetSeasonDetailsUseCase() = GetSeasonDetailsUseCase()
    @Provides fun provideGetTvShowCastUseCase() = GetTvShowCastUseCase()
    @Provides fun provideGetTvShowDetailsUseCase() = GetTvShowDetailsUseCase()
    @Provides fun provideGetTvShowGalleryUseCase() = GetTvShowGalleryUseCase()
    @Provides fun provideGetTvShowRecommendationsUseCase() = GetTvShowRecommendationsUseCase()
    @Provides fun provideGetTvShowReviewsUseCase() = GetTvShowReviewsUseCase()
    @Provides fun provideGetTvShowsProductionCompaniesUseCase() = GetTvShowsProductionCompaniesUseCase()
    @Provides fun provideIncrementCategoryInteractionUseCase() = IncrementCategoryInteractionUseCase()
    @Provides fun provideSortingMediaByCategoriesInteractionUseCase() = SortingMediaByCategoriesInteractionUseCase()
    @Provides fun provideGetTopRatingMediaUseCase() = GetTopRatingMediaUseCase()
    @Provides fun provideGetPopularMediaUseCase() = GetPopularMediaUseCase()
    @Provides fun provideGetMoviesCategoriesUseCase() = GetMoviesCategoriesUseCase()
    @Provides fun provideGetUpComingMediaUseCase() = GetUpComingMediaUseCase()
    @Provides fun provideFilterUpComingMediaByCategoriesUseCase() = FilterUpComingMediaByCategoriesUseCase()
    @Provides fun provideAddMediaToLocalUseCase() = AddMediaToLocalUseCase()
    @Provides fun provideGetMediaFromLocalUseCase() = GetMediaFromLocalUseCase()
    @Provides fun provideLoginUseCase() = LoginUseCase()
    @Provides fun provideGuestLoginUseCase() = GuestLoginUseCase()
    @Provides fun provideGetForgetPasswordUrlUseCase() = GetForgetPasswordUrlUseCase()
    @Provides fun provideGetRegisterUrlUseCase() = GetRegisterUrlUseCase()
    @Provides fun provideGetMovieVideoUseCase() = GetMovieVideoUseCase()
    @Provides fun provideGetTvShowVideoUseCase() = GetTvShowVideoUseCase()
    @Provides fun provideIsLoggedInUseCase() = IsLoggedInUseCase()
    @Provides fun provideHasAnySessionUseCase() = HasAnySessionUseCase()
    @Provides fun provideAddRatingToMovieUseCase() = AddRatingToMovieUseCase()
    @Provides fun provideAddRatingToTvShowUseCase() = AddRatingToTvShowUseCase()
    @Provides fun provideGetEpisodeVideoUseCase() = GetEpisodeVideoUseCase()
}

