package com.paris_2.aflami.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.work.WorkManager
import com.datasource.local.dao.MovieCastDao
import com.datasource.local.dao.MovieDao
import com.datasource.local.dao.MovieGalleryDao
import com.datasource.local.dao.MovieReviewDao
import com.datasource.local.dao.MovieSimilarDao
import com.datasource.local.dao.SeasonDao
import com.datasource.local.dao.TvShowCastDao
import com.datasource.local.dao.TvShowDao
import com.datasource.local.dao.TvShowGalleryDao
import com.datasource.local.dao.TvShowReviewDao
import com.datasource.local.dao.TvShowSimilarDao
import com.datasource.local.datasource.MovieCastLocalDataSourceImp
import com.datasource.local.datasource.MovieGalleryLocalDataSourceImp
import com.datasource.local.datasource.MovieLocalDataSourceImp
import com.datasource.local.datasource.MovieReviewLocalDataSourceImp
import com.datasource.local.datasource.MovieSimilarLocalDataSourceImp
import com.datasource.local.datasource.TvShowCastLocalDataSourceImp
import com.datasource.local.datasource.TvShowGalleryLocalDataSourceImp
import com.datasource.local.datasource.TvShowLocalDataSourceImp
import com.datasource.local.datasource.TvShowReviewLocalDataSourceImp
import com.datasource.local.datasource.TvShowSeasonLocalDataSourceImp
import com.datasource.local.datasource.TvShowSimilarLocalDataSourceImpl
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
import com.paris_2.dataSource.local.user.AuthenticationLocalDataSourceImpl
import com.paris_2.dataSource.local.user.LanguageLocalDataSourceRepositoryImp
import com.paris_2.datasource.remote.user.UserApi
import com.paris_2.datasource.remote.user.UserRemoteDataSourceImpl
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import com.paris_2.repository.user.dataSource.remote.UserRemoteDataSource
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.media.datasource.local.ContinueWatchingLocalDataSource
import com.repository.media.datasource.local.CountriesLocalDataSource
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
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
    fun provideMovieGalleryLocalDataSource(
        dao: MovieGalleryDao,
    ): MovieGalleryLocalDataSource = MovieGalleryLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideMovieCastLocalDataSource(
        dao: MovieCastDao,
    ): MovieCastLocalDataSource = MovieCastLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        workManager: WorkManager,
        dao: MovieDao,
    ): MovieLocalDataSource = MovieLocalDataSourceImp(workManager, dao)

    @Provides
    @Singleton
    fun provideMovieReviewLocalDataSource(
        dao: MovieReviewDao,
    ): MovieReviewLocalDataSource = MovieReviewLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideMovieSimilarLocalDataSource(
        similarDao: MovieSimilarDao,
    ): MovieSimilarLocalDataSource = MovieSimilarLocalDataSourceImp(similarDao)

    @Provides
    @Singleton
    fun provideTvShowCastLocalDataSource(
        dao: TvShowCastDao,
    ): TvShowCastLocalDataSource = TvShowCastLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideTvShowGalleryLocalDataSource(
        dao: TvShowGalleryDao,
    ): TvShowGalleryLocalDataSource = TvShowGalleryLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideTvShowLocalDataSource(
        workManager: WorkManager,
        dao: TvShowDao,
    ): TvShowLocalDataSource = TvShowLocalDataSourceImp(workManager, dao)

    @Provides
    @Singleton
    fun provideTvShowReviewLocalDataSource(
        dao: TvShowReviewDao,
    ): TvShowReviewLocalDataSource = TvShowReviewLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideTvShowSeasonLocalDataSource(
        dao: SeasonDao,
    ): TvShowSeasonLocalDataSource = TvShowSeasonLocalDataSourceImp(dao)

    @Provides
    @Singleton
    fun provideTvShowSimilarLocalDataSource(
        tvShowSimilarDao: TvShowSimilarDao,
    ): TvShowSimilarLocalDataSource = TvShowSimilarLocalDataSourceImpl(tvShowSimilarDao)

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

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("lang_prev")
        }

    @Provides
    @Singleton
    fun provideLanguageLocalDataSource(
       @ApplicationContext context: Context,
    ): LanguageLocalDataSourceRepository = LanguageLocalDataSourceRepositoryImp(context)

}