package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity
import com.repository.media.entity.MediaEntity
import com.repository.movie.models.local.MovieCastEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieGalleryEntity
import com.repository.movie.models.local.MovieReviewEntity
import com.repository.movie.models.local.MovieSimilarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMediaContinueWatching(media: MediaEntity)

    @Query("SELECT * FROM media_table")
    fun getMediaContinueWatching(): Flow<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHomeMedia(media: List<HomeMediaEntity>)

    @Query("SELECT * FROM home_media_table WHERE category = :category AND language = :language")
    suspend fun getHomeMediaByCategory(category: Category, language: String): List<HomeMediaEntity>

    @Query("DELETE FROM home_media_table WHERE category = :category")
    suspend fun clearHomeMediaByCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addMovies(movies: MovieEntity)

    @Query("SELECT * FROM movie_table WHERE id = :movieId AND language = :language")
    suspend fun getMovieById(movieId: Int,language: String): MovieEntity?

    @Query("DELETE FROM movie_table WHERE id = :movieId AND language = :language")
    suspend fun clearMovieDetailsById(movieId: Int, language: String)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addMovieCast(casts: List<MovieCastEntity>)

    @Query("SELECT * FROM movie_cast_table WHERE movieId = :movieId AND language = :language")
    suspend fun getCastByMovieId(movieId: Int,language: String): List<MovieCastEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addMovieGallery(gallery: MovieGalleryEntity)

    @Query("SELECT * FROM movie_gallery_table WHERE movieId = :movieId")
    suspend fun getGalleryByMovieId(movieId: Int): MovieGalleryEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addMovieReviews(reviews: List<MovieReviewEntity>)

    @Query("SELECT * FROM movie_reviews_table WHERE movieId = :movieId AND language = :language")
    suspend fun getReviewsByMovieId(movieId: Int,language: String): List<MovieReviewEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addSimilarMovies(movies: List<MovieSimilarEntity>)

    @Query("SELECT * FROM movie_similar_table WHERE movieId = :movieId AND page = :page AND language = :language")
    suspend fun getSimilarMovies(movieId: Int,page: Int, language: String): List<MovieSimilarEntity>
}