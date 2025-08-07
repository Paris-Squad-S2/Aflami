package com.paris_2.aflami.di

import com.paris_2.domain.media.repository.CategoriesRepository
import com.paris_2.domain.media.repository.CountryRepository
import com.paris_2.domain.media.repository.GenresInteractionRepository
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.paris_2.domain.media.repository.SearchHistoryRepository
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.domain.user.repository.AuthenticationRepository
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.user.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.user.repository.AuthenticationRepositoryImpl
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
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
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
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
        historyLocalDataSource: HistoryLocalDataSource
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(historyLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchMediaRepository(
        networkConnectionChecker: NetworkConnectionChecker,
        searchRemoteDataSource: SearchRemoteDataSource,
        searchHistoryLocalDataSource: HistoryLocalDataSource
    ): SearchMediaRepository {
        return SearchMediaRepositoryImpl(
            networkConnectionChecker,
            searchRemoteDataSource,
            searchHistoryLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCountryRepository(
        countriesLocalDataSource: CountriesLocalDataSource
    ): CountryRepository {
        return CountryRepositoryImpl(countriesLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoriesRepository(
        networkConnectionChecker: NetworkConnectionChecker,
        genresRemoteDataSource: GenresRemoteDataSource

    ): CategoriesRepository {
        return CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideGenresInteractionRepository(
        dataSource: GenresInteractionDataSource
    ): GenresInteractionRepository {
        return GenresInteractionRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        remoteDataSource: AuthenticationRemoteDataSource,
        localDataSource: AuthenticationLocalDataSource
    ): AuthenticationRepository = AuthenticationRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideDetailedMediaRepository(
        networkConnectionChecker: NetworkConnectionChecker,
        mediaRemoteDataSource: MediaRemoteDataSource,
        continueWatchingLocalDataSource: ContinueWatchingLocalDataSource,
        homeMediaLocalDataSource: HomeMediaLocalDataSource
    ): MediaRepository = MediaRepositoryImpl(networkConnectionChecker, mediaRemoteDataSource, continueWatchingLocalDataSource,homeMediaLocalDataSource)

    @Provides
    @Singleton
    fun provideMoviesCategoriesRepository(
        genresRemoteDataSource: GenresRemoteDataSource,
        networkConnectionChecker: NetworkConnectionChecker
    ): MoviesCategoriesRepository = MoviesCategoriesRepositoryImpl(genresRemoteDataSource, networkConnectionChecker)

    @Provides
    @Singleton
    fun provideDetailedMovieRepository(
        networkConnectionChecker: com.repository.movie.util.NetworkConnectionChecker,
        movieLocalDataSource: MovieLocalDataSource,
        movieCastLocalDataSource: MovieCastLocalDataSource,
        movieGalleryLocalDataSource: MovieGalleryLocalDataSource,
        movieReviewLocalDataSource: MovieReviewLocalDataSource,
        movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
        movieSimilarLocalDataSource: MovieSimilarLocalDataSource
    ): MovieRepository = MovieRepositoryImpl(
        networkConnectionChecker,
        movieLocalDataSource,
        movieCastLocalDataSource,
        movieGalleryLocalDataSource,
        movieReviewLocalDataSource,
        movieDetailsRemoteDataSource,
        movieSimilarLocalDataSource
    )

    @Provides
    @Singleton
    fun provideDetailedTvShowRepository(
        tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
        tvShowCastLocalDataSource: TvShowCastLocalDataSource,
        tvShowGalleryLocalDataSource: TvShowGalleryLocalDataSource,
        tvShowReviewLocalDataSource: TvShowReviewLocalDataSource,
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowSeasonLocalDataSource: TvShowSeasonLocalDataSource,
        tvShowSimilarLocalDataSource: TvShowSimilarLocalDataSource,
        networkConnectionChecker: com.repository.util.NetworkConnectionChecker
    ): TvShowRepository = TvShowRepositoryImpl(
        tvShowDetailsRemoteDataSource,
        tvShowCastLocalDataSource,
        tvShowGalleryLocalDataSource,
        tvShowReviewLocalDataSource,
        tvShowLocalDataSource,
        tvShowSeasonLocalDataSource,
        tvShowSimilarLocalDataSource,
        networkConnectionChecker
    )

}
