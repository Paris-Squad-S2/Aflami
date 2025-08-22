package com.paris.domain.lists.useCase

import com.paris.domain.lists.repository.ListsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveMovieFromListUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var removeMovieFromListUseCase: RemoveMovieFromListUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        removeMovieFromListUseCase = RemoveMovieFromListUseCase(listsRepository)
    }

    @Test
    fun `removeMovieFromListUseCase when valid listId and movieId then return response`() = runTest {
        // Given
        val listId = "list123"
        val movieId = 456
        val expectedResponse = true
        coEvery { listsRepository.removeMovieFromList(listId, movieId) } returns expectedResponse

        // When
        val result = removeMovieFromListUseCase.invoke(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun `removeMovieFromListUseCase when valid listId and movieId then call repository with correct parameters`() = runTest {
        // Given
        val listId = "list456"
        val movieId = 789
        val expectedResponse = true
        coEvery { listsRepository.removeMovieFromList(listId, movieId) } returns expectedResponse

        // When
        removeMovieFromListUseCase.invoke(listId, movieId)

        // Then
        coEvery { listsRepository.removeMovieFromList(listId, movieId) }
    }

    @Test
    fun `removeMovieFromListUseCase when error response returned then return error response`() = runTest {
        // Given
        val listId = "list789"
        val movieId = 123
        val expectedResponse = false
        coEvery { listsRepository.removeMovieFromList(listId, movieId) } returns expectedResponse

        // When
        val result = removeMovieFromListUseCase.invoke(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponse)
    }
}