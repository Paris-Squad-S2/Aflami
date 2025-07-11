package com.domain.search.useCases

import com.domain.search.model.Media
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
    fun `invoke should return all media with rating greater that or equal to given rating`() =
        runTest {

            //Given
            val mediaList = listOf(
                createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE, rating = 4.0),
                createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE, rating = 4.8),

                )

            val expectedMediaList = mediaList

            //When
            val result = filterMediaByRatingUseCase(4.0f, mediaList)

            //Then
            assertEquals(2, result.size)
            assertEquals(expectedMediaList, result)

        }

    @Test
    fun `invoke should return only media with rating greater than or equal to given rating when mixed ratings exist`() =
        runTest {
            //Given
            val mediaList = listOf(
                createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE, rating = 3.5),
                createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW, rating = 4.2),
                createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE, rating = 4.8),
                createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW, rating = 2.5),
                createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE, rating = 5.0)
            )

            val expectedMediaList = listOf(
                mediaList[1],
                mediaList[2],
                mediaList[4]
            )

            //When
            val result = filterMediaByRatingUseCase(4.0f, mediaList)

            //Then
            assertEquals(3, result.size)
            assertEquals(expectedMediaList, result)
        }

    @Test
    fun `invoke should return empty list when no media meets rating requirements`() = runTest {

        //Given
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE, rating = 4.0),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW, rating = 3.0),
            createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE, rating = 4.8),
            createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW, rating = 2.5),
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE, rating = 2.0)
        )

        val expectedMediaList = emptyList<Media>()

        //When
        val result = filterMediaByRatingUseCase(5.5f, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }

    @Test
    fun `invoke should return empty list when media list is empty`() = runTest {

        //Given
        val mediaList = emptyList<Media>()
        val expectedMediaList = emptyList<Media>()

        //When
        val result = filterMediaByRatingUseCase(5.0f, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }


}