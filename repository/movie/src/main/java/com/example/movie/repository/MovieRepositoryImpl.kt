package com.example.movie.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository

class MovieRepositoryImpl(
    // inject here local and remote data source dependency
): MovieRepository {
    override suspend fun getMovieDetails(movieId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieRecommendations(movieId: Int): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieGallery(movieId: Int): Gallery {
        TODO("Not yet implemented")
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieReview(movieId: Int): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }
}