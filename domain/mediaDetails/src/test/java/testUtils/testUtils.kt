package testUtils

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Country
import com.domain.mediaDetails.model.Episode
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Genre
import com.domain.mediaDetails.model.Image
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.MovieSimilar
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowSimilar
import kotlinx.datetime.LocalDate

val fakeGenres = listOf(
    Genre(1, "Drama"),
    Genre(2, "Comedy"),
    Genre(3, "Action"),
    Genre(4, "Crime"),
    Genre(5, "Fantasy"),
    Genre(6, "Horror"),
    Genre(7, "Sci-Fi"),
    Genre(8, "Romance"),
    Genre(9, "Thriller"),
    Genre(10, "Mystery")
)


val fakeTvShowsSimilar = listOf(
    TvShowSimilar(
        id = 1,
        title = "Breaking Bad",
        voteAverage = 9.5,
        posterPath = "https://image.tmdb.org/t/p/w500/3xnWaLQjelJDDF7LT1WBo6f4BRe.jpg",
        releaseDate = "2008-01-20",

        ),
    TvShowSimilar(
        id = 2,
        title = "Game of Thrones",
        voteAverage = 9.2,
        posterPath = "https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
        releaseDate = "2011-04-17",
    ),
    TvShowSimilar(
        id = 3,
        title = "Stranger Things",
        voteAverage = 8.7,
        posterPath = "https://image.tmdb.org/t/p/w500/49WJfeN0moxb9IPfGn8AIqMGskD.jpg",
        releaseDate = "2016-07-15",

        ),
    TvShowSimilar(
        id = 4,
        title = "The Office",
        voteAverage = 8.9,
        posterPath = "https://image.tmdb.org/t/p/w500/7DJKHzAi83PmGuHjrEfQ1S9y4LF.jpg",
        releaseDate = "2005-03-24",

        ),
    TvShowSimilar(
        id = 5,
        title = "Sherlock",
        voteAverage = 9.1,
        posterPath = "https://image.tmdb.org/t/p/w500/7WTsnHkbA0FaG6R9twfFde0I9hl.jpg",
        releaseDate = "2010-07-25",
    )
)

val fakeCast = listOf(
    Cast(
        1,
        "Bryan Cranston",
        "https://image.tmdb.org/t/p/w500/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg"
    ),
    Cast(2, "Aaron Paul", "https://image.tmdb.org/t/p/w500/lOhc9ePVxj18cp6w1DgWJGBfULf.jpg"),
    Cast(3, "Anna Gunn", "https://image.tmdb.org/t/p/w500/4dJNBwYqEJ4AjYcQRNEyvvKWxWX.jpg"),
    Cast(
        4,
        "Peter Dinklage",
        "https://image.tmdb.org/t/p/w500/h2wI4qMGQU6xpHRJOk9lzFnI2dh.jpg"
    ),
    Cast(5, "Emilia Clarke", "https://image.tmdb.org/t/p/w500/r6wu5k0UvP1jbMFRKMrToDcEqr1.jpg"),
    Cast(6, "Kit Harington", "https://image.tmdb.org/t/p/w500/4JwYqO1fhTxGkNqJnLKVhLJUCIe.jpg"),
    Cast(
        7,
        "Millie Bobby Brown",
        "https://image.tmdb.org/t/p/w500/vPwPV0iNUjdq2qJJNcCiYn7v4YK.jpg"
    ),
    Cast(8, "Finn Wolfhard", "https://image.tmdb.org/t/p/w500/iGLJODOSh8sJGhHsT8lxPQjqwGI.jpg"),
    Cast(9, "David Harbour", "https://image.tmdb.org/t/p/w500/chPekukMF5SNnW6b22NbYPqAStr.jpg"),
    Cast(10, "Steve Carell", "https://image.tmdb.org/t/p/w500/4TjfQqVjJhLHbGhJONpL4VKg3rv.jpg"),
    Cast(
        11,
        "John Krasinski",
        "https://image.tmdb.org/t/p/w500/1KlahbCHZdkDGnUvVHsNckJ3gF0.jpg"
    ),
    Cast(
        12,
        "Jenna Fischer",
        "https://image.tmdb.org/t/p/w500/fzQfbP9xSGsR8iBmvAKG0wvVUwq.jpg"
    ),
    Cast(
        13,
        "Benedict Cumberbatch",
        "https://image.tmdb.org/t/p/w500/1KlahbCHZdkDGnUvVHsNckJ3gF0.jpg"
    ),
    Cast(
        14,
        "Martin Freeman",
        "https://image.tmdb.org/t/p/w500/qM8S16dRJy1dFpDSZtYdCXKUKtQ.jpg"
    ),
    Cast(15, "Rupert Graves", "https://image.tmdb.org/t/p/w500/9C5pKTb5BRQRDJm6eZCUPNzWYXb.jpg")
)

