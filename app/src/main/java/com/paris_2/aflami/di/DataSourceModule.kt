package com.paris_2.aflami.di

import com.datasource.local.datasource.MovieLocalDataSourceImp
import com.datasource.local.datasource.MovieMovieCastLocalDataSourceImp
import com.datasource.local.datasource.MovieMovieGalleryLocalDataSourceImp
import com.datasource.local.datasource.MovieMovieReviewLocalDataSourceImp
import com.datasource.local.datasource.TvShowCastLocalDataSourceImp
import com.datasource.local.datasource.TvShowGalleryLocalDataSourceImp
import com.datasource.local.datasource.TvShowLocalDataSourceImp
import com.datasource.local.datasource.TvShowReviewLocalDataSourceImp
import com.datasource.local.datasource.TvShowTvShowSeasonLocalDataSourceImp
import com.datasource.local.search.datasource.CountriesLocalDataSourceImpl
import com.datasource.local.search.datasource.GenresInteractionDataSourceImpl
import com.datasource.local.search.datasource.GenresLocalDataSourceImpl
import com.datasource.local.search.datasource.HistoryLocalDataSourceImpl
import com.datasource.local.search.datasource.MediaLocalDataSourceImpl
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataSourceModule = module {
    singleOf(::HistoryLocalDataSourceImpl) bind HistoryLocalDataSource::class
    singleOf(::MediaLocalDataSourceImpl) bind MediaLocalDataSource::class
    singleOf(::CountriesLocalDataSourceImpl) bind CountriesLocalDataSource::class
    singleOf(::GenresLocalDataSourceImpl) bind GenresLocalDataSource::class
    singleOf(::MovieMovieGalleryLocalDataSourceImp) bind MovieGalleryLocalDataSource::class
    singleOf(::MovieMovieCastLocalDataSourceImp) bind MovieCastLocalDataSource::class
    singleOf(::MovieLocalDataSourceImp) bind MovieLocalDataSource::class
    singleOf(::MovieMovieReviewLocalDataSourceImp) bind MovieReviewLocalDataSource::class
    singleOf(::TvShowCastLocalDataSourceImp) bind TvShowCastLocalDataSource::class
    singleOf(::TvShowGalleryLocalDataSourceImp) bind TvShowGalleryLocalDataSource::class
    singleOf(::TvShowLocalDataSourceImp) bind TvShowLocalDataSource::class
    singleOf(::TvShowReviewLocalDataSourceImp) bind TvShowReviewLocalDataSource::class
    singleOf(::TvShowTvShowSeasonLocalDataSourceImp) bind TvShowSeasonLocalDataSource::class
    singleOf(::GenresInteractionDataSourceImpl) bind GenresInteractionDataSource::class
}