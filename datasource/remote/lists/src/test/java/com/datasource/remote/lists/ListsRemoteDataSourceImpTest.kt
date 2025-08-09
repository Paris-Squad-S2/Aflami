package com.datasource.remote.lists

import com.datasource.remote.lists.service.ListApiService
import com.repository.lists.exeptions.NetworkException
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.ListsDto
import com.repository.lists.model.dto.ResponseDto
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import retrofit2.HttpException
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ListsRemoteDataSourceImpTest {

    @MockK
    private lateinit var listApiService: ListApiService

    private lateinit var listsRemoteDataSource: ListsRemoteDataSourceImp

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        listsRemoteDataSource = ListsRemoteDataSourceImp(listApiService)
    }

    @Test
    fun `getLists should return ListsDto when api call succeeds`() = runTest {
        // Given
        val page = 1
        val accountId = 123
        val expectedListsDto = mockk<ListsDto>()
        coEvery { listApiService.getLists(accountId, page) } returns expectedListsDto

        // When
        val result = listsRemoteDataSource.getLists(page, accountId)

        // Then
        assertThat(result).isEqualTo(expectedListsDto)
        coVerify(exactly = 1) { listApiService.getLists(accountId, page) }
    }


    @Test
    fun `getLists should throw UnknownException when HttpException with non-5xx status code occurs`() = runTest {
        // Given
        val page = 1
        val accountId = 123
        val httpException = mockk<HttpException>()
        coEvery { httpException.code() } returns 400
        coEvery { httpException.message() } returns "Bad Request"
        coEvery { listApiService.getLists(accountId, page) } throws httpException

        // When & Then
        val exception = assertFailsWith<NetworkException.UnknownException> {
            listsRemoteDataSource.getLists(page, accountId)
        }

        assertThat(exception.message).isEqualTo("HTTP error: Bad Request")
        coVerify(exactly = 1) { listApiService.getLists(accountId, page) }
    }

    @Test
    fun `getLists should throw UnknownException when generic exception occurs`() = runTest {
        // Given
        val page = 1
        val accountId = 123
        coEvery { listApiService.getLists(accountId, page) } throws IOException("Network error")

        // When & Then
        val exception = assertFailsWith<NetworkException.UnknownException> {
            listsRemoteDataSource.getLists(page, accountId)
        }

        assertThat(exception.message).isEqualTo("Unexpected error: Network error")
        coVerify(exactly = 1) { listApiService.getLists(accountId, page) }
    }

    @Test
    fun `getListDetails should return ListDetailsDto when api call succeeds`() = runTest {
        // Given
        val page = 1
        val listId = "list123"
        val expectedListDetailsDto = mockk<ListDetailsDto>()
        coEvery { listApiService.getListDetails(listId, page) } returns expectedListDetailsDto

        // When
        val result = listsRemoteDataSource.getListDetails(page, listId)

        // Then
        assertThat(result).isSameInstanceAs(expectedListDetailsDto)
        coVerify(exactly = 1) { listApiService.getListDetails(listId, page) }
    }

    @Test
    fun `deleteList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listId = "list123"
        val expectedResponseDto = ResponseDto(statusCode = 200, statusMessage = "Success")
        coEvery { listApiService.deleteList(listId) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.deleteList(listId)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { listApiService.deleteList(listId) }
    }

    @Test
    fun `createList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listName = "My Test List"
        val expectedResponseDto = ResponseDto(statusCode = 201, statusMessage = "Created")
        val expectedRequestBody = mapOf(
            "name" to listName,
            "description" to "Created from Aflami app",
            "language" to "en"
        )
        coEvery { listApiService.createList(expectedRequestBody) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.createList(listName)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { listApiService.createList(expectedRequestBody) }
    }

    @Test
    fun `addMovieToList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listId = "list123"
        val movieId = 456
        val expectedResponseDto = ResponseDto(statusCode = 200, statusMessage = "Added")
        val expectedRequestBody = mapOf("media_id" to movieId)
        coEvery { listApiService.addMovieToList(listId, expectedRequestBody) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.addMovieToList(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { listApiService.addMovieToList(listId, expectedRequestBody) }
    }

    @Test
    fun `removeMovieFromList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listId = "list123"
        val movieId = 456
        val expectedResponseDto = ResponseDto(statusCode = 200, statusMessage = "Removed")
        val expectedRequestBody = mapOf("media_id" to movieId)
        coEvery { listApiService.removeMovieFromList(listId, expectedRequestBody) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.removeMovieFromList(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { listApiService.removeMovieFromList(listId, expectedRequestBody) }
    }

    @Test
    fun `getLists should throw ServerException when HttpException with 5xx status code occurs`() = runTest {
        // Given
        val page = 1
        val accountId = 123
        val httpException = mockk<HttpException>()
        coEvery { httpException.code() } returns 500
        coEvery { httpException.message() } returns "Internal Server Error"
        coEvery { listApiService.getLists(accountId, page) } throws httpException

        // When & Then
        val exception = assertFailsWith<NetworkException.ServerException> {
            listsRemoteDataSource.getLists(page, accountId)
        }

        assertThat(exception.message).isEqualTo("Server error: Internal Server Error")
        coVerify(exactly = 1) { listApiService.getLists(accountId, page) }
    }
}