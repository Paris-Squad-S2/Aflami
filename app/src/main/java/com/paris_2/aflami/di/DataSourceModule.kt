package com.paris_2.aflami.di

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
import com.datasource.local.home.datasource.HomeMediaLocalDataSourceImpl
import com.datasource.local.search.datasource.CountriesLocalDataSourceImpl
import com.datasource.local.search.datasource.GenresInteractionDataSourceImpl
import com.datasource.local.search.datasource.GenresLocalDataSourceImpl
import com.datasource.local.search.datasource.HistoryLocalDataSourceImpl
import com.datasource.local.search.datasource.MediaLocalDataSourceImpl
import com.paris_2.dataSource.local.authentication.AuthenticationLocalDataSourceImpl
import com.paris_2.datasource.remote.authentication.AuthenticationRemoteDataSourceImpl
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides @Singleton fun provideHistoryLocalDataSource(impl: HistoryLocalDataSourceImpl): HistoryLocalDataSource = impl
    @Provides @Singleton fun provideMediaLocalDataSource(impl: MediaLocalDataSourceImpl): MediaLocalDataSource = impl
    @Provides @Singleton fun provideCountriesLocalDataSource(impl: CountriesLocalDataSourceImpl): CountriesLocalDataSource = impl
    @Provides @Singleton fun provideGenresLocalDataSource(impl: GenresLocalDataSourceImpl): GenresLocalDataSource = impl
    @Provides @Singleton fun provideMovieGalleryLocalDataSource(impl: MovieGalleryLocalDataSourceImp): MovieGalleryLocalDataSource = impl
    @Provides @Singleton fun provideMovieCastLocalDataSource(impl: MovieCastLocalDataSourceImp): MovieCastLocalDataSource = impl
    @Provides @Singleton fun provideMovieLocalDataSource(impl: MovieLocalDataSourceImp): MovieLocalDataSource = impl
    @Provides @Singleton fun provideMovieReviewLocalDataSource(impl: MovieReviewLocalDataSourceImp): MovieReviewLocalDataSource = impl
    @Provides @Singleton fun provideTvShowCastLocalDataSource(impl: TvShowCastLocalDataSourceImp): TvShowCastLocalDataSource = impl
    @Provides @Singleton fun provideTvShowGalleryLocalDataSource(impl: TvShowGalleryLocalDataSourceImp): TvShowGalleryLocalDataSource = impl
    @Provides @Singleton fun provideTvShowLocalDataSource(impl: TvShowLocalDataSourceImp): TvShowLocalDataSource = impl
    @Provides @Singleton fun provideTvShowReviewLocalDataSource(impl: TvShowReviewLocalDataSourceImp): TvShowReviewLocalDataSource = impl
    @Provides @Singleton fun provideTvShowSeasonLocalDataSource(impl: TvShowSeasonLocalDataSourceImp): TvShowSeasonLocalDataSource = impl
    @Provides @Singleton fun provideTvShowSimilarLocalDataSource(impl: TvShowSimilarLocalDataSourceImpl): TvShowSimilarLocalDataSource = impl
    @Provides @Singleton fun provideGenresInteractionDataSource(impl: GenresInteractionDataSourceImpl): GenresInteractionDataSource = impl
    @Provides @Singleton fun provideMovieSimilarLocalDataSource(impl: MovieSimilarLocalDataSourceImp): MovieSimilarLocalDataSource = impl
    @Provides @Singleton fun provideHomeMediaLocalDataSource(impl: HomeMediaLocalDataSourceImpl): HomeMediaLocalDataSource = impl
    @Provides @Singleton fun provideAuthenticationRemoteDataSource(impl: AuthenticationRemoteDataSourceImpl): AuthenticationRemoteDataSource = impl
    @Provides @Singleton fun provideAuthenticationLocalDataSource(impl: AuthenticationLocalDataSourceImpl): AuthenticationLocalDataSource = impl
}

