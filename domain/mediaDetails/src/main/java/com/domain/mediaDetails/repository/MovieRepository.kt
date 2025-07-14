package com.domain.mediaDetails.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Season

interface MovieRepository {
    fun getMovieDetails(movieId: Int): Movie
    fun getMovieCast(movieId: Int): List<Cast>
    fun getMovieRecommendations(movieId: Int): List<Movie>
    fun getMovieGallery(movieId: Int): Gallery
    fun getCompanyProducts(movieId: Int): List<ProductionCompany>
    fun getMovieReview(movieId: Int): List<Season>
}