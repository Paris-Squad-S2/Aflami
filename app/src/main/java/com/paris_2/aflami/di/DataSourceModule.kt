package com.paris_2.aflami.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.work.WorkManager
import com.datasource.local.dao.MovieDao
import com.datasource.local.dao.TvShowDao
import com.datasource.local.datasource.MovieLocalDataSourceImp
import com.datasource.local.datasource.TvShowLocalDataSourceImp
import com.datasource.local.media.dao.ContinueWatchingDao
import com.datasource.local.media.dao.CountryDao
import com.datasource.local.media.dao.GenresUserInteractionDao
import com.datasource.local.media.dao.HomeMediaDao
import com.datasource.local.media.dao.SearchHistoryDao
import com.datasource.local.media.datasource.ContinueWatchingLocalDataSourceImpl
import com.datasource.local.media.datasource.CountriesLocalDataSourceImpl
import com.datasource.local.media.datasource.GenresInteractionDataSourceImpl
import com.datasource.local.media.datasource.HistoryLocalDataSourceImpl
import com.datasource.local.media.datasource.HomeMediaLocalDataSourceImpl
import com.datasource.remote.lists.ListsRemoteDataSourceImp
import com.datasource.remote.lists.service.ListApiService
import com.paris_2.dataSource.local.user.AuthenticationLocalDataSourceImpl
import com.paris_2.dataSource.local.user.SettingLocalDataSourceImp
import com.paris_2.datasource.remote.user.UserApi
import com.paris_2.datasource.remote.user.UserRemoteDataSourceImpl
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.paris_2.repository.user.dataSource.remote.UserRemoteDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.datasource.local.CountriesLocalDataSource
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
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
        dao: HomeMediaDao,
        workManager: WorkManager,
    ): HomeMediaLocalDataSource = HomeMediaLocalDataSourceImpl(
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
    ): MovieLocalDataSource = MovieLocalDataSourceImp(workManager, movieDao)

    @Provides
    @Singleton
    fun provideTvShowLocalDataSource(
        workManager: WorkManager,
        dao: TvShowDao,
    ): TvShowLocalDataSource = TvShowLocalDataSourceImp(workManager, dao)

    @Provides
    @Singleton
    fun provideHomeMediaLocalDataSource(
        mediaDao: ContinueWatchingDao
    ): ContinueWatchingLocalDataSource = ContinueWatchingLocalDataSourceImpl(mediaDao)

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
        dataStore: DataStore<Preferences>
    ): SettingLocalDataSource{
        return SettingLocalDataSourceImp(dataStore)
    }

    @Provides
    @Singleton
    fun provideListRemoteDataSource(
        listApiService: ListApiService
    ): ListsRemoteDataSource {
        return ListsRemoteDataSourceImp(listApiService)
    }

   private const val DATA_STORE_NAME = "AppPrefStorage"

}