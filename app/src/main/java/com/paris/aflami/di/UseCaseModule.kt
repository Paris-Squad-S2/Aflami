package com.paris.aflami.di

import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.domain.game.repositories.GamePointsRepository
import com.paris.domain.game.usecases.GenerateGuessActorSessionUseCase
import com.paris.domain.game.usecases.GenerateGuessMovieSessionUseCase
import com.paris.domain.game.usecases.GenerateWhenIsReleasedSessionUseCase
import com.paris.domain.game.usecases.GenerateWhichGenreSessionUseCase
import com.paris.domain.game.usecases.GetActorsMediaUseCase
import com.paris.domain.game.usecases.GetPopularActorsUseCase
import com.paris.domain.game.usecases.GetUserPointUseCase
import com.paris.domain.game.usecases.MoveToNextQuestionUseCase
import com.paris.domain.game.usecases.RemoveAnswerHintUseCase
import com.paris.domain.game.usecases.UpdatePointsUseCase
import com.paris.domain.game.usecases.UseHintUseCase
import com.paris.domain.lists.repository.ListsRepository
import com.paris.domain.lists.useCase.AddMovieToListUseCase
import com.paris.domain.lists.useCase.CreateListUseCase
import com.paris.domain.lists.useCase.DeleteListUseCase
import com.paris.domain.lists.useCase.GetListDetailsUseCase
import com.paris.domain.lists.useCase.GetListUseCase
import com.paris.domain.lists.useCase.GetMovieListIdUseCase
import com.paris.domain.lists.useCase.RemoveMovieFromListUseCase
import com.paris.domain.media.repository.CountryRepository
import com.paris.domain.media.repository.GenresInteractionRepository
import com.paris.domain.media.repository.MediaRepository
import com.paris.domain.media.repository.MovieRepository
import com.paris.domain.media.repository.SearchHistoryRepository
import com.paris.domain.media.repository.SearchMediaRepository
import com.paris.domain.media.repository.TvShowRepository
import com.paris.domain.media.useCase.media.AddWatchHistoryUseCase
import com.paris.domain.media.useCase.media.FilterMediaByRatingUseCase
import com.paris.domain.media.useCase.media.FilterMediaUseCase
import com.paris.domain.media.useCase.media.FilterRatedMediaUseCase
import com.paris.domain.media.useCase.media.FilterUpComingMediaByCategoriesUseCase
import com.paris.domain.media.useCase.media.FilterWatchHistoryUseCase
import com.paris.domain.media.useCase.media.GetMediaByActorNameUseCase
import com.paris.domain.media.useCase.media.GetPopularMediaUseCase
import com.paris.domain.media.useCase.media.GetTopRatingMediaUseCase
import com.paris.domain.media.useCase.media.GetUpComingMediaUseCase
import com.paris.domain.media.useCase.media.GetWatchHistoryUseCase
import com.paris.domain.media.useCase.media.SortingMediaByCategoriesInteractionUseCase
import com.paris.domain.media.useCase.movie.AddRatingToMovieUseCase
import com.paris.domain.media.useCase.movie.DeleteMovieRatingUseCase
import com.paris.domain.media.useCase.movie.GetMovieCastUseCase
import com.paris.domain.media.useCase.movie.GetMovieDetailsUseCase
import com.paris.domain.media.useCase.movie.GetMovieGalleryUseCase
import com.paris.domain.media.useCase.movie.GetMovieRecommendationsUseCase
import com.paris.domain.media.useCase.movie.GetMovieReviewsUseCase
import com.paris.domain.media.useCase.movie.GetMovieVideoUseCase
import com.paris.domain.media.useCase.movie.GetMoviesByCategoryUseCase
import com.paris.domain.media.useCase.movie.GetMoviesCategoriesUseCase
import com.paris.domain.media.useCase.movie.GetMoviesOnlyByCountryNameUseCase
import com.paris.domain.media.useCase.movie.GetMoviesProductionCompaniesUseCase
import com.paris.domain.media.useCase.search.AutoCompleteCountryUseCase
import com.paris.domain.media.useCase.search.ClearAllRecentSearchesUseCase
import com.paris.domain.media.useCase.search.ClearRecentSearchUseCase
import com.paris.domain.media.useCase.search.GetAllCategoriesUseCase
import com.paris.domain.media.useCase.search.GetAllRecentSearchesUseCase
import com.paris.domain.media.useCase.search.GetCountryCodeByNameUseCase
import com.paris.domain.media.useCase.search.IncrementCategoryInteractionUseCase
import com.paris.domain.media.useCase.search.SearchByQueryUseCase
import com.paris.domain.media.useCase.tvShows.AddRatingToTvShowUseCase
import com.paris.domain.media.useCase.tvShows.DeleteTvShowRatingUseCase
import com.paris.domain.media.useCase.tvShows.GetEpisodeVideoUseCase
import com.paris.domain.media.useCase.tvShows.GetSeasonDetailsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowDetailsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowGalleryUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowRecommendationsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowReviewsUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowVideoUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowsByCategoryUseCase
import com.paris.domain.media.useCase.tvShows.GetTvShowsProductionCompaniesUseCase
import com.paris.domain.user.repository.SettingRepository
import com.paris.domain.user.repository.UserRepository
import com.paris.domain.user.usecase.ManageSettingsUseCase
import com.paris.domain.user.usecase.auth.DeleteSessionIdUseCase
import com.paris.domain.user.usecase.auth.GetAccountIdUseCase
import com.paris.domain.user.usecase.auth.GetForgetPasswordUrlUseCase
import com.paris.domain.user.usecase.auth.GetRegisterUrlUseCase
import com.paris.domain.user.usecase.auth.GetSessionIdUseCase
import com.paris.domain.user.usecase.auth.GuestLoginUseCase
import com.paris.domain.user.usecase.auth.HasAnySessionUseCase
import com.paris.domain.user.usecase.auth.IsLoggedInUseCase
import com.paris.domain.user.usecase.auth.LoginUseCase
import com.paris.domain.user.usecase.onboarding.CompleteOnboardingUseCase
import com.paris.domain.user.usecase.onboarding.IsOnboardingCompletedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetMovieListIdUseCase(listsRepository: ListsRepository) =
        GetMovieListIdUseCase(listsRepository)
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
    fun provideGetAllCategoriesUseCase() = GetAllCategoriesUseCase()

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
    fun provideGetMoviesCategoriesUseCase() = GetMoviesCategoriesUseCase()

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
    fun provideGetMoviesByCategoryUseCase(mediaRepository: MediaRepository) =
        GetMoviesByCategoryUseCase(mediaRepository)

    @Provides
    fun provideGetTvShowsByCategoryUseCase(mediaRepository: MediaRepository) =
        GetTvShowsByCategoryUseCase(mediaRepository)
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
    fun provideLanguageUseCase(
        settingRepository: SettingRepository,
        userRepository: UserRepository,
    ) =
        ManageSettingsUseCase(settingRepository, userRepository)

    @Provides
    fun provideGetListUseCase(listRepository: ListsRepository) = GetListUseCase(listRepository)

    @Provides
    fun provideGetListDetailsUseCase(listRepository: ListsRepository) =
        GetListDetailsUseCase(listRepository)

    @Provides
    fun provideDeleteItemFromListUseCase(listRepository: ListsRepository) =
        DeleteListUseCase(listRepository)

    @Provides
    fun provideCreateListUseCase(listRepository: ListsRepository) =
        CreateListUseCase(listRepository)

    @Provides
    fun provideAddMovieToListUseCase(listRepository: ListsRepository) =
        AddMovieToListUseCase(listRepository)

    @Provides
    fun provideRemoveMovieFromListUseCase(listRepository: ListsRepository) =
        RemoveMovieFromListUseCase(listRepository)

    @Provides
    fun provideGetPopularActorsUseCase(repository : ActorPopularityRepository) =
        GetPopularActorsUseCase(repository)

    @Provides
    fun provideGetActorsMediaUseCase(useCase: GetPopularActorsUseCase) =
        GetActorsMediaUseCase(useCase)

    @Provides
    fun provideGuessActorSessionUseCase(repository: ActorPopularityRepository) =
        GenerateGuessActorSessionUseCase(repository)

    @Provides
    fun provideGuessMovieSessionUseCase(repository: ActorPopularityRepository) =
        GenerateGuessMovieSessionUseCase(repository)

    @Provides
    fun provideWhenIsReleasedSessionUseCase(repository: ActorPopularityRepository) =
        GenerateWhenIsReleasedSessionUseCase(repository)

    @Provides
    fun provideWhichGenreSessionUseCase(repository: ActorPopularityRepository) =
        GenerateWhichGenreSessionUseCase(repository)

    @Provides
    fun provideGetUserPointUseCase(
        repository: GamePointsRepository
    ) = GetUserPointUseCase(repository)

    @Provides
    fun provideRemoveAnswerHintUseCase() =
        RemoveAnswerHintUseCase()

    @Provides
    fun provideMoveToNextQuestionUseCase() =
        MoveToNextQuestionUseCase()

    @Provides
    fun provideUseHintUseCase(gamePointsRepository: GamePointsRepository) =
        UseHintUseCase(gamePointsRepository)

    @Provides
    fun provideUpdateUserGamePointsUseCase(repository: GamePointsRepository) =
        UpdatePointsUseCase(repository)

}