package com.paris_2.aflami.di

import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.paris_2.domain.media.useCase.AddMediaToLocalUseCase
import com.paris_2.domain.media.useCase.GetMediaFromLocalUseCase
import com.paris_2.domain.media.useCase.GetMoviesCategoriesUseCase
import com.paris_2.domain.media.useCase.GetPopularMediaUseCase
import com.paris_2.domain.media.useCase.GetTopRatingMediaUseCase
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.domain.media.useCase.movie.AddRatingToMovieUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieCastUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieDetailsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieGalleryUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieReviewsUseCase
import com.paris_2.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.paris_2.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.paris_2.domain.media.repository.CategoriesRepository
import com.paris_2.domain.media.repository.CountryRepository
import com.paris_2.domain.media.repository.GenresInteractionRepository
import com.paris_2.domain.media.repository.SearchHistoryRepository
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.paris_2.domain.media.useCase.AutoCompleteCountryUseCase
import com.paris_2.domain.media.useCase.ClearAllRecentSearchesUseCase
import com.paris_2.domain.media.useCase.ClearRecentSearchUseCase
import com.paris_2.domain.media.useCase.FilterMediaByRatingUseCase
import com.paris_2.domain.media.useCase.FilterMediaUseCase
import com.paris_2.domain.media.useCase.FilterUpComingMediaByCategoriesUseCase
import com.paris_2.domain.media.useCase.GetAllCategoriesUseCase
import com.paris_2.domain.media.useCase.GetAllRecentSearchesUseCase
import com.paris_2.domain.media.useCase.GetCountryCodeByNameUseCase
import com.paris_2.domain.media.useCase.GetMediaByActorNameUseCase
import com.paris_2.domain.media.useCase.GetMoviesOnlyByCountryNameUseCase
import com.paris_2.domain.media.useCase.GetUpComingMediaUseCase
import com.paris_2.domain.media.useCase.IncrementCategoryInteractionUseCase
import com.paris_2.domain.media.useCase.SearchByQueryUseCase
import com.paris_2.domain.media.useCase.SortingMediaByCategoriesInteractionUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieRecommendationsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieVideoUseCase
import com.paris_2.domain.media.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.paris_2.domain.user.repository.AuthenticationRepository
import com.paris_2.domain.user.usecase.GetForgetPasswordUrlUseCase
import com.paris_2.domain.user.usecase.GetRegisterUrlUseCase
import com.paris_2.domain.user.usecase.GetSessionIdUseCase
import com.paris_2.domain.user.usecase.GuestLoginUseCase
import com.paris_2.domain.user.usecase.HasAnySessionUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.LoginUseCase
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
    @Provides fun provideAutoCompleteCountryUseCase(countryRepository: CountryRepository) =
        AutoCompleteCountryUseCase(countryRepository)
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

