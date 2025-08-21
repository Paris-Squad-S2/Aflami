package com.paris.domain.media.useCase

import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.testUtils.createMedia
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat

class FilterMediaByRatingUseCaseTest {

    private lateinit var filterMediaByRatingUseCase: FilterMediaByRatingUseCase

    @BeforeEach
    fun setUp() {
        filterMediaByRatingUseCase = FilterMediaByRatingUseCase()
    }

    @Test
    fun `should return media with rating greater than or equal to 4_0`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mediaListAllAbove4)
        assertThat(result).hasSize(2)
    }

    @Test
    fun `should return correct media with rating greater than or equal to 4_0`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mediaListAllAbove4)
        assertThat(result).isEqualTo(mediaListAllAbove4)
    }

    @Test
    fun `should return media with rating greater than or equal to 4_0 when ratings are mixed`() =
        runTest {
            val result = filterMediaByRatingUseCase(4.0f, mixedRatingsMediaList)
            assertThat(result.size).isEqualTo(3)
        }

    @Test
    fun `should return correct media when ratings are mixed`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mixedRatingsMediaList)
        assertThat(result).isEqualTo(expectedFromMixed)
    }

    @Test
    fun `should return empty list when all media are below given rating`() = runTest {
        val result = filterMediaByRatingUseCase(5.5f, mixedRatingsMediaList)
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when media list is empty`() = runTest {
        val result = filterMediaByRatingUseCase(5.0f, emptyList())
        assertThat(result).isEmpty()
    }

    @Test
    fun `should filter out media with null rating`() = runTest {
        val mediaWithNullRating = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie, rating = null),
            createMedia(id = 2, title = "Series 1", type = MediaType.TvShow, rating = 4.2),
            createMedia(id = 3, title = "Movie 2", type = MediaType.Movie, rating = null),
            createMedia(id = 4, title = "Series 2", type = MediaType.TvShow, rating = 3.5)
        )

        val result = filterMediaByRatingUseCase(3.0f, mediaWithNullRating)

        assertThat(result[0]).isEqualTo(mediaWithNullRating[1])
    }

    companion object {
        private val mediaListAllAbove4 = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie, rating = 4.0),
            createMedia(id = 2, title = "Movie 2", type = MediaType.Movie, rating = 4.8)
        )

        private val mixedRatingsMediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie, rating = 3.5),
            createMedia(id = 2, title = "Series 1", type = MediaType.TvShow, rating = 4.2),
            createMedia(id = 3, title = "Movie 2", type = MediaType.Movie, rating = 4.8),
            createMedia(id = 4, title = "Series 2", type = MediaType.TvShow, rating = 2.5),
            createMedia(id = 5, title = "Movie 3", type = MediaType.Movie, rating = 5.0)
        )

        private val expectedFromMixed = listOf(
            mixedRatingsMediaList[1],
            mixedRatingsMediaList[2],
            mixedRatingsMediaList[4]
        )
    }
}