package com.paris_2.aflami.di


import android.content.Context
import androidx.room.Room
import com.datasource.local.media.MovieDetailDataBase
import com.datasource.local.media.TvShowDetailDataBase
import com.datasource.local.guessGame.GuessGameDatabase
import com.datasource.local.guessGame.dao.GamePointsDao
import com.datasource.local.media.HomeDatabase
import com.datasource.local.media.dao.CountryDao
import com.datasource.local.media.dao.GenresUserInteractionDao
import com.datasource.local.media.dao.ContinueWatchingDao
import com.datasource.local.media.dao.SearchHistoryDao
import com.datasource.local.media.SearchDatabase
import com.datasource.local.media.dao.HomeMediaDao
import com.datasource.local.media.dao.MovieDao
import com.datasource.local.media.dao.TvShowDao
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

    @Provides
    @Singleton
    fun provideGuessGameDatabase(
        @ApplicationContext context: Context
    ): GuessGameDatabase = Room.databaseBuilder(
        context,
        GuessGameDatabase::class.java,
        DatabaseConstants.GAME_DATABASE_NAME
    ).build()


    @Provides fun provideSearchHistoryDao(db: SearchDatabase): SearchHistoryDao = db.searchHistoryDao()
    @Provides fun provideCountryDao(db: SearchDatabase): CountryDao = db.countryDao()
    @Provides fun provideGenresUserInteractionDao(db: SearchDatabase): GenresUserInteractionDao = db.genreUserInteractionDao()

    @Provides fun provideMovieDao(db: MovieDetailDataBase): MovieDao = db.movieDao()

    @Provides fun provideTvShowDao(db: TvShowDetailDataBase): TvShowDao = db.tvShowDao()

    @Provides fun provideHomeMediaDao(db: HomeDatabase): ContinueWatchingDao = db.continueWatchingDao()

    @Provides fun provideHomeDao(db: HomeDatabase): HomeMediaDao = db.homeMediaDao()

    @Provides fun provideGameDao(db: GuessGameDatabase) : GamePointsDao = db.gamePointsDao()
}

object DatabaseConstants {
    const val MOVIE_DATABASE_NAME = "movie_database"
    const val TV_SHOW_DATABASE_NAME = "tv_show_database"
    const val SEARCH_DATABASE_NAME = "search_db"
    const val HOME_DATABASE_NAME = "home_db"
    const val GAME_DATABASE_NAME = "Transform Selected Code..."
}
