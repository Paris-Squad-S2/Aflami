package com.paris_2.aflami.di


import com.datasource.local.datasource.MovieCastLocalDataSourceImp
import com.datasource.local.datasource.MovieGalleryLocalDataSourceImp
import com.datasource.local.datasource.MovieLocalDataSourceImp
import com.datasource.local.datasource.MovieReviewLocalDataSourceImp
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import org.koin.dsl.module

val movieDetailModule = module {
    single<MovieCastLocalDataSource> { MovieCastLocalDataSourceImp(get()) }
    single<MovieGalleryLocalDataSource> { MovieGalleryLocalDataSourceImp(get()) }
    single<MovieReviewLocalDataSource> { MovieReviewLocalDataSourceImp(get()) }
    single<MovieLocalDataSource> { MovieLocalDataSourceImp(get()) }
}