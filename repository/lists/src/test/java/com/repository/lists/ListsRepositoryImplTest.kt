package com.repository.lists

import com.google.common.truth.Truth.assertThat
import com.paris.domain.lists.entity.Media
import com.paris.domain.lists.exception.ListsNetworkException
import com.paris.repository.user.dataSource.remote.UserRemoteDataSource
import com.paris.repository.user.model.remote.AccountDto
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.lists.exeptions.NetworkException
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.ListDto
import com.repository.lists.model.dto.ListsDto
import com.repository.lists.model.dto.MediaDetailsDto
import com.repository.lists.model.dto.ResponseDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class ListsRepositoryImplTest {

    @MockK
    private lateinit var listsRemoteDataSource: ListsRemoteDataSource

    @MockK
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private lateinit var listsRepositoryImpl: ListsRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        listsRepositoryImpl = ListsRepositoryImpl(listsRemoteDataSource, userRemoteDataSource)
    }

    @Test
    fun `getLists when remote data source returns valid data then return mapped domain lists`() = runTest {
        val page = 1
        val accountId = 123
        val accountDto = AccountDto(
            id = accountId,
            name = "",
            username = ""
        )
        val listsDto = ListsDto(
            page = 1,
            listDto = listOf(
                ListDto(
                    id = 1,
                    name = "My List",
                    description = "A list of my favorite movies",
                    itemCount = 10
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        coEvery { userRemoteDataSource.getAccountDetails() } returns accountDto
        coEvery { listsRemoteDataSource.getLists(page, accountId) } returns listsDto

        val result = listsRepositoryImpl.getLists(page)

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(1)
        assertThat(result[0].name).isEqualTo("My List")
        assertThat(result[0].description).isEqualTo("A list of my favorite movies")
        assertThat(result[0].itemCount).isEqualTo(10)
    }

    @Test
    fun `getLists when remote data source throws server exception then throw ListsNetworkException`() = runTest {
        val page = 1
        val accountId = 123
        val accountDto = AccountDto(
            id = accountId,
            name = "",
            username = ""
        )
        coEvery { userRemoteDataSource.getAccountDetails() } returns accountDto
        coEvery { listsRemoteDataSource.getLists(page, accountId) } throws NetworkException.ServerException("Server error")

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.getLists(page)
        }
        assertThat(exception.message).isEqualTo("Server error")
    }

    @Test
    fun `getListDetails when remote data source returns valid data then return mapped domain list details`() = runTest {
        val page = 1
        val listId = "123"
        val listDetailsDto = ListDetailsDto(
            id = 123,
            name = "My List Details",
            items = listOf(
                MediaDetailsDto(
                    id= 1,
                    posterPath = "/poster1.jpg",
                    title = "Movie 1",
                    voteAverage = 8.5,
                    releaseDate = "2023-01-01"
                )
            ),
            itemCount = 1
        )
        coEvery { listsRemoteDataSource.getListDetails(page, listId) } returns listDetailsDto

        val result = listsRepositoryImpl.getListDetails(page, listId)

        assertThat(result.id).isEqualTo(123)
        assertThat(result.name).isEqualTo("My List Details")
        assertThat(result.items).hasSize(1)
        assertThat(result.items[0]).isEqualTo(
            Media(
                id = 1,
                imageUrl = "https://image.tmdb.org/t/p/w500//poster1.jpg",
                title = "Movie 1",
                voteAverage = 8.5,
                releaseDate =  LocalDate.parse("2023-01-01")
            )
        )
    }

    @Test
    fun `deleteList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listId = "list123"
        val responseDto = ResponseDto(
            statusCode = 200,
            statusMessage = "List deleted successfully",
            success = true
        )
        coEvery { listsRemoteDataSource.deleteList(listId) } returns responseDto

        val result = listsRepositoryImpl.deleteList(listId)

        assertTrue (result)
    }

    @Test
    fun `createList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listName = "New List"
        val responseDto = ResponseDto(
            statusCode = 201,
            statusMessage = "List created successfully",
            success = true
        )
        coEvery { listsRemoteDataSource.createList(listName) } returns responseDto

        val result = listsRepositoryImpl.createList(listName)

        assertTrue (result)

    }

    @Test
    fun `addMovieToList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listId = "list123"
        val movieId = 456
        val responseDto = ResponseDto(
            statusCode = 200,
            statusMessage = "Movie added successfully",
            success = true
        )
        coEvery { listsRemoteDataSource.addMovieToList(listId, movieId) } returns responseDto

        val result = listsRepositoryImpl.addMovieToList(listId, movieId)

        assertTrue (result)

    }

    @Test
    fun `removeMovieFromList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listId = "list123"
        val movieId = 456
        val responseDto = ResponseDto(
            statusCode = 200,
            statusMessage = "Movie removed successfully",
            success = true
        )
        coEvery { listsRemoteDataSource.removeMovieFromList(listId, movieId) } returns responseDto

        val result = listsRepositoryImpl.removeMovieFromList(listId, movieId)


        assertTrue (result)

    }

    @Test
    fun `getLists when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val page = 1
        val accountId = 123
        val accountDto = AccountDto(
            id = accountId,
            name = "",
            username = ""
        )
        val errorMessage = "Unknown error occurred"
        
        coEvery { userRemoteDataSource.getAccountDetails() } returns accountDto
        coEvery { listsRemoteDataSource.getLists(page, accountId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.getLists(page)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `getLists when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val page = 1
        val accountId = 123
        val accountDto = AccountDto(
            id = accountId,
            name = "",
            username = ""
        )
        val errorMessage = "Generic error occurred"
        
        coEvery { userRemoteDataSource.getAccountDetails() } returns accountDto
        coEvery { listsRemoteDataSource.getLists(page, accountId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.getLists(page)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `getListDetails when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val page = 1
        val listId = "list123"
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.getListDetails(page, listId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.getListDetails(page, listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `getListDetails when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val page = 1
        val listId = "list123"
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.getListDetails(page, listId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.getListDetails(page, listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `deleteList when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.deleteList(listId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.deleteList(listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `deleteList when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.deleteList(listId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.deleteList(listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `createList when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listName = "New List"
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.createList(listName) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.createList(listName)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `createList when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listName = "New List"
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.createList(listName) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.createList(listName)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `addMovieToList when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val movieId = 456
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.addMovieToList(listId, movieId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.addMovieToList(listId, movieId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `addMovieToList when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val movieId = 456
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.addMovieToList(listId, movieId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.addMovieToList(listId, movieId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `removeMovieFromList when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val movieId = 456
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.removeMovieFromList(listId, movieId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.removeMovieFromList(listId, movieId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `removeMovieFromList when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val movieId = 456
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.removeMovieFromList(listId, movieId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImpl.removeMovieFromList(listId, movieId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }
}