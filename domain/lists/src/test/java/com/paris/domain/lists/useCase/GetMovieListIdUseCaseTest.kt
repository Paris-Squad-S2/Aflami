package com.paris.domain.lists.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.entity.Media
import com.paris.domain.lists.repository.ListsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMovieListIdUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var getMovieListIdUseCase: GetMovieListIdUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieListIdUseCase = GetMovieListIdUseCase(listsRepository)
    }

    @Test
    fun `when movie exists in list then return listId`() = runTest {
        val movieId = 101
        val listId = 123
        val lists = listOf(Lists(id = listId, name = "Favorites", description = "", itemCount = 1))
        val listDetails = ListDetails(
            id = listId,
            name = "Favorites",
            items = listOf(
                Media(
                    id = movieId,
                    imageUrl = "image.jpg",
                    title = "Test Movie",
                    voteAverage = 8.5,
                    releaseDate = LocalDate(2025, 8, 23)
                )
            )
        )

        coEvery { listsRepository.getLists(page = 1) } returns lists
        coEvery {
            listsRepository.getListDetails(
                page = 1,
                listId = listId.toString()
            )
        } returns listDetails

        val result = getMovieListIdUseCase.invoke(movieId)

        assertThat(result).isEqualTo(listId.toString())
    }

    @Test
    fun `when movie does not exist in any list then return null`() = runTest {
        val movieId = 202
        val listId = 456
        val lists = listOf(Lists(id = listId, name = "Watchlist", description = "", itemCount = 1))
        val listDetails = ListDetails(
            id = listId,
            name = "Watchlist",
            items = listOf(
                Media(
                    id = 999,
                    imageUrl = "image2.jpg",
                    title = "Other Movie",
                    voteAverage = 7.0,
                    releaseDate = LocalDate(2025, 1, 1)
                )
            )
        )

        coEvery { listsRepository.getLists(page = 1) } returns lists
        coEvery {
            listsRepository.getListDetails(
                page = 1,
                listId = listId.toString()
            )
        } returns listDetails

        val result = getMovieListIdUseCase.invoke(movieId)

        assertThat(result).isNull()
    }

    @Test
    fun `when multiple lists and movie exists in second list then return second listId`() =
        runTest {
            val movieId = 303
            val list1 = Lists(id = 1, name = "First List", description = "", itemCount = 1)
            val list2 = Lists(id = 2, name = "Second List", description = "", itemCount = 1)

            val listDetails1 = ListDetails(
                id = 1,
                name = "First List",
                items = listOf(
                    Media(
                        id = 111,
                        imageUrl = "img1.jpg",
                        title = "Other Movie",
                        voteAverage = 6.0,
                        releaseDate = LocalDate(2024, 5, 10)
                    )
                )
            )
            val listDetails2 = ListDetails(
                id = 2,
                name = "Second List",
                items = listOf(
                    Media(
                        id = movieId,
                        imageUrl = "img2.jpg",
                        title = "Target Movie",
                        voteAverage = 9.0,
                        releaseDate = LocalDate(2025, 7, 15)
                    )
                )
            )

            coEvery { listsRepository.getLists(page = 1) } returns listOf(list1, list2)
            coEvery { listsRepository.getListDetails(page = 1, listId = "1") } returns listDetails1
            coEvery { listsRepository.getListDetails(page = 1, listId = "2") } returns listDetails2

            val result = getMovieListIdUseCase.invoke(movieId)

            assertThat(result).isEqualTo("2")
        }

    @Test
    fun `when repository returns empty lists then return null`() = runTest {
        coEvery { listsRepository.getLists(page = 1) } returns emptyList()

        val result = getMovieListIdUseCase.invoke(404)

        assertThat(result).isNull()
    }
}