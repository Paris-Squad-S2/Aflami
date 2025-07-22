package testUtils

import com.paris_2.domain.movie.model.MovieCast
import com.paris_2.domain.movie.model.MovieGallery
import com.paris_2.domain.movie.model.MovieGenre
import com.paris_2.domain.movie.model.MovieImage
import com.paris_2.domain.movie.model.Movie
import com.paris_2.domain.movie.model.MovieSimilar
import com.paris_2.domain.movie.model.MovieProductionCompany
import com.paris_2.domain.movie.model.MovieReview
import kotlinx.datetime.LocalDate

val fakeMovieGenres = listOf(
    MovieGenre(1, "Drama"),
    MovieGenre(2, "Comedy"),
    MovieGenre(3, "Action"),
    MovieGenre(4, "Crime"),
    MovieGenre(5, "Fantasy"),
    MovieGenre(6, "Horror"),
    MovieGenre(7, "Sci-Fi"),
    MovieGenre(8, "Romance"),
    MovieGenre(9, "Thriller"),
    MovieGenre(10, "Mystery")
)

val fakeMovieCasts = listOf(
    MovieCast(
        1,
        "Bryan Cranston",
        "https://image.tmdb.org/t/p/w500/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg"
    ),
    MovieCast(2, "Aaron Paul", "https://image.tmdb.org/t/p/w500/lOhc9ePVxj18cp6w1DgWJGBfULf.jpg"),
    MovieCast(3, "Anna Gunn", "https://image.tmdb.org/t/p/w500/4dJNBwYqEJ4AjYcQRNEyvvKWxWX.jpg"),
    MovieCast(
        4,
        "Peter Dinklage",
        "https://image.tmdb.org/t/p/w500/h2wI4qMGQU6xpHRJOk9lzFnI2dh.jpg"
    ),
    MovieCast(5, "Emilia Clarke", "https://image.tmdb.org/t/p/w500/r6wu5k0UvP1jbMFRKMrToDcEqr1.jpg"),
    MovieCast(6, "Kit Harington", "https://image.tmdb.org/t/p/w500/4JwYqO1fhTxGkNqJnLKVhLJUCIe.jpg"),
    MovieCast(
        7,
        "Millie Bobby Brown",
        "https://image.tmdb.org/t/p/w500/vPwPV0iNUjdq2qJJNcCiYn7v4YK.jpg"
    ),
    MovieCast(8, "Finn Wolfhard", "https://image.tmdb.org/t/p/w500/iGLJODOSh8sJGhHsT8lxPQjqwGI.jpg"),
    MovieCast(9, "David Harbour", "https://image.tmdb.org/t/p/w500/chPekukMF5SNnW6b22NbYPqAStr.jpg"),
    MovieCast(10, "Steve Carell", "https://image.tmdb.org/t/p/w500/4TjfQqVjJhLHbGhJONpL4VKg3rv.jpg"),
    MovieCast(
        11,
        "John Krasinski",
        "https://image.tmdb.org/t/p/w500/1KlahbCHZdkDGnUvVHsNckJ3gF0.jpg"
    ),
    MovieCast(
        12,
        "Jenna Fischer",
        "https://image.tmdb.org/t/p/w500/fzQfbP9xSGsR8iBmvAKG0wvVUwq.jpg"
    ),
    MovieCast(
        13,
        "Benedict Cumberbatch",
        "https://image.tmdb.org/t/p/w500/1KlahbCHZdkDGnUvVHsNckJ3gF0.jpg"
    ),
    MovieCast(
        14,
        "Martin Freeman",
        "https://image.tmdb.org/t/p/w500/qM8S16dRJy1dFpDSZtYdCXKUKtQ.jpg"
    ),
    MovieCast(15, "Rupert Graves", "https://image.tmdb.org/t/p/w500/9C5pKTb5BRQRDJm6eZCUPNzWYXb.jpg")
)

val fakeMovieReviews = listOf(
    MovieReview(
        id = "1",
        name = "Alex Thompson",
        createdAt = LocalDate(2023, 11, 15),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar1.jpg",
        username = "tvaddict99",
        rating = 9.5,
        description = "An absolute masterpiece! The character development and storytelling are top-notch. Breaking Bad is a must-watch for any TV fan."
    ),
    MovieReview(
        id = "2",
        name = "Maria Rodriguez",
        createdAt = LocalDate(2023, 10, 22),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar2.jpg",
        username = "bingewatcher",
        rating = 8.8,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    ),
    MovieReview(
        id = "3",
        name = "James Wilson",
        createdAt = LocalDate(2023, 12, 1),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar3.jpg",
        username = "seriescritic",
        rating = 9.2,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    ),
    MovieReview(
        id = "4",
        name = "Lisa Chen",
        createdAt = LocalDate(2023, 11, 8),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar4.jpg",
        username = "dramalover",
        rating = 8.9,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    ),
    MovieReview(
        id = "5",
        name = "Robert Brown",
        createdAt = LocalDate(2023, 10, 30),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar5.jpg",
        username = "showreviewer",
        rating = 9.0,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    )
)

