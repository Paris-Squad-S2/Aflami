package com.paris_2.aflami.di

import com.paris.domain.lists.repository.ListsRepository
import com.paris_2.domain.media.repository.CategoriesRepository
import com.paris_2.domain.media.repository.CountryRepository
import com.paris_2.domain.media.repository.GenresInteractionRepository
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.paris_2.domain.media.repository.SearchHistoryRepository
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.domain.user.repository.SettingRepository
import com.paris_2.domain.user.repository.UserRepository
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.paris_2.repository.user.dataSource.remote.UserRemoteDataSource
import com.paris_2.repository.user.repository.SettingRepositoryImp
import com.paris_2.repository.user.repository.UserRepositoryImpl
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.lists.ListsRepositoryImp
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.datasource.local.CountriesLocalDataSource
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.repository.CategoriesRepositoryImpl
import com.repository.media.repository.CountryRepositoryImpl
import com.repository.media.repository.GenresInteractionRepositoryImpl
import com.repository.media.repository.MediaRepositoryImpl
import com.repository.media.repository.MoviesCategoriesRepositoryImpl
import com.repository.media.repository.SearchHistoryRepositoryImpl
import com.repository.media.repository.SearchMediaRepositoryImpl
import com.repository.media.util.NetworkConnectionChecker
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.remote.MovieRemoteDataSource
import com.repository.movie.repository.MovieRepositoryImpl
import com.repository.repository.TvShowRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(
        historyLocalDataSource: HistoryLocalDataSource,
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(historyLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchMediaRepository(
        networkConnectionChecker: NetworkConnectionChecker,
        searchRemoteDataSource: SearchRemoteDataSource,
        searchHistoryLocalDataSource: HistoryLocalDataSource,
        settingLocalDataSource: SettingLocalDataSource,
    ): SearchMediaRepository {
        return SearchMediaRepositoryImpl(
            networkConnectionChecker,
            searchRemoteDataSource,
            searchHistoryLocalDataSource,
            settingLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCountryRepository(
        countriesLocalDataSource: CountriesLocalDataSource,
    ): CountryRepository {
        return CountryRepositoryImpl(countriesLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoriesRepository(
        networkConnectionChecker: NetworkConnectionChecker,
        genresRemoteDataSource: GenresRemoteDataSource,
        settingLocalDataSource: SettingLocalDataSource,
    ): CategoriesRepository {
        return CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresRemoteDataSource,
            settingLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideGenresInteractionRepository(
        dataSource: GenresInteractionDataSource,
    ): GenresInteractionRepository {
        return GenresInteractionRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: AuthenticationLocalDataSource,
    ): UserRepository =
        UserRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideLanguageRepository(
        settingLocalDataSource: SettingLocalDataSource,
    ): SettingRepository = SettingRepositoryImp(settingLocalDataSource)

    @Provides
    @Singleton
    fun provideDetailedMediaRepository(
        networkConnectionChecker: NetworkConnectionChecker,
        mediaRemoteDataSource: MediaRemoteDataSource,
        continueWatchingLocalDataSource: ContinueWatchingLocalDataSource,
        homeMediaLocalDataSource: HomeMediaLocalDataSource,
        settingLocalDataSource: SettingLocalDataSource,
    ): MediaRepository = MediaRepositoryImpl(
        networkConnectionChecker,
        mediaRemoteDataSource,
        continueWatchingLocalDataSource, homeMediaLocalDataSource,
        settingLocalDataSource
    )

    @Provides
    @Singleton
    fun provideMoviesCategoriesRepository(
        genresRemoteDataSource: GenresRemoteDataSource,
        networkConnectionChecker: NetworkConnectionChecker,
        settingLocalDataSource: SettingLocalDataSource,
    ): MoviesCategoriesRepository =
        MoviesCategoriesRepositoryImpl(
            genresRemoteDataSource,
            networkConnectionChecker,
            settingLocalDataSource
        )

    @Provides
    @Singleton
    fun provideDetailedMovieRepository(
        networkConnectionChecker: com.repository.movie.util.NetworkConnectionChecker,
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource,
        settingLocalDataSource: SettingLocalDataSource,
    ): MovieRepository = MovieRepositoryImpl(
        networkConnectionChecker,
        movieLocalDataSource,
        movieRemoteDataSource,
        settingLocalDataSource
    )

    @Provides
    @Singleton
    fun provideDetailedTvShowRepository(
        tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
        tvShowLocalDataSource: TvShowLocalDataSource,
        networkConnectionChecker: com.repository.util.NetworkConnectionChecker,
        settingLocalDataSource: SettingLocalDataSource,
    ): TvShowRepository = TvShowRepositoryImpl(
        tvShowDetailsRemoteDataSource,
        tvShowLocalDataSource,
        networkConnectionChecker,
        settingLocalDataSource
    )

    @Provides
    @Singleton
    fun provideListsTvShowRepository(
        listRemoteDataSource: ListsRemoteDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): ListsRepository = ListsRepositoryImp(listRemoteDataSource, userRemoteDataSource)
}
