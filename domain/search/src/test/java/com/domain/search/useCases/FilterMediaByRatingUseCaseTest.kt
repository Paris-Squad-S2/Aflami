package com.domain.search.useCases

import com.domain.search.model.MediaType
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import testUtils.createMedia
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterMediaByRatingUseCaseTest {

    private lateinit var filterMediaByRatingUseCase: FilterMediaByRatingUseCase

    @BeforeEach
    fun setUp() {
        filterMediaByRatingUseCase = FilterMediaByRatingUseCase()
    }

    @Test
    fun `should return media with rating greater than or equal to 4_0`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mediaListAllAbove4)
        assertEquals(2, result.size)
    }

    @Test
    fun `should return correct media with rating greater than or equal to 4_0`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mediaListAllAbove4)
        assertEquals(mediaListAllAbove4, result)
    }

    @Test
    fun `should return media with rating greater than or equal to 4_0 when ratings are mixed`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mixedRatingsMediaList)
        assertEquals(3, result.size)
    }

    @Test
    fun `should return correct media when ratings are mixed`() = runTest {
        val result = filterMediaByRatingUseCase(4.0f, mixedRatingsMediaList)
        assertEquals(expectedFromMixed, result)
    }

    @Test
    fun `should return empty list when all media are below given rating`() = runTest {
        val result = filterMediaByRatingUseCase(5.5f, mixedRatingsMediaList)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return empty list when media list is empty`() = runTest {
        val result = filterMediaByRatingUseCase(5.0f, emptyList())
        assertEquals(emptyList(), result)
    }

    companion object {
        private val mediaListAllAbove4 = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE, rating = 4.0),
            createMedia(id = 2, title = "Movie 2", type = MediaType.MOVIE, rating = 4.8)
        )

        private val mixedRatingsMediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE, rating = 3.5),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW, rating = 4.2),
            createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE, rating = 4.8),
            createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW, rating = 2.5),
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE, rating = 5.0)
        )

        private val expectedFromMixed = listOf(
            mixedRatingsMediaList[1],
            mixedRatingsMediaList[2],
            mixedRatingsMediaList[4]
        )
    }
}