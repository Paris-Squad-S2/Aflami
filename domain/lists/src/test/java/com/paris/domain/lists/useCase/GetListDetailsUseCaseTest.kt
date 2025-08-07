package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Media
import com.paris.domain.lists.repository.ListsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetListDetailsUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var getListDetailsUseCase: GetListDetailsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        getListDetailsUseCase = GetListDetailsUseCase(listsRepository)
    }

    @Test
    fun `getListDetailsUseCase when valid page and listId then return list details`() = runTest {
        val page = 1
        val listId = 100
        val mediaItems = listOf(
            Media(
                id = 1,
                imageUrl = "/poster1.jpg",
                title = "Movie 1",
                voteAverage = 8.5,
                releaseDate = LocalDate.parse("2023-01-01")
            )
        )
        val expectedListDetails = ListDetails(
            id = listId,
            name = "My Favorite Movies",
            items = mediaItems
        )
        coEvery { listsRepository.getListDetails(page, listId.toString()) } returns expectedListDetails

        val result = getListDetailsUseCase.invoke(page, listId.toString())

        assertThat(result).isEqualTo(expectedListDetails)
    }

    @Test
    fun `getListDetailsUseCase when valid page and listId then call repository with correct parameters`() = runTest {
        val page = 2
        val listId = 456
        val expectedListDetails = ListDetails(
            id = listId,
            name = "Watchlist",
            items = emptyList()
        )
        coEvery { listsRepository.getListDetails(page, listId.toString()) } returns expectedListDetails

        getListDetailsUseCase.invoke(page, listId.toString())

        coEvery { listsRepository.getListDetails(page, listId.toString()) }
    }

    @Test
    fun `getListDetailsUseCase when empty items list returned then return list details with empty items`() = runTest {
        val page = 1
        val listId = 789
        val expectedListDetails = ListDetails(
            id = listId,
            name = "Empty List",
            items = emptyList()
        )
        coEvery { listsRepository.getListDetails(page, listId.toString()) } returns expectedListDetails

        val result = getListDetailsUseCase.invoke(page, listId.toString())

        assertThat(result.items).isEmpty()
    }
}