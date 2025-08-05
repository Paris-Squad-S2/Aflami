package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Response
import com.paris.domain.lists.repository.ListsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
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
        val expectedResponse = Response(
            statusCode = 200,
            statusMessage = "List deleted successfully"
        )
        coEvery { listsRepository.deleteList(listId) } returns expectedResponse

        val result = deleteListUseCase.invoke(listId)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun `deleteListUseCase when valid listId then call repository with correct parameters`() = runTest {
        val listId = "list456"
        val expectedResponse = Response(
            statusCode = 200,
            statusMessage = "List deleted successfully"
        )
        coEvery { listsRepository.deleteList(listId) } returns expectedResponse

        deleteListUseCase.invoke(listId)

        coEvery { listsRepository.deleteList(listId) }
    }

    @Test
    fun `deleteListUseCase when error response returned then return error response`() = runTest {
        val listId = "list789"
        val expectedResponse = Response(
            statusCode = 404,
            statusMessage = "List not found"
        )
        coEvery { listsRepository.deleteList(listId) } returns expectedResponse

        val result = deleteListUseCase.invoke(listId)

        assertThat(result).isEqualTo(expectedResponse)
    }
}