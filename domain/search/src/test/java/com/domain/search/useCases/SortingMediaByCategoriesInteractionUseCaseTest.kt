package com.domain.search.useCases

import com.domain.search.model.GenreUserInteractionModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.GenresInteractionRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class SortingMediaByCategoriesInteractionUseCaseTest {
    private lateinit var genresInteractionRepository: GenresInteractionRepository
    private lateinit var sortingUseCase: SortingMediaByCategoriesInteractionUseCase

    @BeforeEach
    fun setUp() {
        genresInteractionRepository = mockk(relaxed = true)
        sortingUseCase = SortingMediaByCategoriesInteractionUseCase(genresInteractionRepository)
    }

    private val sampleDate = LocalDate(2020, 1, 1)
    private val defaultImage = "example.jpg"

    @Test
    fun `should sort media by sum of category interaction counts descending`() = runTest {
        val interactions = listOf(
            GenreUserInteractionModel(genreId = 1, interactionCount = 10),
            GenreUserInteractionModel(genreId = 2, interactionCount = 5),
            GenreUserInteractionModel(genreId = 3, interactionCount = 1)
        )
        coEvery { genresInteractionRepository.getAllInteractions() } returns interactions

        val mediaList = listOf(
            Media(
                id = 101,
                imageUri = defaultImage,
                title = "A",
                type = MediaType.MOVIE,
                categories = listOf(1, 3),
                yearOfRelease = sampleDate,
                rating = 7.2
            ),
            Media(
                id = 102,
                imageUri = defaultImage,
                title = "B",
                type = MediaType.TVSHOW,
                categories = listOf(2),
                yearOfRelease = sampleDate,
                rating = 7.2
            ),
            Media(
                id = 103,
                imageUri = defaultImage,
                title = "C",
                type = MediaType.MOVIE,
                categories = listOf(3),
                yearOfRelease = sampleDate,
                rating = 7.2
            ),
            Media(
                id = 104,
                imageUri = defaultImage,
                title = "D",
                type = MediaType.TVSHOW,
                categories = listOf(2, 3),
                yearOfRelease = sampleDate,
                rating = 7.2
            )
        )
        val result = sortingUseCase(mediaList)
        assertEquals(listOf(101, 104, 102, 103), result.map { it.id })
    }

    @Test
    fun `should preserve input order when media have equal category interaction sums`() = runTest {
        val interactions = listOf(
            GenreUserInteractionModel(genreId = 1, interactionCount = 10),
            GenreUserInteractionModel(genreId = 2, interactionCount = 0)
        )
        coEvery { genresInteractionRepository.getAllInteractions() } returns interactions
        val mediaList = listOf(
            Media(
                id = 200,
                imageUri = defaultImage,
                title = "A",
                type = MediaType.MOVIE,
                categories = listOf(1),
                yearOfRelease = sampleDate,
                rating = 7.5
            ),
            Media(
                id = 201,
                imageUri = defaultImage,
                title = "B",
                type = MediaType.TVSHOW,
                categories = listOf(2),
                yearOfRelease = sampleDate,
                rating = 5.1
            )
        )
        val result = sortingUseCase(mediaList)
        assertEquals(listOf(200, 201), result.map { it.id })
    }

    @Test
    fun `should order medias with no matching genres as zero interaction`() = runTest {
        val interactions = listOf(
            GenreUserInteractionModel(genreId = 1, interactionCount = 7)
        )
        coEvery { genresInteractionRepository.getAllInteractions() } returns interactions
        val mediaList = listOf(
            Media(
                id = 300,
                imageUri = defaultImage,
                title = "A",
                type = MediaType.TVSHOW,
                categories = listOf(2),
                yearOfRelease = sampleDate,
                rating = 10.0
            ),
            Media(
                id = 301,
                imageUri = defaultImage,
                title = "B",
                type = MediaType.MOVIE,
                categories = listOf(1),
                yearOfRelease = sampleDate,
                rating = 8.5
            )
        )
        val result = sortingUseCase(mediaList)
        assertEquals(listOf(301, 300), result.map { it.id })
    }

    @Test
    fun `should return empty list when input media list is empty`() = runTest {
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()
        val result = sortingUseCase(emptyList())
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should treat all zero when repository returns no interactions`() = runTest {
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()
        val mediaList = listOf(
            Media(
                id = 401,
                imageUri = defaultImage,
                title = "A",
                type = MediaType.TVSHOW,
                categories = listOf(2),
                yearOfRelease = sampleDate,
                rating = 3.1
            ),
            Media(
                id = 402,
                imageUri = defaultImage,
                title = "B",
                type = MediaType.MOVIE,
                categories = listOf(5),
                yearOfRelease = sampleDate,
                rating = 8.1
            )
        )
        val result = sortingUseCase(mediaList)
        assertEquals(listOf(401, 402), result.map { it.id })
    }
}