val fakeProductionCompanies = listOf(
    MovieProductionCompany(1, "https://image.tmdb.org/t/p/w500/hbo.png", "HBO", "US"),
    MovieProductionCompany(2, "https://image.tmdb.org/t/p/w500/netflix.png", "Netflix", "US"),
    MovieProductionCompany(3, "https://image.tmdb.org/t/p/w500/amc.png", "AMC", "US"),
    MovieProductionCompany(4, "https://image.tmdb.org/t/p/w500/bbc.png", "BBC", "GB"),
    MovieProductionCompany(5, "https://image.tmdb.org/t/p/w500/disney.png", "Disney+", "US"),
    MovieProductionCompany(6, "https://image.tmdb.org/t/p/w500/amazon.png", "Amazon Prime", "US")
)

val fakeMovieImages = listOf(
    MovieImage(1, "https://image.tmdb.org/t/p/w500/tvbackdrop1.jpg"),
    MovieImage(2, "https://image.tmdb.org/t/p/w500/tvbackdrop2.jpg"),
    MovieImage(3, "https://image.tmdb.org/t/p/w500/tvbackdrop3.jpg"),
    MovieImage(4, "https://image.tmdb.org/t/p/w500/tvbackdrop4.jpg"),
    MovieImage(5, "https://image.tmdb.org/t/p/w500/tvposter1.jpg"),
    MovieImage(6, "https://image.tmdb.org/t/p/w500/tvposter2.jpg"),
    MovieImage(7, "https://image.tmdb.org/t/p/w500/tvstill1.jpg"),
    MovieImage(8, "https://image.tmdb.org/t/p/w500/tvstill2.jpg"),
    MovieImage(9, "https://image.tmdb.org/t/p/w500/tvstill3.jpg"),
    MovieImage(10, "https://image.tmdb.org/t/p/w500/tvstill4.jpg")
)

val fakeMovieGallery = MovieGallery(movieImages = fakeMovieImages)

val fakeMovie = Movie(
    id = 3,
    title = "Pulp Fiction",
    voteAverage = 8.9,
    description = "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
    posterPath = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
    movieGenres = listOf(fakeMovieGenres[1], fakeMovieGenres[6]),
    releaseDate = "1994-10-14",
    runtime = 154,
    country = "US",
    productionCompanies = listOf(fakeProductionCompanies[3])
)
val fakeMovies = listOf(
    Movie(
        id = 1,
        title = "The Dark Knight",
        voteAverage = 9.0,
        description = "Batman raises the stakes in his war on crime with the help of Lt. Jim Gordon and District Attorney Harvey Dent.",
        posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        movieGenres = listOf(fakeMovieGenres[0], fakeMovieGenres[1], fakeMovieGenres[6]),
        releaseDate = "2008-07-18",
        runtime = 152,
        country = "US",
        productionCompanies = listOf(fakeProductionCompanies[0])
    ),
    Movie(
        id = 2,
        title = "Inception",
        voteAverage = 8.8,
        description = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea.",
        posterPath = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        movieGenres = listOf(fakeMovieGenres[0], fakeMovieGenres[4], fakeMovieGenres[6]),
        releaseDate = "2010-07-16",
        runtime = 148,
        country = "GB",
        productionCompanies = listOf(fakeProductionCompanies[0])
    ),
    Movie(
        id = 3,
        title = "Pulp Fiction",
        voteAverage = 8.9,
        description = "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
        posterPath = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
        movieGenres = listOf(fakeMovieGenres[1], fakeMovieGenres[6]),
        releaseDate = "1994-10-14",
        runtime = 154,
        country = "US",
        productionCompanies = listOf(fakeProductionCompanies[3])
    )
)

val fakeMovieSimilar = listOf(
    MovieSimilar(
        id = 1,
        title = "The Dark Knight",
        voteAverage = 9.0,
        posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        releaseDate = "2008-07-18",
    ),
    MovieSimilar(
        id = 2,
        title = "Inception",
        voteAverage = 8.8,
        posterPath = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        releaseDate = "2010-07-16",
    ),
    MovieSimilar(
        id = 3,
        title = "Pulp Fiction",
        voteAverage = 8.9,
        posterPath = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
        releaseDate = "1994-10-14",
    )
)