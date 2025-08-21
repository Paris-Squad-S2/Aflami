package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import org.junit.jupiter.api.BeforeEach
import com.paris.domain.media.testUtils.createMedia
import org.junit.jupiter.api.Test

class FilterByListOfCategoriesUseCaseTest {

    private lateinit var filterMediaUseCase: FilterMediaUseCase

    @BeforeEach
    fun setUp() {
        filterMediaUseCase = FilterMediaUseCase()
    }

    @Test
    fun `should return 1 item when one category matches`() {
        //Given
        val selectedCategories = listOf(Category.Action)

        //When
        val result = filterMediaUseCase(selectedCategories, mediaList)

        //Then
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun `should return correct media when one category matches`() {
        //Given
        val selectedCategories = listOf(Category.Adventure)
        val expectedMediaList = listOf(
            mediaList[0]
        )

        //When
        val result = filterMediaUseCase(selectedCategories, mediaList)

        //Then
        assertThat(result).isEqualTo(expectedMediaList)
    }

    @Test
    fun `should return empty list when no category matches`() {
        //Given
        val selectedCategories = listOf(Category.Comedy)
        val expectedMediaList = emptyList<Media>()

        //When
        val result = filterMediaUseCase(selectedCategories, mediaList)

        //Then
        assertThat(result).isEqualTo(expectedMediaList)
    }

    @Test
    fun `should return empty list when media list is empty`() {
        //Given
        val mediaList = emptyList<Media>()
        val selectedCategories = listOf(Category.Animation)

        //When
        val result = filterMediaUseCase(selectedCategories, mediaList)

        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return items matching any of the selected categories`() {
        //Given
        val selectedCategoriesList = listOf(Category.Action, Category.Animation)

        //When
        val result = filterMediaUseCase(selectedCategoriesList, mediaList)

        //Then
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun `should return correct media when multiple categories match`() {
        //Given
        val selectedCategories = listOf(Category.Action, Category.Animation)
        val expected = mediaList

        //When
        val result = filterMediaUseCase(selectedCategories, mediaList)

        //Then
        assertThat(result).isEqualTo(expected)
    }

    companion object {
        private val mediaList = listOf(
            createMedia(
                id = 1,
                title = "Movie 1",
                type = MediaType.Movie,
                categories = listOf(Category.Action, Category.Adventure)
            ),
            createMedia(id = 2, title = "Series 1", type = MediaType.TvShow, categories = listOf(Category.Animation))
        )
    }
}