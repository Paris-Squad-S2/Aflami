package com.paris_2.aflami.di


import android.content.Context
import androidx.room.Room
import com.datasource.MovieDetailDataBase
import com.datasource.local.TvShowDetailDataBase
import com.datasource.local.dao.*
import com.datasource.local.home.HomeDatabase
import com.datasource.local.home.dao.HomeMediaDao
import com.datasource.local.search.SearchDatabase
import com.datasource.local.search.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideSearchDatabase(
        @ApplicationContext context: Context
    ): SearchDatabase = Room.databaseBuilder(
        context,
        SearchDatabase::class.java,
        DatabaseConstants.SEARCH_DATABASE_NAME
    ).addMigrations(SearchDatabase.MIGRATION_1_2).build()

    @Provides
    @Singleton
    fun provideMovieDetailDatabase(
        @ApplicationContext context: Context
    ): MovieDetailDataBase = Room.databaseBuilder(
        context,
        MovieDetailDataBase::class.java,
        DatabaseConstants.MOVIE_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideHomeDatabase(
        @ApplicationContext context: Context
    ): HomeDatabase = Room.databaseBuilder(
        context,
        HomeDatabase::class.java,
        DatabaseConstants.HOME_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideTvShowDetailDatabase(
        @ApplicationContext context: Context
    ): TvShowDetailDataBase = Room.databaseBuilder(
        context,
        TvShowDetailDataBase::class.java,
        DatabaseConstants.TV_SHOW_DATABASE_NAME
    ).build()

    @Provides fun provideSearchHistoryDao(db: SearchDatabase): SearchHistoryDao = db.searchHistoryDao()
    @Provides fun provideCountryDao(db: SearchDatabase): CountryDao = db.countryDao()
    @Provides fun provideGenresUserInteractionDao(db: SearchDatabase): GenresUserInteractionDao = db.genreUserInteractionDao()
    @Provides fun provideMovieCastDao(db: MovieDetailDataBase): MovieCastDao = db.castDao()
    @Provides fun provideMovieGalleryDao(db: MovieDetailDataBase): MovieGalleryDao = db.galleryDao()
    @Provides fun provideMovieDao(db: MovieDetailDataBase): MovieDao = db.movieDao()
    @Provides fun provideMovieReviewDao(db: MovieDetailDataBase): MovieReviewDao = db.reviewDao()
    @Provides fun provideMovieSimilarDao(db: MovieDetailDataBase): MovieSimilarDao = db.movieSimilarDao()

    @Provides fun provideTvShowCastDao(db: TvShowDetailDataBase): TvShowCastDao = db.castDao()
    @Provides fun provideTvShowGalleryDao(db: TvShowDetailDataBase): TvShowGalleryDao = db.galleryDao()
    @Provides fun provideTvShowDao(db: TvShowDetailDataBase): TvShowDao = db.tvShowDao()
    @Provides fun provideTvShowReviewDao(db: TvShowDetailDataBase): TvShowReviewDao = db.reviewDao()
    @Provides fun provideSeasonDao(db: TvShowDetailDataBase): SeasonDao = db.seasonDao()
    @Provides fun provideTvShowSimilarDao(db: TvShowDetailDataBase): TvShowSimilarDao = db.tvShowSimilarDao()

    @Provides fun provideHomeMediaDao(db: HomeDatabase): HomeMediaDao = db.mediaDao()
}

object DatabaseConstants {
    const val MOVIE_DATABASE_NAME = "movie_database"
    const val TV_SHOW_DATABASE_NAME = "tv_show_database"
    const val SEARCH_DATABASE_NAME = "search_db"
    const val HOME_DATABASE_NAME = "home_db"
}
