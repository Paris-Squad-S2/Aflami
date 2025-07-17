package com.repository.movie.repository

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Country
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Genre
import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository
import kotlinx.datetime.LocalDate

class FakeMovieRepositoryImpl(
) : MovieRepository {

    private val favoriteMovies = mutableListOf<Movie>()
    private val fakeGenres = listOf(
        Genre(1, "Action"),
        Genre(2, "Drama"),
        Genre(3, "Comedy"),
        Genre(4, "Horror"),
        Genre(5, "Sci-Fi"),
        Genre(6, "Romance"),
        Genre(7, "Thriller"),
        Genre(8, "Adventure")
    )

    private val fakeCountries = listOf(
        Country("US", "United States", "الولايات المتحدة"),
        Country("GB", "United Kingdom", "المملكة المتحدة"),
        Country("FR", "France", "فرنسا"),
        Country("DE", "Germany", "ألمانيا"),
        Country("JP", "Japan", "اليابان")
    )

    private val fakeProductionCompanies = listOf(
        ProductionCompany(
            1,
            "https://image.tmdb.org/t/p/w500/warner_bros.png",
            "Warner Bros.",
            "US"
        ),
        ProductionCompany(
            2,
            "https://image.tmdb.org/t/p/w500/disney.png",
            "Walt Disney Pictures",
            "US"
        ),
        ProductionCompany(
            3,
            "https://image.tmdb.org/t/p/w500/universal.png",
            "Universal Pictures",
            "US"
        ),
        ProductionCompany(
            4,
            "https://image.tmdb.org/t/p/w500/paramount.png",
            "Paramount Pictures",
            "US"
        ),
        ProductionCompany(5, "https://image.tmdb.org/t/p/w500/sony.png", "Sony Pictures", "US")
    )

    private val fakeMovie = Movie(
        id = 3,
        title = "Pulp Fiction",
        voteAverage = 8.9,
        description = "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
        posterPath = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
        genres = listOf(fakeGenres[1], fakeGenres[6]),
        releaseDate = "1994-10-14",
        runtime = 154,
        country = fakeCountries[0],
        productionCompanies = listOf(fakeProductionCompanies[3])
    )
    private val fakeMovies = listOf(
        Movie(
            id = 1,
            title = "The Dark Knight",
            voteAverage = 9.0,
            description = "Batman raises the stakes in his war on crime with the help of Lt. Jim Gordon and District Attorney Harvey Dent.",
            posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            genres = listOf(fakeGenres[0], fakeGenres[1], fakeGenres[6]),
            releaseDate = "2008-07-18",
            runtime = 152,
            country = fakeCountries[0],
            productionCompanies = listOf(fakeProductionCompanies[0])
        ),
        Movie(
            id = 2,
            title = "Inception",
            voteAverage = 8.8,
            description = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea.",
            posterPath = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
            genres = listOf(fakeGenres[0], fakeGenres[4], fakeGenres[6]),
            releaseDate = "2010-07-16",
            runtime = 148,
            country = fakeCountries[1],
            productionCompanies = listOf(fakeProductionCompanies[0])
        ),
        Movie(
            id = 3,
            title = "Pulp Fiction",
            voteAverage = 8.9,
            description = "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
            posterPath = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
            genres = listOf(fakeGenres[1], fakeGenres[6]),
            releaseDate = "1994-10-14",
            runtime = 154,
            country = fakeCountries[0],
            productionCompanies = listOf(fakeProductionCompanies[3])
        )
    )

    private val fakeCast = listOf(
        Cast(
            1,
            "Christian Bale",
            "https://image.tmdb.org/t/p/w500/3qx2QFUbG6t6IlzR0F9k3Z6Yhf7.jpg"
        ),
        Cast(2, "Heath Ledger", "https://image.tmdb.org/t/p/w500/5Y9HnYYa9jF4NunY9lSgJGjSe8E.jpg"),
        Cast(3, "Aaron Eckhart", "https://image.tmdb.org/t/p/w500/kJm2nqMRmjZMhXQFcRtLMTbLLxJ.jpg"),
        Cast(4, "Michael Caine", "https://image.tmdb.org/t/p/w500/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg"),
        Cast(5, "Gary Oldman", "https://image.tmdb.org/t/p/w500/2v9FVVBUrrkW2m3QOcYkuhq9A6o.jpg"),
        Cast(
            6,
            "Leonardo DiCaprio",
            "https://image.tmdb.org/t/p/w500/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg"
        ),
        Cast(
            7,
            "Marion Cotillard",
            "https://image.tmdb.org/t/p/w500/1A8dE2xOHYyXWEKjgBJQbN1nAA.jpg"
        ),
        Cast(8, "John Travolta", "https://image.tmdb.org/t/p/w500/nO4gUzYaEQgKfBbDNTlKgkFbOkS.jpg"),
        Cast(
            9,
            "Samuel L. Jackson",
            "https://image.tmdb.org/t/p/w500/AiAYAqyVpfQi4KMi5aWNU6wlhLB.jpg"
        ),
        Cast(10, "Uma Thurman", "https://image.tmdb.org/t/p/w500/6JYiYvPqVfR5nqeYWFUlZdUqxD.jpg")
    )

    private val fakeReviews = listOf(
        Review(
            id = "1",
            name = "John Smith",
            createdAt = LocalDate(2023, 10, 15),
            avatarUrl = "https://image.tmdb.org/t/p/w500/avatar1.jpg",
            username = "moviecritic123",
            rating = 9.0
        ),
        Review(
            id = "2",
            name = "Sarah Johnson",
            createdAt = LocalDate(2023, 11, 2),
            avatarUrl = "https://image.tmdb.org/t/p/w500/avatar2.jpg",
            username = "filmfan456",
            rating = 8.5
        ),
        Review(
            id = "3",
            name = "Mike Davis",
            createdAt = LocalDate(2023, 9, 28),
            avatarUrl = "https://image.tmdb.org/t/p/w500/avatar3.jpg",
            username = "cinephile789",
            rating = 8.0
        ),
        Review(
            id = "4",
            name = "Emma Wilson",
            createdAt = LocalDate(2023, 12, 5),
            avatarUrl = "https://image.tmdb.org/t/p/w500/avatar4.jpg",
            username = "moviebuff101",
            rating = 9.5
        )
    )

    private val fakeImages = listOf(
        Image(1, "https://image.tmdb.org/t/p/w500/backdrop1.jpg"),
        Image(2, "https://image.tmdb.org/t/p/w500/backdrop2.jpg"),
        Image(3, "https://image.tmdb.org/t/p/w500/backdrop3.jpg"),
        Image(4, "https://image.tmdb.org/t/p/w500/backdrop4.jpg"),
        Image(5, "https://image.tmdb.org/t/p/w500/poster1.jpg"),
        Image(6, "https://image.tmdb.org/t/p/w500/poster2.jpg"),
        Image(7, "https://image.tmdb.org/t/p/w500/still1.jpg"),
        Image(8, "https://image.tmdb.org/t/p/w500/still2.jpg")
    )

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return fakeMovies.find { it.id == movieId } ?: fakeMovie
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return fakeCast
    }

    override suspend fun getMovieRecommendations(movieId: Int,page: Int): List<Movie> {
        return fakeMovies.filter { it.id != movieId }
    }

    override suspend fun getMovieGallery(movieId: Int): Gallery {
        return Gallery(images = fakeImages)
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        return fakeMovies.find { it.id == movieId }?.productionCompanies ?: emptyList()
    }

    override suspend fun getMovieReview(movieId: Int,page: Int): List<Review> {
        return fakeReviews
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        val currentMovie = favoriteMovies.find { it.id == movieId }
        if(currentMovie != null){
            favoriteMovies.add(currentMovie)
        }
    }

}