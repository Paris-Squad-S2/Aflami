package com.paris_2.aflami.di

import com.datasource.local.datasource.TvShowCastLocalDataSourceImp
import com.datasource.local.datasource.TvShowGalleryLocalDataSourceImp
import com.datasource.local.datasource.TvShowLocalDataSourceImp
import com.datasource.local.datasource.TvShowReviewLocalDataSourceImp
import com.datasource.local.datasource.TvShowSeasonLocalDataSourceImp
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import org.koin.dsl.module

val tvShowDetailsModule = module {
    single<TvShowCastLocalDataSource> { TvShowCastLocalDataSourceImp(get()) }
    single<TvShowGalleryLocalDataSource> { TvShowGalleryLocalDataSourceImp(get()) }
    single<TvShowReviewLocalDataSource> { TvShowReviewLocalDataSourceImp(get()) }
    single<TvShowSeasonLocalDataSource> { TvShowSeasonLocalDataSourceImp(get()) }
    single<TvShowLocalDataSource> { TvShowLocalDataSourceImp(get()) }
}