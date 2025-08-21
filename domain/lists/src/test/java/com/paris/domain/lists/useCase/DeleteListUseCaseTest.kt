package com.paris.domain.lists.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.lists.repository.ListsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteListUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var deleteListUseCase: DeleteListUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        deleteListUseCase = DeleteListUseCase(listsRepository)
    }

    @Test
    fun `deleteListUseCase when valid listId then return response`() = runTest {
        val listId = "list123"
        val expectedResponse = true
        coEvery { listsRepository.deleteList(listId) } returns expectedResponse

        val result = deleteListUseCase.invoke(listId)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun `deleteListUseCase when valid listId then call repository with correct parameters`() = runTest {
        val listId = "list456"
        val expectedResponse = true
        coEvery { listsRepository.deleteList(listId) } returns expectedResponse

        deleteListUseCase.invoke(listId)

        coEvery { listsRepository.deleteList(listId) }
    }

    @Test
    fun `deleteListUseCase when error response returned then return error response`() = runTest {
        val listId = "list789"
        val expectedResponse = true
        coEvery { listsRepository.deleteList(listId) } returns expectedResponse

        val result = deleteListUseCase.invoke(listId)

        assertThat(result).isEqualTo(expectedResponse)
    }
}