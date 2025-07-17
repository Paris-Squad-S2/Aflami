package com.paris_2.aflami.di

import com.datasource.local.datasource.CastLocalDataSourceImp
import com.datasource.local.datasource.GalleryLocalDataSourceImp
import com.datasource.local.datasource.ReviewLocalDataSourceImp
import com.datasource.local.datasource.TvShowLocalDataSourceImp
import com.repository.datasource.local.CastLocalDataSource
import com.repository.datasource.local.GalleryLocalDataSource
import com.repository.datasource.local.ReviewLocalDataSource
import com.repository.datasource.local.TvShowLocalDataSource
import org.koin.dsl.module

val movieDetailModule = module {
    single<CastLocalDataSource> { CastLocalDataSourceImp(get()) }
    single<GalleryLocalDataSource> { GalleryLocalDataSourceImp(get()) }
    single<ReviewLocalDataSource> { ReviewLocalDataSourceImp(get()) }
    single<TvShowLocalDataSource> { TvShowLocalDataSourceImp(get()) }
}