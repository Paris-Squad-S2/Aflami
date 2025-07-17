package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

class MovieDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
) : MovieDetailsScreenInteractionListener, BaseViewModel<MovieDetailsScreenState>(
    MovieDetailsScreenState(
        movieDetailsUiState = MovieDetailsUiState(
            movie = MovieUi(
                id = 0,
                posterUrl = "",
                rating = "",
                title = "",
                genres = emptyList(),
                releaseDate = "",
                runtime = "",
                country = "",
                description = "",
                productionCompanies = emptyList()
            ),
            cast = emptyList(),
            reviews = emptyList(),
            gallery = emptyList()
        ),
        isLoading = false,
        errorMessage = null
    )
) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
        emitState(
            MovieDetailsScreenState(
                movieDetailsUiState = MovieDetailsUiState(
                    movie = MovieUi(
                        id = 123,
                        posterUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                        rating = "8.2",
                        title = "Inception (ID: $mediaId)",
                        genres = listOf("Action", "Science Fiction", "Thriller"),
                        releaseDate = "2010-07-16",
                        runtime = "2h 28m",
                        country = "USA",
                        description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                        productionCompanies = listOf(
                            ProductionCompanyUi(
                                logoUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                                name = "Legendary Pictures",
                                originCountry = "USA"
                            ),
                            ProductionCompanyUi(
                                logoUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                                name = "Syncopy",
                                originCountry = "UK"
                            )
                        )
                    ),
                    cast = listOf(
                        CastUi(
                            name = "Leonardo DiCaprio",
                            imageUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg"
                        ),
                        CastUi(
                            name = "Joseph Gordon-Levitt",
                            imageUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg"
                        )
                    ),
                    reviews = listOf(
                        ReviewUi(
                            avatarUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                            username = "movie_buff_91",
                            name = "Amazing Movie!",
                            rating = 9.0,
                            createdAt = "2023-04-12"
                        ),
                        ReviewUi(
                            avatarUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                            username = "cinema_fanatic",
                            name = "Mind-blowing!",
                            rating = 8.5,
                            createdAt = "2023-05-20"
                        )
                    ),
                    gallery = listOf(
                        "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                        "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg"
                    )
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    override fun onNavigateBack() {
        navigateUp()
    }

    override fun onFavouriteClick(title: String) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onAddToListClick(title: String) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onShowAllCastClick(movieId: Int) {
        navigate(MediaDetailsDestinations.MovieCastScreen(movieId = movieId))
    }
}