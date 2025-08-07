package com.datasource.remote.lists

import com.datasource.remote.lists.service.RetrofitListApiService
import com.repository.lists.exeptions.NetworkException
import com.repository.lists.model.dto.AccountDto
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
import org.junit.Before
import retrofit2.HttpException
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ListsRemoteDataSourceImpTest {

    @MockK
    private lateinit var retrofitListApiService: RetrofitListApiService

    private lateinit var listsRemoteDataSource: ListsRemoteDataSourceImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        listsRemoteDataSource = ListsRemoteDataSourceImp(retrofitListApiService)
    }

    @Test
    fun `getLists should return ListsDto when api call succeeds`() = runTest {
        // Given
        val page = 1
        val accountId = "123"
        val expectedListsDto = mockk<ListsDto>()
        coEvery { retrofitListApiService.getLists(accountId, page) } returns expectedListsDto

        // When
        val result = listsRemoteDataSource.getLists(page, accountId)

        // Then
        assertThat(result).isEqualTo(expectedListsDto)
        coVerify(exactly = 1) { retrofitListApiService.getLists(accountId, page) }
    }


    @Test
    fun `getLists should throw UnknownException when HttpException with non-5xx status code occurs`() = runTest {
        // Given
        val page = 1
        val accountId = "123"
        val httpException = mockk<HttpException>()
        coEvery { httpException.code() } returns 400
        coEvery { httpException.message() } returns "Bad Request"
        coEvery { retrofitListApiService.getLists(accountId, page) } throws httpException

        // When & Then
        val exception = assertFailsWith<NetworkException.UnknownException> {
            listsRemoteDataSource.getLists(page, accountId)
        }

        assertThat(exception.message).isEqualTo("HTTP error: Bad Request")
        coVerify(exactly = 1) { retrofitListApiService.getLists(accountId, page) }
    }

    @Test
    fun `getLists should throw UnknownException when generic exception occurs`() = runTest {
        // Given
        val page = 1
        val accountId = "123"
        coEvery { retrofitListApiService.getLists(accountId, page) } throws IOException("Network error")

        // When & Then
        val exception = assertFailsWith<NetworkException.UnknownException> {
            listsRemoteDataSource.getLists(page, accountId)
        }

        assertThat(exception.message).isEqualTo("Unexpected error: Network error")
        coVerify(exactly = 1) { retrofitListApiService.getLists(accountId, page) }
    }

    @Test
    fun `getAccountId should return account id when api call succeeds`() = runTest {
        // Given
        val accountDto = AccountDto(id = 123, name = "Test User", username = "testuser")
        coEvery { retrofitListApiService.getAccountDetails() } returns accountDto

        // When
        val result = listsRemoteDataSource.getAccountId()

        // Then
        assertThat(result).isEqualTo("123")
        coVerify(exactly = 1) { retrofitListApiService.getAccountDetails() }
    }

    @Test
    fun `getAccountId should throw ServerException when HttpException with 5xx status code occurs`() = runTest {
        // Given
        val httpException = mockk<HttpException>()
        coEvery { httpException.code() } returns 503
        coEvery { httpException.message() } returns "Service Unavailable"
        coEvery { retrofitListApiService.getAccountDetails() } throws httpException

        // When & Then
        val exception = assertFailsWith<NetworkException.ServerException> {
            listsRemoteDataSource.getAccountId()
        }

        assertThat(exception.message).isEqualTo("Server error: Service Unavailable")
        coVerify(exactly = 1) { retrofitListApiService.getAccountDetails() }
    }

    @Test
    fun `getAccountId should throw UnknownException when HttpException with non-5xx status code occurs`() = runTest {
        // Given
        val httpException = mockk<HttpException>()
        coEvery { httpException.code() } returns 401
        coEvery { httpException.message() } returns "Unauthorized"
        coEvery { retrofitListApiService.getAccountDetails() } throws httpException

        // When & Then
        val exception = assertFailsWith<NetworkException.UnknownException> {
            listsRemoteDataSource.getAccountId()
        }

        assertThat(exception.message).isEqualTo("HTTP error: Unauthorized")
        coVerify(exactly = 1) { retrofitListApiService.getAccountDetails() }
    }

    @Test
    fun `getListDetails should return ListDetailsDto when api call succeeds`() = runTest {
        // Given
        val page = 1
        val listId = "list123"
        val expectedListDetailsDto = mockk<ListDetailsDto>()
        coEvery { retrofitListApiService.getListDetails(listId, page) } returns expectedListDetailsDto

        // When
        val result = listsRemoteDataSource.getListDetails(page, listId)

        // Then
        assertThat(result).isSameInstanceAs(expectedListDetailsDto)
        coVerify(exactly = 1) { retrofitListApiService.getListDetails(listId, page) }
    }

    @Test
    fun `deleteList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listId = "list123"
        val expectedResponseDto = ResponseDto(statusCode = 200, statusMessage = "Success")
        coEvery { retrofitListApiService.deleteList(listId) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.deleteList(listId)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { retrofitListApiService.deleteList(listId) }
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
        coEvery { retrofitListApiService.createList(expectedRequestBody) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.createList(listName)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { retrofitListApiService.createList(expectedRequestBody) }
    }

    @Test
    fun `addMovieToList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listId = "list123"
        val movieId = 456
        val expectedResponseDto = ResponseDto(statusCode = 200, statusMessage = "Added")
        val expectedRequestBody = mapOf("media_id" to movieId)
        coEvery { retrofitListApiService.addMovieToList(listId, expectedRequestBody) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.addMovieToList(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { retrofitListApiService.addMovieToList(listId, expectedRequestBody) }
    }

    @Test
    fun `removeMovieFromList should return ResponseDto when api call succeeds`() = runTest {
        // Given
        val listId = "list123"
        val movieId = 456
        val expectedResponseDto = ResponseDto(statusCode = 200, statusMessage = "Removed")
        val expectedRequestBody = mapOf("media_id" to movieId)
        coEvery { retrofitListApiService.removeMovieFromList(listId, expectedRequestBody) } returns expectedResponseDto

        // When
        val result = listsRemoteDataSource.removeMovieFromList(listId, movieId)

        // Then
        assertThat(result).isEqualTo(expectedResponseDto)
        coVerify(exactly = 1) { retrofitListApiService.removeMovieFromList(listId, expectedRequestBody) }
    }

    @Test
    fun `getLists should throw ServerException when HttpException with 5xx status code occurs`() = runTest {
        // Given
        val page = 1
        val accountId = "123"
        val httpException = mockk<HttpException>()
        coEvery { httpException.code() } returns 500
        coEvery { httpException.message() } returns "Internal Server Error"
        coEvery { retrofitListApiService.getLists(accountId, page) } throws httpException

        // When & Then
        val exception = assertFailsWith<NetworkException.ServerException> {
            listsRemoteDataSource.getLists(page, accountId)
        }

        assertThat(exception.message).isEqualTo("Server error: Internal Server Error")
        coVerify(exactly = 1) { retrofitListApiService.getLists(accountId, page) }
    }
}