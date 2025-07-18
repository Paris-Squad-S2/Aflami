package com.paris_2.aflami.di

import com.datasource.MovieDetailDataBase
import com.datasource.local.TvShowDetailDataBase
import com.datasource.local.dao.MovieCastDao
import com.datasource.local.dao.MovieDao
import com.datasource.local.dao.MovieGalleryDao
import com.datasource.local.dao.MovieReviewDao
import com.datasource.local.dao.TvShowCastDao
import com.datasource.local.dao.TvShowDao
import com.datasource.local.dao.TvShowGalleryDao
import com.datasource.local.dao.TvShowReviewDao
import com.datasource.local.search.SearchDatabase
import com.datasource.local.search.dao.CountryDao
import com.datasource.local.search.dao.GenresDao
import com.datasource.local.search.dao.GenresUserInteractionDao
import com.datasource.local.search.dao.MediaDao
import com.datasource.local.search.dao.SearchHistoryDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { androidApplication().applicationContext }
    single { SearchDatabase.getInstance(androidApplication().applicationContext) }
    single<SearchHistoryDao> { get<SearchDatabase>().searchHistoryDao() }
    single<MediaDao> { get<SearchDatabase>().mediaDao() }
    single<CountryDao> { get<SearchDatabase>().countryDao() }
    single<GenresDao> { get<SearchDatabase>().genresDao() }
    single<GenresUserInteractionDao> { get<SearchDatabase>().genreUserInteractionDao() }
    single<MovieCastDao> { get<MovieDetailDataBase>().castDao() }
    single<MovieGalleryDao> { get<MovieDetailDataBase>().galleryDao() }
    single<MovieDao> { get<MovieDetailDataBase>().movieDao() }
    single<MovieReviewDao> { get<MovieDetailDataBase>().reviewDao() }
    single<TvShowCastDao> { get<TvShowDetailDataBase>().castDao() }
    single<TvShowGalleryDao> { get<TvShowDetailDataBase>().galleryDao() }
    single<TvShowDao> { get<TvShowDetailDataBase>().movieDao() }
    single<TvShowReviewDao> { get<TvShowDetailDataBase>().reviewDao() }
    single { MovieDetailDataBase.getInstance(androidApplication().applicationContext) }
    single { TvShowDetailDataBase.getInstance(androidApplication().applicationContext) }
}