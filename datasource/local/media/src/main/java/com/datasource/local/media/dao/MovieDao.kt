package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.models.local.moive.MovieCastEntity
import com.repository.media.models.local.moive.MovieGalleryEntity
import com.repository.media.models.local.moive.MovieEntity
import com.repository.media.models.local.moive.MovieSimilarEntity
import com.repository.media.models.local.moive.MovieReviewEntity

@Dao
interface MovieDao {
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