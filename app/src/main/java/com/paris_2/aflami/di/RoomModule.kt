package com.paris_2.aflami.di

import android.content.Context
import androidx.room.Room
import com.datasource.MovieDetailDataBase
import com.datasource.local.TvShowDetailDataBase
import com.datasource.local.dao.MovieCastDao
import com.datasource.local.dao.MovieDao
import com.datasource.local.dao.MovieGalleryDao
import com.datasource.local.dao.MovieReviewDao
import com.datasource.local.dao.SeasonDao
import com.datasource.local.dao.TvShowCastDao
import com.datasource.local.dao.TvShowDao
import com.datasource.local.dao.TvShowGalleryDao
import com.datasource.local.dao.TvShowReviewDao
import com.datasource.local.search.SearchDatabase
import com.datasource.local.search.dao.CountryDao
import com.datasource.local.search.dao.GenresDao
import com.datasource.local.search.dao.MediaDao
import com.datasource.local.search.dao.SearchHistoryDao
import com.paris_2.aflami.di.DatabaseConstants.MOVIE_DATABASE_NAME
import com.paris_2.aflami.di.DatabaseConstants.SEARCH_DATABASE_NAME
import com.paris_2.aflami.di.DatabaseConstants.TV_SHOW_DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { androidApplication().applicationContext }
    single<SearchDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            SearchDatabase::class.java,
            SEARCH_DATABASE_NAME
        )
            .addMigrations(SearchDatabase.MIGRATION_1_2)
            .build()
    }

    single<MovieDetailDataBase> {
        Room.databaseBuilder(
            get<Context>(),
            MovieDetailDataBase::class.java,
            MOVIE_DATABASE_NAME
        )
            .build()
    }

    single<TvShowDetailDataBase> {
        Room.databaseBuilder(
            get<Context>(),
            TvShowDetailDataBase::class.java,
            TV_SHOW_DATABASE_NAME
        )
            .build()
    }

    single<SearchHistoryDao> { get<SearchDatabase>().searchHistoryDao() }
    single<MediaDao> { get<SearchDatabase>().mediaDao() }
    single<CountryDao> { get<SearchDatabase>().countryDao() }
    single<GenresDao> { get<SearchDatabase>().genresDao() }
    single<MovieCastDao> { get<MovieDetailDataBase>().castDao() }
    single<MovieGalleryDao> { get<MovieDetailDataBase>().galleryDao() }
    single<MovieDao> { get<MovieDetailDataBase>().movieDao() }
    single<MovieReviewDao> { get<MovieDetailDataBase>().reviewDao() }
    single<TvShowCastDao> { get<TvShowDetailDataBase>().castDao() }
    single<TvShowGalleryDao> { get<TvShowDetailDataBase>().galleryDao() }
    single<TvShowDao> { get<TvShowDetailDataBase>().movieDao() }
    single<TvShowReviewDao> { get<TvShowDetailDataBase>().reviewDao() }
    single<SeasonDao> { get<TvShowDetailDataBase>().seasonDao() }

}

object DatabaseConstants {
    const val MOVIE_DATABASE_NAME = "movie_database"
    const val TV_SHOW_DATABASE_NAME = "tv_show_database"
    const val SEARCH_DATABASE_NAME = "search_db"
}