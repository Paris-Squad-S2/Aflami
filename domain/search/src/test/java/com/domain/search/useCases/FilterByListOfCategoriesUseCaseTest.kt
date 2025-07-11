package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import org.junit.jupiter.api.BeforeEach
import testUtils.createMedia
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterByListOfCategoriesUseCaseTest {

    private lateinit var filterByListOfCategoriesUseCase: FilterByListOfCategoriesUseCase

    @BeforeEach
    fun setUp() {
        filterByListOfCategoriesUseCase = FilterByListOfCategoriesUseCase()
    }

    @Test
    fun `invoke should return media with matching categories`() {

        //Given
        val mediaList = listOf(
            createMedia(
                id = 1,
                title = "Movie 1",
                type = MediaType.MOVIE,
                categories = listOf(1, 2)
            ),
            createMedia(
                id = 2,
                title = "Series 1",
                type = MediaType.TVSHOW,
                categories = listOf(3)
            ),
        )

        val selectedCategories = listOf(1)

        val expectedMediaList = listOf(
            mediaList[0]
        )

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

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
                categories = listOf(1, 2)
            ),
            createMedia(
                id = 2,
                title = "Series 1",
                type = MediaType.TVSHOW,
                categories = listOf(3)
            ),
        )

        val selectedCategories = listOf(4)

        val expectedMediaList = emptyList<Media>()

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }

    @Test
    fun `invoke should return empty list when media list is empty`() {

        //Given
        val mediaList = emptyList<Media>()
        val selectedCategories = listOf(3)
        val expectedMediaList = emptyList<Media>()

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

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
                categories = listOf(1, 2)
            ),
            createMedia(
                id = 2,
                title = "Series 1",
                type = MediaType.TVSHOW,
                categories = listOf(3)
            ),
        )

        val selectedCategoriesList = listOf(1, 3)

        val expectedMediaList = listOf(
            mediaList[0],
            mediaList[1]
        )

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategoriesList, mediaList)

        //Then
        assertEquals(expectedMediaList, result)

    }
}