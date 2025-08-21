package com.paris.aflami.di


import android.content.Context
import androidx.room.Room
import com.datasource.local.guessGame.GuessGameDatabase
import com.datasource.local.guessGame.dao.GamePointsDao
import com.datasource.local.media.MediaDatabase
import com.datasource.local.media.dao.CountryDao
import com.datasource.local.media.dao.GenresUserInteractionDao
import com.datasource.local.media.dao.SearchHistoryDao
import com.datasource.local.media.dao.MediaDao
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
    fun provideMediaDatabase(
        @ApplicationContext context: Context
    ): MediaDatabase = Room.databaseBuilder(
        context,
        MediaDatabase::class.java,
        DatabaseConstants.MEDIA_DATABASE_NAME
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


    @Provides fun provideSearchHistoryDao(db: MediaDatabase): SearchHistoryDao = db.searchHistoryDao()
    @Provides fun provideCountryDao(db: MediaDatabase): CountryDao = db.countryDao()
    @Provides fun provideGenresUserInteractionDao(db: MediaDatabase): GenresUserInteractionDao = db.genreUserInteractionDao()
    @Provides fun provideTvShowDao(db: MediaDatabase): TvShowDao = db.tvShowDao()
    @Provides fun provideContinueWatchingDao(db: MediaDatabase): MediaDao = db.mediaDao()
    @Provides fun provideMovieDao(db: MediaDatabase): MovieDao = db.movieDao()
    @Provides fun provideGameDao(db: GuessGameDatabase) : GamePointsDao = db.gamePointsDao()

}

object DatabaseConstants {
    const val MEDIA_DATABASE_NAME = "media_db"
    const val GAME_DATABASE_NAME = "Transform Selected Code..."
}