val fakeEpisodes = listOf(
    Episode(
        id = 1,
        episodeNumber = 1,
        posterUrl = "https://image.tmdb.org/t/p/w500/episode1.jpg",
        voteAverage = 8.2,
        airDate = LocalDate(2008, 1, 20),
        runtime = 58,
        description = "Walter White, a struggling high school chemistry teacher, is diagnosed with lung cancer.",
        stillUrl = "https://image.tmdb.org/t/p/w500/still1.jpg"
    ),
    Episode(
        id = 2,
        episodeNumber = 2,
        posterUrl = "https://image.tmdb.org/t/p/w500/episode2.jpg",
        voteAverage = 8.5,
        airDate = LocalDate(2008, 1, 27),
        runtime = 47,
        description = "Walt and Jesse attempt to tie up loose ends. The desperate situation gets more complicated.",
        stillUrl = "https://image.tmdb.org/t/p/w500/still2.jpg"
    ),
    Episode(
        id = 3,
        episodeNumber = 3,
        posterUrl = "https://image.tmdb.org/t/p/w500/episode3.jpg",
        voteAverage = 8.7,
        airDate = LocalDate(2008, 2, 10),
        runtime = 48,
        description = "Walt and Jesse clean up after the bathtub incident before Walt's family returns home.",
        stillUrl = "https://image.tmdb.org/t/p/w500/still3.jpg"
    ),
    Episode(
        id = 4,
        episodeNumber = 4,
        posterUrl = "https://image.tmdb.org/t/p/w500/episode4.jpg",
        voteAverage = 8.9,
        airDate = LocalDate(2008, 2, 17),
        runtime = 47,
        description = "Walt attempts to reconnect with his family. Jesse struggles with his own family issues.",
        stillUrl = "https://image.tmdb.org/t/p/w500/still4.jpg"
    ),
    Episode(
        id = 5,
        episodeNumber = 5,
        posterUrl = "https://image.tmdb.org/t/p/w500/episode5.jpg",
        voteAverage = 9.1,
        airDate = LocalDate(2008, 2, 24),
        runtime = 47,
        description = "Walt rejects everyone who tries to help him with the cancer. Jesse tries to make amends.",
        stillUrl = "https://image.tmdb.org/t/p/w500/still5.jpg"
    )
)

val fakeSeasons:List<Season> = listOf(
    Season(
        id = 1,
        name = "Season 1",
        episodes = fakeEpisodes.take(5),
        seasonNumber = 1,
        episodeCount = 10,
    ),
    Season(
        id = 2,
        name = "Season 1",
        episodes = fakeEpisodes.take(5),
        seasonNumber = 1,
        episodeCount = 10,
    ),
    Season(
        id = 3,
        name = "Season 1",
        episodes = fakeEpisodes.take(5),
        seasonNumber = 1,
        episodeCount = 10,
    )
)

val fakeReviews = listOf(
    Review(
        id = "1",
        name = "Alex Thompson",
        createdAt = LocalDate(2023, 11, 15),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar1.jpg",
        username = "tvaddict99",
        rating = 9.5
    ),
    Review(
        id = "2",
        name = "Maria Rodriguez",
        createdAt = LocalDate(2023, 10, 22),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar2.jpg",
        username = "bingewatcher",
        rating = 8.8
    ),
    Review(
        id = "3",
        name = "James Wilson",
        createdAt = LocalDate(2023, 12, 1),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar3.jpg",
        username = "seriescritic",
        rating = 9.2
    ),
    Review(
        id = "4",
        name = "Lisa Chen",
        createdAt = LocalDate(2023, 11, 8),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar4.jpg",
        username = "dramalover",
        rating = 8.9
    ),
    Review(
        id = "5",
        name = "Robert Brown",
        createdAt = LocalDate(2023, 10, 30),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar5.jpg",
        username = "showreviewer",
        rating = 9.0
    )
)

val fakeProductionCompanies = listOf(
    ProductionCompany(1, "https://image.tmdb.org/t/p/w500/hbo.png", "HBO", "US"),
    ProductionCompany(2, "https://image.tmdb.org/t/p/w500/netflix.png", "Netflix", "US"),
    ProductionCompany(3, "https://image.tmdb.org/t/p/w500/amc.png", "AMC", "US"),
    ProductionCompany(4, "https://image.tmdb.org/t/p/w500/bbc.png", "BBC", "GB"),
    ProductionCompany(5, "https://image.tmdb.org/t/p/w500/disney.png", "Disney+", "US"),
    ProductionCompany(6, "https://image.tmdb.org/t/p/w500/amazon.png", "Amazon Prime", "US")
)

