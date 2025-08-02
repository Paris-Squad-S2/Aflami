package com.paris_2.aflami.di

import com.domain.home.repository.MediaRepository
import com.domain.home.repository.MoviesCategoriesRepository
import com.domain.home.usecase.AddMediaToLocalUseCase
import com.domain.home.usecase.FilterUpComingMediaByCategoriesUseCase
import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.domain.home.usecase.GetMoviesCategoriesUseCase
import com.domain.home.usecase.GetPopularMediaUseCase
import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.domain.home.usecase.GetUpComingMediaUseCase
import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.repository.TvShowRepository
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
import com.domain.media.repository.CategoriesRepository
import com.domain.media.repository.CountryRepository
import com.domain.media.repository.GenresInteractionRepository
import com.domain.media.repository.SearchHistoryRepository
import com.domain.media.repository.SearchMediaRepository
import com.domain.media.useCase.AutoCompleteCountryUseCase
import com.domain.media.useCase.ClearAllRecentSearchesUseCase
import com.domain.media.useCase.ClearRecentSearchUseCase
import com.domain.media.useCase.FilterMediaByRatingUseCase
import com.domain.media.useCase.FilterMediaUseCase
import com.domain.media.useCase.GetAllCategoriesUseCase
import com.domain.media.useCase.GetAllRecentSearchesUseCase
import com.domain.media.useCase.GetCountryCodeByNameUseCase
import com.domain.media.useCase.GetMediaByActorNameUseCase
import com.domain.media.useCase.GetMoviesOnlyByCountryNameUseCase
import com.domain.media.useCase.IncrementCategoryInteractionUseCase
import com.domain.media.useCase.SearchByQueryUseCase
import com.domain.media.useCase.SortingMediaByCategoriesInteractionUseCase
import com.domain.user.repository.AuthenticationRepository
import com.domain.user.usecase.GetForgetPasswordUrlUseCase
import com.domain.user.usecase.GetRegisterUrlUseCase
import com.domain.user.usecase.GetSessionIdUseCase
import com.domain.user.usecase.GuestLoginUseCase
import com.domain.user.usecase.HasAnySessionUseCase
import com.domain.user.usecase.IsLoggedInUseCase
import com.domain.user.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides fun provideGetAllRecentSearchesUseCase(searchHistoryRepository: SearchHistoryRepository) = GetAllRecentSearchesUseCase(searchHistoryRepository)
    @Provides fun provideClearRecentSearchUseCase(searchHistoryRepository: SearchHistoryRepository) = ClearRecentSearchUseCase(searchHistoryRepository)
    @Provides fun provideClearAllRecentSearchesUseCase(searchHistoryRepository: SearchHistoryRepository) = ClearAllRecentSearchesUseCase(searchHistoryRepository)
    @Provides fun provideAutoCompleteCountryUseCase(countryRepository: CountryRepository) = AutoCompleteCountryUseCase(countryRepository)
    @Provides fun provideGetCountryCodeByNameUseCase(countryRepository: CountryRepository) = GetCountryCodeByNameUseCase(countryRepository)
    @Provides fun provideFilterMediaUseCase() = FilterMediaUseCase()
    @Provides fun provideFilterMediaByRatingUseCase() = FilterMediaByRatingUseCase()
    @Provides fun provideGetAllCategoriesUseCase(categoriesRepository: CategoriesRepository) = GetAllCategoriesUseCase(categoriesRepository)
    @Provides fun provideGetMediaByActorNameUseCase(searchMediaRepository: SearchMediaRepository) = GetMediaByActorNameUseCase(searchMediaRepository)
    @Provides fun provideGetMoviesOnlyByCountryNameUseCase(searchMediaRepository: SearchMediaRepository) = GetMoviesOnlyByCountryNameUseCase(searchMediaRepository)
    @Provides fun provideSearchByQueryUseCase(searchMediaRepository: SearchMediaRepository) = SearchByQueryUseCase(searchMediaRepository)
    @Provides fun provideGetMovieCastUseCase(movieRepository: MovieRepository) = GetMovieCastUseCase(movieRepository)
    @Provides fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository) = GetMovieDetailsUseCase(movieRepository)
    @Provides fun provideGetMovieGalleryUseCase(movieRepository: MovieRepository) = GetMovieGalleryUseCase(movieRepository)
    @Provides fun provideGetMovieRecommendationsUseCase(movieRepository: MovieRepository) = GetMovieRecommendationsUseCase(movieRepository)
    @Provides fun provideGetMovieReviewsUseCase(movieRepository: MovieRepository) = GetMovieReviewsUseCase(movieRepository)
    @Provides fun provideGetMoviesProductionCompaniesUseCase(movieRepository: MovieRepository) = GetMoviesProductionCompaniesUseCase(movieRepository)
    @Provides fun provideGetSeasonDetailsUseCase(tvShowRepository: TvShowRepository) = GetSeasonDetailsUseCase(tvShowRepository)
    @Provides fun provideGetTvShowCastUseCase(tvShowRepository: TvShowRepository) = GetTvShowCastUseCase(tvShowRepository)
    @Provides fun provideGetTvShowDetailsUseCase(tvShowRepository: TvShowRepository) = GetTvShowDetailsUseCase(tvShowRepository)
    @Provides fun provideGetTvShowGalleryUseCase(tvShowRepository: TvShowRepository) = GetTvShowGalleryUseCase(tvShowRepository)
    @Provides fun provideGetTvShowRecommendationsUseCase(tvShowRepository: TvShowRepository) = GetTvShowRecommendationsUseCase(tvShowRepository)
    @Provides fun provideGetTvShowReviewsUseCase(tvShowRepository: TvShowRepository) = GetTvShowReviewsUseCase(tvShowRepository)
    @Provides fun provideGetTvShowsProductionCompaniesUseCase(tvShowRepository: TvShowRepository) = GetTvShowsProductionCompaniesUseCase(tvShowRepository)
    @Provides fun provideIncrementCategoryInteractionUseCase(genresInteractionRepository: GenresInteractionRepository) = IncrementCategoryInteractionUseCase(genresInteractionRepository)
    @Provides fun provideSortingMediaByCategoriesInteractionUseCase(genresInteractionRepository: GenresInteractionRepository) = SortingMediaByCategoriesInteractionUseCase(genresInteractionRepository)
    @Provides fun provideGetTopRatingMediaUseCase(mediaRepository: MediaRepository) = GetTopRatingMediaUseCase(mediaRepository)
    @Provides fun provideGetPopularMediaUseCase(mediaRepository: MediaRepository) = GetPopularMediaUseCase(mediaRepository)
    @Provides fun provideGetMoviesCategoriesUseCase(moviesCategoriesRepository: MoviesCategoriesRepository) = GetMoviesCategoriesUseCase(moviesCategoriesRepository)
    @Provides fun provideGetUpComingMediaUseCase(mediaRepository: MediaRepository) = GetUpComingMediaUseCase(mediaRepository)
    @Provides fun provideFilterUpComingMediaByCategoriesUseCase(mediaRepository: MediaRepository) = FilterUpComingMediaByCategoriesUseCase(mediaRepository)
    @Provides fun provideAddMediaToLocalUseCase(mediaRepository: MediaRepository) = AddMediaToLocalUseCase(mediaRepository)
    @Provides fun provideGetMediaFromLocalUseCase(mediaRepository: MediaRepository) = GetMediaFromLocalUseCase(mediaRepository)
    @Provides fun provideLoginUseCase(authenticationRepository: AuthenticationRepository) = LoginUseCase(authenticationRepository)
    @Provides fun provideGuestLoginUseCase(authenticationRepository: AuthenticationRepository) = GuestLoginUseCase(authenticationRepository)
    @Provides fun provideGetForgetPasswordUrlUseCase(authenticationRepository: AuthenticationRepository) = GetForgetPasswordUrlUseCase(authenticationRepository)
    @Provides fun provideGetRegisterUrlUseCase(authenticationRepository: AuthenticationRepository) = GetRegisterUrlUseCase(authenticationRepository)
    @Provides fun provideGetMovieVideoUseCase(movieRepository: MovieRepository) = GetMovieVideoUseCase(movieRepository)
    @Provides fun provideGetTvShowVideoUseCase(tvShowRepository: TvShowRepository) = GetTvShowVideoUseCase(tvShowRepository)
    @Provides fun provideIsLoggedInUseCase(authenticationRepository: AuthenticationRepository) = IsLoggedInUseCase(authenticationRepository)
    @Provides fun provideHasAnySessionUseCase(authenticationRepository: AuthenticationRepository) = HasAnySessionUseCase(authenticationRepository)
    @Provides fun provideAddRatingToMovieUseCase(movieRepository: MovieRepository) = AddRatingToMovieUseCase(movieRepository)
    @Provides fun provideAddRatingToTvShowUseCase(tvShowRepository: TvShowRepository) = AddRatingToTvShowUseCase(tvShowRepository)
    @Provides fun provideGetEpisodeVideoUseCase(tvShowRepository: TvShowRepository) = GetEpisodeVideoUseCase(tvShowRepository)
    @Provides fun provideGetSessionIdUseCase(authenticationRepository: AuthenticationRepository) = GetSessionIdUseCase(authenticationRepository)
}

