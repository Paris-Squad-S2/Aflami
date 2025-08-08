package com.paris_2.aflami.di

import com.paris.domain.lists.repository.ListsRepository
import com.paris.domain.lists.useCase.AddMovieToListUseCase
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.DeleteListUseCase
import com.paris.domain.lists.useCase.GetListDetailsUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris.domain.lists.useCase.RemoveMovieFromListUseCase
import com.paris_2.domain.media.repository.CategoriesRepository
import com.paris_2.domain.media.repository.CountryRepository
import com.paris_2.domain.media.repository.GenresInteractionRepository
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.paris_2.domain.media.repository.SearchHistoryRepository
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.domain.media.useCase.AddWatchHistoryUseCase
import com.paris_2.domain.media.useCase.AutoCompleteCountryUseCase
import com.paris_2.domain.media.useCase.ClearAllRecentSearchesUseCase
import com.paris_2.domain.media.useCase.ClearRecentSearchUseCase
import com.paris_2.domain.media.useCase.FilterMediaByRatingUseCase
import com.paris_2.domain.media.useCase.FilterMediaUseCase
import com.paris_2.domain.media.useCase.FilterRatedMediaUseCase
import com.paris_2.domain.media.useCase.FilterUpComingMediaByCategoriesUseCase
import com.paris_2.domain.media.useCase.FilterWatchHistoryUseCase
import com.paris_2.domain.media.useCase.GetAllCategoriesUseCase
import com.paris_2.domain.media.useCase.GetAllRecentSearchesUseCase
import com.paris_2.domain.media.useCase.GetCountryCodeByNameUseCase
import com.paris_2.domain.media.useCase.GetMediaByActorNameUseCase
import com.paris_2.domain.media.useCase.GetMoviesCategoriesUseCase
import com.paris_2.domain.media.useCase.GetMoviesOnlyByCountryNameUseCase
import com.paris_2.domain.media.useCase.GetPopularMediaUseCase
import com.paris_2.domain.media.useCase.GetTopRatingMediaUseCase
import com.paris_2.domain.media.useCase.GetUpComingMediaUseCase
import com.paris_2.domain.media.useCase.GetWatchHistoryUseCase
import com.paris_2.domain.media.useCase.IncrementCategoryInteractionUseCase
import com.paris_2.domain.media.useCase.SearchByQueryUseCase
import com.paris_2.domain.media.useCase.SortingMediaByCategoriesInteractionUseCase
import com.paris_2.domain.media.useCase.movie.AddRatingToMovieUseCase
import com.paris_2.domain.media.useCase.movie.DeleteMovieRatingUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieCastUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieDetailsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieGalleryUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieRecommendationsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieReviewsUseCase
import com.paris_2.domain.media.useCase.movie.GetMovieVideoUseCase
import com.paris_2.domain.media.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.paris_2.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.paris_2.domain.media.useCase.tvShows.DeleteTvShowRatingUseCase
import com.paris_2.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.paris_2.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.paris_2.domain.user.repository.SettingRepository
import com.paris_2.domain.user.repository.UserRepository
import com.paris_2.domain.user.usecase.CompleteOnboardingUseCase
import com.paris_2.domain.user.usecase.DeleteSessionIdUseCase
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import com.paris_2.domain.user.usecase.GetForgetPasswordUrlUseCase
import com.paris_2.domain.user.usecase.GetRegisterUrlUseCase
import com.paris_2.domain.user.usecase.GetSessionIdUseCase
import com.paris_2.domain.user.usecase.GuestLoginUseCase
import com.paris_2.domain.user.usecase.HasAnySessionUseCase
import com.paris_2.domain.user.usecase.IsLoggedInUseCase
import com.paris_2.domain.user.usecase.IsOnboardingCompletedUseCase
import com.paris_2.domain.user.usecase.LoginUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetAllRecentSearchesUseCase(searchHistoryRepository: SearchHistoryRepository) =
        GetAllRecentSearchesUseCase(searchHistoryRepository)

    @Provides
    fun provideClearRecentSearchUseCase(searchHistoryRepository: SearchHistoryRepository) =
        ClearRecentSearchUseCase(searchHistoryRepository)

    @Provides
    fun provideClearAllRecentSearchesUseCase(searchHistoryRepository: SearchHistoryRepository) =
        ClearAllRecentSearchesUseCase(searchHistoryRepository)

    @Provides
    fun provideAutoCompleteCountryUseCase(countryRepository: CountryRepository) =
        AutoCompleteCountryUseCase(countryRepository)

    @Provides
    fun provideCompleteOnboardingUseCase(settingRepository: SettingRepository) =
        CompleteOnboardingUseCase(settingRepository)

    @Provides
    fun provideIsOnboardingCompletedUseCase(
        settingRepository: SettingRepository,
    ): IsOnboardingCompletedUseCase {
        return IsOnboardingCompletedUseCase(settingRepository)
    }


    @Provides
    fun provideGetCountryCodeByNameUseCase(countryRepository: CountryRepository) =
        GetCountryCodeByNameUseCase(countryRepository)

    @Provides
    fun provideFilterMediaUseCase() = FilterMediaUseCase()

    @Provides
    fun provideFilterMediaByRatingUseCase() = FilterMediaByRatingUseCase()

    @Provides
    fun provideGetAllCategoriesUseCase(categoriesRepository: CategoriesRepository) =
        GetAllCategoriesUseCase(categoriesRepository)

    @Provides
    fun provideGetMediaByActorNameUseCase(searchMediaRepository: SearchMediaRepository) =
        GetMediaByActorNameUseCase(searchMediaRepository)

    @Provides
    fun provideGetMoviesOnlyByCountryNameUseCase(searchMediaRepository: SearchMediaRepository) =
        GetMoviesOnlyByCountryNameUseCase(searchMediaRepository)

    @Provides
    fun provideSearchByQueryUseCase(searchMediaRepository: SearchMediaRepository) =
        SearchByQueryUseCase(searchMediaRepository)

    @Provides
    fun provideGetMovieCastUseCase(movieRepository: MovieRepository) =
        GetMovieCastUseCase(movieRepository)

    @Provides
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository) =
        GetMovieDetailsUseCase(movieRepository)

    @Provides
    fun provideGetMovieGalleryUseCase(movieRepository: MovieRepository) =
        GetMovieGalleryUseCase(movieRepository)

    @Provides
    fun provideGetMovieRecommendationsUseCase(movieRepository: MovieRepository) =
        GetMovieRecommendationsUseCase(movieRepository)

    @Provides
    fun provideGetMovieReviewsUseCase(movieRepository: MovieRepository) =
        GetMovieReviewsUseCase(movieRepository)

    @Provides
    fun provideDeleteMovieRatingUseCase(movieRepository: MovieRepository) =
        DeleteMovieRatingUseCase(movieRepository)

    @Provides
    fun provideGetMoviesProductionCompaniesUseCase(movieRepository: MovieRepository) =
        GetMoviesProductionCompaniesUseCase(movieRepository)

    @Provides
    fun provideGetSeasonDetailsUseCase(tvShowRepository: TvShowRepository) =
        GetSeasonDetailsUseCase(tvShowRepository)

    @Provides
    fun provideGetTvShowCastUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowCastUseCase(tvShowRepository)

    @Provides
    fun provideDeleteTvShowRatingUseCase(tvShowRepository: TvShowRepository) =
        DeleteTvShowRatingUseCase(tvShowRepository)

    @Provides
    fun provideGetTvShowDetailsUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowDetailsUseCase(tvShowRepository)

    @Provides
    fun provideGetTvShowGalleryUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowGalleryUseCase(tvShowRepository)

    @Provides
    fun provideGetTvShowRecommendationsUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowRecommendationsUseCase(tvShowRepository)

    @Provides
    fun provideGetTvShowReviewsUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowReviewsUseCase(tvShowRepository)

    @Provides
    fun provideGetTvShowsProductionCompaniesUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowsProductionCompaniesUseCase(tvShowRepository)

    @Provides
    fun provideIncrementCategoryInteractionUseCase(genresInteractionRepository: GenresInteractionRepository) =
        IncrementCategoryInteractionUseCase(genresInteractionRepository)

    @Provides
    fun provideSortingMediaByCategoriesInteractionUseCase(genresInteractionRepository: GenresInteractionRepository) =
        SortingMediaByCategoriesInteractionUseCase(genresInteractionRepository)

    @Provides
    fun provideGetTopRatingMediaUseCase(mediaRepository: MediaRepository) =
        GetTopRatingMediaUseCase(mediaRepository)

    @Provides
    fun provideGetPopularMediaUseCase(mediaRepository: MediaRepository) =
        GetPopularMediaUseCase(mediaRepository)

    @Provides
    fun provideGetMoviesCategoriesUseCase(moviesCategoriesRepository: MoviesCategoriesRepository) =
        GetMoviesCategoriesUseCase(moviesCategoriesRepository)

    @Provides
    fun provideGetUpComingMediaUseCase(mediaRepository: MediaRepository) =
        GetUpComingMediaUseCase(mediaRepository)

    @Provides
    fun provideFilterUpComingMediaByCategoriesUseCase(mediaRepository: MediaRepository) =
        FilterUpComingMediaByCategoriesUseCase(mediaRepository)

    @Provides
    fun provideAddMediaToLocalUseCase(mediaRepository: MediaRepository) =
        AddWatchHistoryUseCase(mediaRepository)

    @Provides
    fun provideFilterWatchHistoryUseCase(mediaRepository: MediaRepository) =
        FilterWatchHistoryUseCase(mediaRepository)

    @Provides
    fun provideGetMediaFromLocalUseCase(mediaRepository: MediaRepository) =
        GetWatchHistoryUseCase(mediaRepository)

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository) =
        LoginUseCase(userRepository)

    @Provides
    fun provideGetAccountIdUseCase(userRepository: UserRepository) =
        GetAccountIdUseCase(userRepository)

    @Provides
    fun provideDeleteSessionIdUseCase(userRepository: UserRepository) =
        DeleteSessionIdUseCase(userRepository)

    @Provides
    fun provideGuestLoginUseCase(userRepository: UserRepository) =
        GuestLoginUseCase(userRepository)

    @Provides
    fun provideGetForgetPasswordUrlUseCase(userRepository: UserRepository) =
        GetForgetPasswordUrlUseCase(userRepository)

    @Provides
    fun provideGetRegisterUrlUseCase(userRepository: UserRepository) =
        GetRegisterUrlUseCase(userRepository)

    @Provides
    fun provideGetMovieVideoUseCase(movieRepository: MovieRepository) =
        GetMovieVideoUseCase(movieRepository)

    @Provides
    fun provideGetTvShowVideoUseCase(tvShowRepository: TvShowRepository) =
        GetTvShowVideoUseCase(tvShowRepository)

    @Provides
    fun provideIsLoggedInUseCase(userRepository: UserRepository) =
        IsLoggedInUseCase(userRepository)

    @Provides
    fun provideHasAnySessionUseCase(userRepository: UserRepository) =
        HasAnySessionUseCase(userRepository)

    @Provides
    fun provideAddRatingToMovieUseCase(movieRepository: MovieRepository) =
        AddRatingToMovieUseCase(movieRepository)

    @Provides
    fun provideAddRatingToTvShowUseCase(tvShowRepository: TvShowRepository) =
        AddRatingToTvShowUseCase(tvShowRepository)

    @Provides
    fun provideGetEpisodeVideoUseCase(tvShowRepository: TvShowRepository) =
        GetEpisodeVideoUseCase(tvShowRepository)

    @Provides
    fun provideGetSessionIdUseCase(userRepository: UserRepository) =
        GetSessionIdUseCase(userRepository)

    @Provides
    fun provideFilterRatedMediaUseCase(mediaRepository: MediaRepository): FilterRatedMediaUseCase =
        FilterRatedMediaUseCase(mediaRepository)

    @Provides
    fun provideLanguageUseCase(settingRepository: SettingRepository) =
        SettingsUseCase(settingRepository)

    @Provides
    fun provideGetListUseCase(listRepository: ListsRepository) = GetListUseCase(listRepository)

    @Provides
    fun provideGetListDetailsUseCase(listRepository: ListsRepository) = GetListDetailsUseCase(listRepository)

    @Provides
    fun provideDeleteItemFromListUseCase(listRepository: ListsRepository) = DeleteListUseCase(listRepository)

    @Provides
    fun provideCreateListUseCase(listRepository: ListsRepository) = CreateListUseCase(listRepository)

    @Provides
    fun provideAddMovieToListUseCase(listRepository: ListsRepository) = AddMovieToListUseCase(listRepository)

    @Provides
    fun provideRemoveMovieFromListUseCase(listRepository: ListsRepository) = RemoveMovieFromListUseCase(listRepository)

}