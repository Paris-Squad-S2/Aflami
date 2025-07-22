package testUtils

import com.paris_2.domain.tvshow.model.TvShowCast
import com.paris_2.domain.tvshow.model.Episode
import com.paris_2.domain.tvshow.model.TvShowGallery
import com.paris_2.domain.tvshow.model.TvShowGenre
import com.paris_2.domain.tvshow.model.TvShowImage
import com.paris_2.domain.tvshow.model.TvShowProductionCompany
import com.paris_2.domain.tvshow.model.TvShowReview
import com.paris_2.domain.tvshow.model.Season
import com.paris_2.domain.tvshow.model.TvShow
import com.paris_2.domain.tvshow.model.TvShowSimilar
import kotlinx.datetime.LocalDate

val fakeTvShowGenres = listOf(
    TvShowGenre(1, "Drama"),
    TvShowGenre(2, "Comedy"),
    TvShowGenre(3, "Action"),
    TvShowGenre(4, "Crime"),
    TvShowGenre(5, "Fantasy"),
    TvShowGenre(6, "Horror"),
    TvShowGenre(7, "Sci-Fi"),
    TvShowGenre(8, "Romance"),
    TvShowGenre(9, "Thriller"),
    TvShowGenre(10, "Mystery")
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

val fakeTvShowCasts = listOf(
    TvShowCast(
        1,
        "Bryan Cranston",
        "https://image.tmdb.org/t/p/w500/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg"
    ),
    TvShowCast(2, "Aaron Paul", "https://image.tmdb.org/t/p/w500/lOhc9ePVxj18cp6w1DgWJGBfULf.jpg"),
    TvShowCast(3, "Anna Gunn", "https://image.tmdb.org/t/p/w500/4dJNBwYqEJ4AjYcQRNEyvvKWxWX.jpg"),
    TvShowCast(
        4,
        "Peter Dinklage",
        "https://image.tmdb.org/t/p/w500/h2wI4qMGQU6xpHRJOk9lzFnI2dh.jpg"
    ),
    TvShowCast(5, "Emilia Clarke", "https://image.tmdb.org/t/p/w500/r6wu5k0UvP1jbMFRKMrToDcEqr1.jpg"),
    TvShowCast(6, "Kit Harington", "https://image.tmdb.org/t/p/w500/4JwYqO1fhTxGkNqJnLKVhLJUCIe.jpg"),
    TvShowCast(
        7,
        "Millie Bobby Brown",
        "https://image.tmdb.org/t/p/w500/vPwPV0iNUjdq2qJJNcCiYn7v4YK.jpg"
    ),
    TvShowCast(8, "Finn Wolfhard", "https://image.tmdb.org/t/p/w500/iGLJODOSh8sJGhHsT8lxPQjqwGI.jpg"),
    TvShowCast(9, "David Harbour", "https://image.tmdb.org/t/p/w500/chPekukMF5SNnW6b22NbYPqAStr.jpg"),
    TvShowCast(10, "Steve Carell", "https://image.tmdb.org/t/p/w500/4TjfQqVjJhLHbGhJONpL4VKg3rv.jpg"),
    TvShowCast(
        11,
        "John Krasinski",
        "https://image.tmdb.org/t/p/w500/1KlahbCHZdkDGnUvVHsNckJ3gF0.jpg"
    ),
    TvShowCast(
        12,
        "Jenna Fischer",
        "https://image.tmdb.org/t/p/w500/fzQfbP9xSGsR8iBmvAKG0wvVUwq.jpg"
    ),
    TvShowCast(
        13,
        "Benedict Cumberbatch",
        "https://image.tmdb.org/t/p/w500/1KlahbCHZdkDGnUvVHsNckJ3gF0.jpg"
    ),
    TvShowCast(
        14,
        "Martin Freeman",
        "https://image.tmdb.org/t/p/w500/qM8S16dRJy1dFpDSZtYdCXKUKtQ.jpg"
    ),
    TvShowCast(15, "Rupert Graves", "https://image.tmdb.org/t/p/w500/9C5pKTb5BRQRDJm6eZCUPNzWYXb.jpg")
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
        tvShowEpisodes = fakeEpisodes.take(5),
        seasonNumber = 1,
        episodeCount = 10,
    ),
    Season(
        id = 2,
        name = "Season 1",
        tvShowEpisodes = fakeEpisodes.take(5),
        seasonNumber = 1,
        episodeCount = 10,
    ),
    Season(
        id = 3,
        name = "Season 1",
        tvShowEpisodes = fakeEpisodes.take(5),
        seasonNumber = 1,
        episodeCount = 10,
    )
)

