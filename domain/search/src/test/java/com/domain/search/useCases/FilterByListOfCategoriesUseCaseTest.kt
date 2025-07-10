package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import org.junit.jupiter.api.BeforeEach
import testUtils.createMedia
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterByListOfCategoriesUseCaseTest {

    private lateinit var useCase: FilterByListOfCategoriesUseCase

    @BeforeEach
    fun setUp() {
        useCase = FilterByListOfCategoriesUseCase()
    }

    @Test
    fun `invoke should return media with matching categories`() {

        //Given
        val mediaList = listOf(
            createMedia(
                id = 1,
                title = "Movie 1",
                type = MediaType.MOVIE,
                categories = listOf("Action", "Romance")
            ),
            createMedia(
                id = 2,
                title = "Series 1",
                type = MediaType.TVSHOW,
                categories = listOf("Comedy")
            ),
        )

        val selectedCategories = listOf("Action")

        val expectedMediaList = listOf(
            mediaList[0]
        )

        //When
        val result = useCase(selectedCategories, mediaList)

        //Then
        assertEquals(1, result.size)
        assertEquals(expectedMediaList, result)

    }

    @Test
    fun `invoke should return empty list when no media matches categories`() {

        //Given
        val mediaList = listOf(
            createMedia(
                id = 1,
                title = "Movie 1",
                type = MediaType.MOVIE,
                categories = listOf("Action", "Romance")
            ),
            createMedia(
                id = 2,
                title = "Series 1",
                type = MediaType.TVSHOW,
                categories = listOf("Comedy")
            ),
        )

        val selectedCategories = listOf("Science Fiction")

        val expectedMediaList = emptyList<Media>()

        //When
        val result = useCase(selectedCategories, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }

    @Test
    fun `invoke should return empty list when media list is empty`() {

        //Given
        val mediaList = emptyList<Media>()
        val selectedCategories = listOf("Comedy")
        val expectedMediaList = emptyList<Media>()

        //When
        val result = useCase(selectedCategories, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }

    @Test
    fun `invoke should return all media when multiple categories match`() {

        //Given
        val mediaList = listOf(
            createMedia(
                id = 1,
                title = "Movie 1",
                type = MediaType.MOVIE,
                categories = listOf("Action", "Romance")
            ),
            createMedia(
                id = 2,
                title = "Series 1",
                type = MediaType.TVSHOW,
                categories = listOf("Comedy")
            ),
        )

        val selectedCategoriesList = listOf("Action", "Comedy")

        val expectedMediaList = listOf(
            mediaList[0],
            mediaList[1]
        )

        //When
        val result = useCase(selectedCategoriesList, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }


}