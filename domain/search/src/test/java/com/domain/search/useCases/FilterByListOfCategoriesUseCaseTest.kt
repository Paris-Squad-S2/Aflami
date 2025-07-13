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
    fun `should return 1 item when one category matches`() {
        //Given
        val selectedCategories = listOf(1)

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

        //Then
        assertEquals(1, result.size)
    }

    @Test
    fun `should return correct media when one category matches`() {
        //Given
        val selectedCategories = listOf(1)
        val expectedMediaList = listOf(
            mediaList[0]
        )

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

        //Then
        assertEquals(expectedMediaList, result)
    }

    @Test
    fun `should return empty list when no category matches`() {
        //Given
        val selectedCategories = listOf(4)
        val expectedMediaList = emptyList<Media>()

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

        //Then
        assertEquals(expectedMediaList, result)
    }

    @Test
    fun `should return empty list when media list is empty`() {
        //Given
        val mediaList = emptyList<Media>()
        val selectedCategories = listOf(3)

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

        //Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return items matching any of the selected categories`() {
        //Given
        val selectedCategoriesList = listOf(1, 3)

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategoriesList, mediaList)

        //Then
        assertEquals(2, result.size)
    }

    @Test
    fun `should return correct media when multiple categories match`() {
        //Given
        val selectedCategories = listOf(1, 3)
        val expected = mediaList

        //When
        val result = filterByListOfCategoriesUseCase(selectedCategories, mediaList)

        //Then
        assertEquals(expected, result)
    }

    companion object {
        private val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE, categories = listOf(1, 2)),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW, categories = listOf(3))
        )
    }
}