val fakeTvShowReviews = listOf(
    TvShowReview(
        id = "1",
        name = "Alex Thompson",
        createdAt = LocalDate(2023, 11, 15),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar1.jpg",
        username = "tvaddict99",
        rating = 9.5,
        description = "An absolute masterpiece! The character development and storytelling are top-notch. Breaking Bad is a must-watch for any TV fan."
    ),
    TvShowReview(
        id = "2",
        name = "Maria Rodriguez",
        createdAt = LocalDate(2023, 10, 22),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar2.jpg",
        username = "bingewatcher",
        rating = 8.8,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    ),
    TvShowReview(
        id = "3",
        name = "James Wilson",
        createdAt = LocalDate(2023, 12, 1),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar3.jpg",
        username = "seriescritic",
        rating = 9.2,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    ),
    TvShowReview(
        id = "4",
        name = "Lisa Chen",
        createdAt = LocalDate(2023, 11, 8),
        avatarUrl = "https://image.tmdb.org/t/p/w500/avatar4.jpg",
        username = "dramalover",
        rating = 8.9,
        description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White."
    ),
    TvShowReview(
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
    TvShowProductionCompany(1, "https://image.tmdb.org/t/p/w500/hbo.png", "HBO", "US"),
    TvShowProductionCompany(2, "https://image.tmdb.org/t/p/w500/netflix.png", "Netflix", "US"),
    TvShowProductionCompany(3, "https://image.tmdb.org/t/p/w500/amc.png", "AMC", "US"),
    TvShowProductionCompany(4, "https://image.tmdb.org/t/p/w500/bbc.png", "BBC", "GB"),
    TvShowProductionCompany(5, "https://image.tmdb.org/t/p/w500/disney.png", "Disney+", "US"),
    TvShowProductionCompany(6, "https://image.tmdb.org/t/p/w500/amazon.png", "Amazon Prime", "US")
)

val fakeTvShow = TvShow(
    id = 1,
    title = "Breaking Bad",
    voteAverage = 9.5,
    description = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.",
    posterPath = "https://image.tmdb.org/t/p/w500/3xnWaLQjelJDDF7LT1WBo6f4BRe.jpg",
    tvShowGenres = listOf(fakeTvShowGenres[0], fakeTvShowGenres[3], fakeTvShowGenres[8]),
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
        tvShowGenres = listOf(fakeTvShowGenres[0], fakeTvShowGenres[3], fakeTvShowGenres[8]),
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
        tvShowGenres = listOf(fakeTvShowGenres[0], fakeTvShowGenres[4], fakeTvShowGenres[2]),
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
        tvShowGenres = listOf(fakeTvShowGenres[0], fakeTvShowGenres[5], fakeTvShowGenres[6]),
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
        tvShowGenres = listOf(fakeTvShowGenres[1]),
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
        tvShowGenres = listOf(fakeTvShowGenres[0], fakeTvShowGenres[9], fakeTvShowGenres[3]),
        releaseDate = "2010-07-25",
        runtime = 90,
        country = "GB",
        productionCompanies = listOf(fakeProductionCompanies[3]),
        seasons = listOf(fakeSeasons[1])
    )
)


val fakeTvShowImages = listOf(
    TvShowImage(1, "https://image.tmdb.org/t/p/w500/tvbackdrop1.jpg"),
    TvShowImage(2, "https://image.tmdb.org/t/p/w500/tvbackdrop2.jpg"),
    TvShowImage(3, "https://image.tmdb.org/t/p/w500/tvbackdrop3.jpg"),
    TvShowImage(4, "https://image.tmdb.org/t/p/w500/tvbackdrop4.jpg"),
    TvShowImage(5, "https://image.tmdb.org/t/p/w500/tvposter1.jpg"),
    TvShowImage(6, "https://image.tmdb.org/t/p/w500/tvposter2.jpg"),
    TvShowImage(7, "https://image.tmdb.org/t/p/w500/tvstill1.jpg"),
    TvShowImage(8, "https://image.tmdb.org/t/p/w500/tvstill2.jpg"),
    TvShowImage(9, "https://image.tmdb.org/t/p/w500/tvstill3.jpg"),
    TvShowImage(10, "https://image.tmdb.org/t/p/w500/tvstill4.jpg")
)

val fakeTvShowGallery = TvShowGallery(tvShowImages = fakeTvShowImages)
