package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.repository.ListsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetListUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var getListUseCase: GetListUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        getListUseCase = GetListUseCase(listsRepository)
    }

    @Test
    fun `getListUseCase when valid page then return lists`() = runTest {
        val page = 1
        val expectedLists = listOf(
            Lists(
                id = 1,
                name = "My List",
                description = "A list of my favorite movies",
                itemCount = 10
            )
        )
        coEvery { listsRepository.getLists(page) } returns expectedLists

        val result = getListUseCase.invoke(page)

        assertThat(result).isEqualTo(expectedLists)
    }

    @Test
    fun `getListUseCase when valid page then call repository with correct page`() = runTest {
        val page = 2
        val expectedLists = emptyList<Lists>()
        coEvery { listsRepository.getLists(page) } returns expectedLists

        getListUseCase.invoke(page)

        coEvery { listsRepository.getLists(page) }
    }

    @Test
    fun `getListUseCase when empty list returned then return empty list`() = runTest {
        val page = 1
        val expectedLists = emptyList<Lists>()
        coEvery { listsRepository.getLists(page) } returns expectedLists

        val result = getListUseCase.invoke(page)

        assertThat(result).isEmpty()
    }
}