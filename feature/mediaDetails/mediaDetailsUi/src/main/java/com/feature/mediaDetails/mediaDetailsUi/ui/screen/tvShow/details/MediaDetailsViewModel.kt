package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi
import com.paris_2.aflami.appnavigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

class TvShowDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator = getKoin().get(),
) : TvShowScreenInteractionListener, BaseViewModel<TvShowDetailsScreenState>(
    TvShowDetailsScreenState(
        TvShowDetailsUiState(
            tvShowUi = TvShowUi(
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
            gallery = emptyList(),
            seasons = emptyList()
        ),
        isLoading = false,
        errorMessage = null
    )
) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId

        emitState(
            TvShowDetailsScreenState(
                tvShowDetailsUiState = TvShowDetailsUiState(
                    tvShowUi = TvShowUi(
                        id = 123,
                        posterUrl = "https://via.placeholder.com/300x450",
                        rating = "8.7",
                        title = "Mock Show $mediaId",
                        genres = listOf("Drama", "Sci-Fi"),
                        releaseDate = "2022-09-10",
                        runtime = "60",
                        country = "USA",
                        description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                        productionCompanies = listOf(
                            ProductionCompanyUi(
                                logoUrl = "https://via.placeholder.com/100",
                                name = "Mock Studio",
                                originCountry = "US"
                            )
                        )
                    ),
                    cast = listOf(
                        CastUi(name = "Mock Actor 1", imageUrl = "https://via.placeholder.com/150"),
                        CastUi(name = "Mock Actor 2", imageUrl = "https://via.placeholder.com/150")
                    ),
                    reviews = listOf(
                        ReviewUi(
                            avatarUrl = "https://via.placeholder.com/50",
                            username = "mockuser1",
                            name = "Mock Reviewer",
                            rating = 4.0,
                            createdAt = "2024-11-01"
                        )
                    ),
                    gallery = listOf(
                        "https://via.placeholder.com/300x200",
                        "https://via.placeholder.com/300x200"
                    ),
                    seasons = listOf(
                        SeasonUi(
                            id = "s1",
                            name = "Season 1",
                            episodes = listOf(
                                EpisodeUi(
                                    episodeNumber = 1,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 7.9,
                                    airDate = "2022-09-10",
                                    runtime = "58",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                ),
                                EpisodeUi(
                                    episodeNumber = 2,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 8.2,
                                    airDate = "2022-09-17",
                                    runtime = "61",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                )
                            )
                        ),
                        SeasonUi(
                            id = "s2",
                            name = "Season 2",
                            episodes = listOf(
                                EpisodeUi(
                                    episodeNumber = 1,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 7.9,
                                    airDate = "2022-09-10",
                                    runtime = "58",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                ),
                                EpisodeUi(
                                    episodeNumber = 2,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 8.2,
                                    airDate = "2022-09-17",
                                    runtime = "61",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                )
                            )
                        ),
                        SeasonUi(
                            id = "s3",
                            name = "Season 3",
                            episodes = listOf(
                                EpisodeUi(
                                    episodeNumber = 1,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 7.9,
                                    airDate = "2022-09-10",
                                    runtime = "58",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                ),
                                EpisodeUi(
                                    episodeNumber = 2,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 8.2,
                                    airDate = "2022-09-17",
                                    runtime = "61",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                )
                            )
                        ),
                        SeasonUi(
                            id = "s1",
                            name = "Season 1",
                            episodes = listOf(
                                EpisodeUi(
                                    episodeNumber = 1,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 7.9,
                                    airDate = "2022-09-10",
                                    runtime = "58",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                ),
                                EpisodeUi(
                                    episodeNumber = 2,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 8.2,
                                    airDate = "2022-09-17",
                                    runtime = "61",
                                    description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison.",
                                    stillUrl = "https://via.placeholder.com/300"
                                )
                            )
                        )
                    )
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    override fun onNavigateBack() {
            viewModelScope.launch {
                appNavigator.navigateUp()
        }
    }

    override fun onFavouriteClick(title: String) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))

    }

    override fun onAddToListClick(title: String) {
        navigate(MediaDetailsDestinations.LoginDialogDestination(title))
    }

    override fun onShowAllCastClick(tvShowId: Int) {
        navigate(MediaDetailsDestinations.TvShowCastScreen(tvShowId = tvShowId))
    }
}