val fakeTvShow = TvShow(
    id = 1,
    title = "Breaking Bad",
    voteAverage = 9.5,
    description = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.",
    posterPath = "https://image.tmdb.org/t/p/w500/3xnWaLQjelJDDF7LT1WBo6f4BRe.jpg",
    genres = listOf(fakeGenres[0], fakeGenres[3], fakeGenres[8]),
    releaseDate = "2008-01-20",
    runtime = 47,
    country = "US",
    productionCompanies = listOf(fakeProductionCompanies[2]),
    seasons = fakeSeasons

)

val fakeTvShows = listOf(
    TvShow(
        id = 1,
        title = "Breaking Bad",
        voteAverage = 9.5,
        description = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.",
        posterPath = "https://image.tmdb.org/t/p/w500/3xnWaLQjelJDDF7LT1WBo6f4BRe.jpg",
        genres = listOf(fakeGenres[0], fakeGenres[3], fakeGenres[8]),
        releaseDate = "2008-01-20",
        runtime = 47,
        country = "US",
        productionCompanies = listOf(fakeProductionCompanies[2]),
        seasons = listOf(fakeSeasons[0])
    ),
    TvShow(
        id = 2,
        title = "Game of Thrones",
        voteAverage = 9.2,
        description = "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.",
        posterPath = "https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
        genres = listOf(fakeGenres[0], fakeGenres[4], fakeGenres[2]),
        releaseDate = "2011-04-17",
        runtime = 57,
        country = "US",
        productionCompanies = listOf(fakeProductionCompanies[0]),
        seasons = listOf(fakeSeasons[0])
    ),
    TvShow(
        id = 3,
        title = "Stranger Things",
        voteAverage = 8.7,
        description = "When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces.",
        posterPath = "https://image.tmdb.org/t/p/w500/49WJfeN0moxb9IPfGn8AIqMGskD.jpg",
        genres = listOf(fakeGenres[0], fakeGenres[5], fakeGenres[6]),
        releaseDate = "2016-07-15",
        runtime = 51,
        country = "US",
        productionCompanies = listOf(fakeProductionCompanies[1]),
        seasons = listOf(fakeSeasons[0])
    ),
    TvShow(
        id = 4,
        title = "The Office",
        voteAverage = 8.9,
        description = "A mockumentary on a group of typical office workers, where the workday consists of ego clashes, inappropriate behavior, and tedium.",
        posterPath = "https://image.tmdb.org/t/p/w500/7DJKHzAi83PmGuHjrEfQ1S9y4LF.jpg",
        genres = listOf(fakeGenres[1]),
        releaseDate = "2005-03-24",
        runtime = 22,
        country = "US",
        productionCompanies = listOf(fakeProductionCompanies[1]),
        seasons = listOf(fakeSeasons[0])
    ),
    TvShow(
        id = 5,
        title = "Sherlock",
        voteAverage = 9.1,
        description = "A modern update finds the famous sleuth and his doctor partner solving crime in 21st century London.",
        posterPath = "https://image.tmdb.org/t/p/w500/7WTsnHkbA0FaG6R9twfFde0I9hl.jpg",
        genres = listOf(fakeGenres[0], fakeGenres[9], fakeGenres[3]),
        releaseDate = "2010-07-25",
        runtime = 90,
        country = "GB",
        productionCompanies = listOf(fakeProductionCompanies[3]),
        seasons = listOf(fakeSeasons[1])
    )
)


val fakeImages = listOf(
    Image(1, "https://image.tmdb.org/t/p/w500/tvbackdrop1.jpg"),
    Image(2, "https://image.tmdb.org/t/p/w500/tvbackdrop2.jpg"),
    Image(3, "https://image.tmdb.org/t/p/w500/tvbackdrop3.jpg"),
    Image(4, "https://image.tmdb.org/t/p/w500/tvbackdrop4.jpg"),
    Image(5, "https://image.tmdb.org/t/p/w500/tvposter1.jpg"),
    Image(6, "https://image.tmdb.org/t/p/w500/tvposter2.jpg"),
    Image(7, "https://image.tmdb.org/t/p/w500/tvstill1.jpg"),
    Image(8, "https://image.tmdb.org/t/p/w500/tvstill2.jpg"),
    Image(9, "https://image.tmdb.org/t/p/w500/tvstill3.jpg"),
    Image(10, "https://image.tmdb.org/t/p/w500/tvstill4.jpg")
)

val fakeGallery = Gallery(images = fakeImages)

val fakeMovie = Movie(
    id = 3,
    title = "Pulp Fiction",
    voteAverage = 8.9,
    description = "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
    posterPath = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
    genres = listOf(fakeGenres[1], fakeGenres[6]),
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
        genres = listOf(fakeGenres[0], fakeGenres[1], fakeGenres[6]),
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
        genres = listOf(fakeGenres[0], fakeGenres[4], fakeGenres[6]),
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
        genres = listOf(fakeGenres[1], fakeGenres[6]),
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