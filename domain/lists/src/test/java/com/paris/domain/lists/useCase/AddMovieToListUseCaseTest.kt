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

class AddMovieToListUseCaseTest {

    @MockK
    private lateinit var listsRepository: ListsRepository

    private lateinit var addMovieToListUseCase: AddMovieToListUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        addMovieToListUseCase = AddMovieToListUseCase(listsRepository)
    }

    @Test
    fun `addMovieToListUseCase when valid listId and movieId then return response`() = runTest {
        // Given
        val listId = "list123"
        val movieId = 456
        val expectedResponse = Response(
            statusCode = 200,
            statusMessage = "Movie added successfully",
            success = true
        )
        coEvery { listsRepository.addMovieToList(listId, movieId) } returns expectedResponse

        // When
        val result = addMovieToListUseCase.invoke(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun `addMovieToListUseCase when valid listId and movieId then call repository with correct parameters`() = runTest {
        // Given
        val listId = "list456"
        val movieId = 789
        val expectedResponse = Response(
            statusCode = 200,
            statusMessage = "Movie added successfully",
            success = true
        )
        coEvery { listsRepository.addMovieToList(listId, movieId) } returns expectedResponse

        // When
        addMovieToListUseCase.invoke(listId, movieId)

        // Then
        coEvery { listsRepository.addMovieToList(listId, movieId) }
    }

    @Test
    fun `addMovieToListUseCase when error response returned then return error response`() = runTest {
        // Given
        val listId = "list789"
        val movieId = 123
        val expectedResponse = Response(
            statusCode = 404,
            statusMessage = "List not found",
            success = false
        )
        coEvery { listsRepository.addMovieToList(listId, movieId) } returns expectedResponse

        // When
        val result = addMovieToListUseCase.invoke(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponse)
    }
}