package com.paris_2.domain.movie.repository

import com.paris_2.domain.movie.model.MovieCast
import com.paris_2.domain.movie.model.MovieGallery
import com.paris_2.domain.movie.model.Movie
import com.paris_2.domain.movie.model.MovieSimilar
import com.paris_2.domain.movie.model.MovieProductionCompany
import com.paris_2.domain.movie.model.MovieReview

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun getMovieCast(movieId: Int): List<MovieCast>
    suspend fun getMovieRecommendations(movieId: Int,page: Int): List<MovieSimilar>
    suspend fun getMovieGallery(movieId: Int): MovieGallery
    suspend fun getCompanyProducts(movieId: Int): List<MovieProductionCompany>
    suspend fun getMovieReview(movieId: Int,page: Int): List<MovieReview>
    suspend fun addMovieToFavorite(movieId: Int)
}