package com.paris.aflami.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.work.WorkManager
import com.datasource.local.media.dao.MovieDao
import com.datasource.local.media.dao.TvShowDao
import com.datasource.local.media.datasource.MovieLocalDataSourceImpl
import com.datasource.local.media.datasource.TvShowLocalDataSourceImpl
import com.datasource.local.guessGame.dao.GamePointsDao
import com.datasource.local.guessGame.datasource.GamePointsLocalDataSourceImpl
import com.datasource.local.media.dao.CountryDao
import com.datasource.local.media.dao.GenresUserInteractionDao
import com.datasource.local.media.dao.MediaDao
import com.datasource.local.media.dao.SearchHistoryDao
import com.datasource.local.media.datasource.CountriesLocalDataSourceImpl
import com.datasource.local.media.datasource.GenresInteractionDataSourceImpl
import com.datasource.local.media.datasource.HistoryLocalDataSourceImpl
import com.datasource.local.media.datasource.MediaLocalDataSourceImpl
import com.datasource.remote.lists.ListsRemoteDataSourceImpl
import com.datasource.remote.lists.service.ListApiService
import com.paris.dataSource.local.user.AuthenticationLocalDataSourceImpl
import com.paris.dataSource.local.user.SettingLocalDataSourceImpl
import com.paris.datasource.remote.user.UserApi
import com.paris.datasource.remote.user.UserRemoteDataSourceImpl
import com.paris.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.paris.repository.user.dataSource.remote.UserRemoteDataSource
import com.repository.media.datasource.local.TvShowLocalDataSource
import com.repository.guessgame.datasource.local.GamePointsLocalDataSource
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.media.datasource.local.CountriesLocalDataSource
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.datasource.local.MovieLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideHistoryLocalDataSource(
        searchHistoryDao: SearchHistoryDao,
    ): HistoryLocalDataSource = HistoryLocalDataSourceImpl(searchHistoryDao)

    @Provides
    @Singleton
    fun provideHomeLocalDataSource(
        dao: MediaDao,
        workManager: WorkManager,
    ): MediaLocalDataSource = MediaLocalDataSourceImpl(
        dao,
        workManager = workManager
    )

    @Provides
    @Singleton
    fun provideCountriesLocalDataSource(
        dao: CountryDao,
    ): CountriesLocalDataSource = CountriesLocalDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideGenresInteractionDataSource(
        genresInteractionDao: GenresUserInteractionDao,
    ): GenresInteractionDataSource = GenresInteractionDataSourceImpl(genresInteractionDao)

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        workManager: WorkManager,
        movieDao: MovieDao,
    ): MovieLocalDataSource = MovieLocalDataSourceImpl(workManager, movieDao)

    @Provides
    @Singleton
    fun provideTvShowLocalDataSource(
        workManager: WorkManager,
        dao: TvShowDao,
    ): TvShowLocalDataSource = TvShowLocalDataSourceImpl(workManager, dao)


    @Provides
    @Singleton
    fun provideAuthenticationRemoteDataSource(
        apiService: UserApi
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideAuthenticationLocalDataSource(
        @ApplicationContext context: Context,
    ): AuthenticationLocalDataSource = AuthenticationLocalDataSourceImpl(context)

    @Singleton
    @Provides
    fun provideLocalDataStore(
        @ApplicationContext applicationContext: Context,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            applicationContext.preferencesDataStoreFile(DATA_STORE_NAME)
        }
    }

    @Singleton
    @Provides
    fun provideSettingLocalDataSource(
        dataStore: DataStore<Preferences>,
        @ApplicationContext context: Context
    ): SettingLocalDataSource {
        return SettingLocalDataSourceImpl(dataStore, context)
    }

    @Provides
    @Singleton
    fun provideListRemoteDataSource(
        listApiService: ListApiService
    ): ListsRemoteDataSource {
        return ListsRemoteDataSourceImpl(listApiService)
    }

    @Provides
    @Singleton
    fun provideGamePointsLocalDataSource(
        gamePointsDao: GamePointsDao
    ): GamePointsLocalDataSource = GamePointsLocalDataSourceImpl(gamePointsDao)

    private const val DATA_STORE_NAME = "AppPrefStorage"

}