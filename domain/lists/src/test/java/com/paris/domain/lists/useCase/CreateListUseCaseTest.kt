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

class CreateListUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var createListUseCase: CreateListUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        createListUseCase = CreateListUseCase(listsRepository)
    }

    @Test
    fun `createListUseCase when valid name then return response`() = runTest {
        val listName = "My New List"
        val expectedResponse = Response(
            statusCode = 200,
            statusMessage = "List created successfully",
            success = true
        )
        coEvery { listsRepository.createList(listName) } returns expectedResponse

        val result = createListUseCase.invoke(listName)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun `createListUseCase when valid name then call repository with correct parameters`() = runTest {
        val listName = "Another List"
        val expectedResponse = Response(
            statusCode = 200,
            statusMessage = "List created successfully",
            success = true
        )
        coEvery { listsRepository.createList(listName) } returns expectedResponse

        createListUseCase.invoke(listName)

        coEvery { listsRepository.createList(listName) }
    }

    @Test
    fun `createListUseCase when error response returned then return error response`() = runTest {
        val listName = "Invalid List"
        val expectedResponse = Response(
            statusCode = 400,
            statusMessage = "Invalid list name",
            success = true
        )
        coEvery { listsRepository.createList(listName) } returns expectedResponse

        val result = createListUseCase.invoke(listName)

        assertThat(result).isEqualTo(expectedResponse)
    }
}