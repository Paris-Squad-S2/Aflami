package com.paris_2.aflami.di

import com.domain.home.repository.MediaRepository
import com.domain.home.repository.MoviesCategoriesRepository
import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.search.repository.CategoriesRepository
import com.domain.search.repository.CountryRepository
import com.domain.search.repository.GenresInteractionRepository
import com.domain.search.repository.SearchHistoryRepository
import com.domain.search.repository.SearchMediaRepository
import com.paris_2.domain.authentication.repository.AuthenticationRepository
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource
import com.paris_2.repository.authentication.dataSource.remote.AuthenticationRemoteDataSource
import com.paris_2.repository.authentication.repository.AuthenticationRepositoryImpl
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.home.datasource.local.HomeMediaLocalDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.repository.MediaRepositoryImpl
import com.repository.home.repository.MoviesCategoriesRepositoryImpl
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.repository.MovieRepositoryImpl
import com.repository.repository.TvShowRepositoryImpl
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.repository.CategoriesRepositoryImpl
import com.repository.search.repository.CountryRepositoryImpl
import com.repository.search.repository.GenresInteractionRepositoryImpl
import com.repository.search.repository.SearchHistoryRepositoryImpl
import com.repository.search.repository.SearchMediaRepositoryImpl
import com.repository.search.util.NetworkConnectionChecker
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
        mediaLocalDataSource: MediaLocalDataSource,
        searchRemoteDataSource: SearchRemoteDataSource,
        searchHistoryLocalDataSource: HistoryLocalDataSource
    ): SearchMediaRepository {
        return SearchMediaRepositoryImpl(
            networkConnectionChecker,
            mediaLocalDataSource,
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
        genresLocalDataSource: GenresLocalDataSource,
        genresRemoteDataSource: com.repository.search.dataSource.remote.GenresRemoteDataSource

    ): CategoriesRepository {
        return CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresLocalDataSource,
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
        networkConnectionChecker: com.repository.home.util.NetworkConnectionChecker,
        mediaRemoteDataSource: MediaRemoteDataSource,
        homeMediaLocalDataSource: HomeMediaLocalDataSource
    ): MediaRepository = MediaRepositoryImpl(networkConnectionChecker, mediaRemoteDataSource, homeMediaLocalDataSource)

    @Provides
    @Singleton
    fun provideMoviesCategoriesRepository(
        genresRemoteDataSource: com.repository.home.datasource.remote.GenresRemoteDataSource,
        networkConnectionChecker: com.repository.home.util.NetworkConnectionChecker
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
