package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.ReviewEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: MovieEntity)

    @Query("SELECT * FROM movies_table WHERE id = :movieId AND language = :language")
    suspend fun getMovieById(movieId: Int,language: String): MovieEntity?

    @Query("DELETE FROM movies_table WHERE id = :movieId AND language = :language")
    suspend fun clearMovieDetailsById(movieId: Int, language: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieCast(casts: List<CastEntity>)

    @Query("SELECT * FROM cast_table WHERE movieId = :movieId AND language = :language")
    suspend fun getCastByMovieId(movieId: Int,language: String): List<CastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieGallery(gallery: GalleryEntity)

    @Query("SELECT * FROM gallery_table WHERE movieId = :movieId")
    suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieReviews(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM reviews_table WHERE movieId = :movieId AND language = :language")
    suspend fun getReviewsByMovieId(movieId: Int,language: String): List<ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSimilarMovies(movies: List<MovieSimilarEntity>)

    @Query("SELECT * FROM movies_similar_table WHERE movieId = :movieId AND page = :page AND language = :language")
    suspend fun getSimilarMovies(movieId: Int,page: Int, language: String): List<MovieSimilarEntity>
}