package com.repository.lists

import com.paris.domain.lists.entity.Media
import com.paris.domain.lists.entity.Response
import com.paris.domain.lists.exception.ListsNetworkException
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
import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ListsRepositoryImpTest {

    @MockK
    private lateinit var listsRemoteDataSource: ListsRemoteDataSource

    private lateinit var listsRepositoryImp: ListsRepositoryImp

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        listsRepositoryImp = ListsRepositoryImp(listsRemoteDataSource)
    }

    @Test
    fun `getLists when remote data source returns valid data then return mapped domain lists`() = runTest {
        val page = 1
        val accountId = "account123"
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
        coEvery { listsRemoteDataSource.getAccountId() } returns accountId
        coEvery { listsRemoteDataSource.getLists(page, accountId) } returns listsDto

        val result = listsRepositoryImp.getLists(page)

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(1)
        assertThat(result[0].name).isEqualTo("My List")
        assertThat(result[0].description).isEqualTo("A list of my favorite movies")
        assertThat(result[0].itemCount).isEqualTo(10)
    }

    @Test
    fun `getLists when remote data source throws server exception then throw ListsNetworkException`() = runTest {
        val page = 1
        val accountId = "account123"
        coEvery { listsRemoteDataSource.getAccountId() } returns accountId
        coEvery { listsRemoteDataSource.getLists(page, accountId) } throws NetworkException.ServerException("Server error")

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.getLists(page)
        }
        assertThat(exception.message).isEqualTo("Server error")
    }

    @Test
    fun `getListDetails when remote data source returns valid data then return mapped domain list details`() = runTest {
        val page = 1
        val listId = "list123"
        val listDetailsDto = ListDetailsDto(
            id = listId,
            name = "My List Details",
            mediaDetailsDto = listOf(
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

        val result = listsRepositoryImp.getListDetails(page, listId)

        assertThat(result.id).isEqualTo(listId)
        assertThat(result.name).isEqualTo("My List Details")
        assertThat(result.items).hasSize(1)
        assertThat(result.items[0]).isEqualTo(
            Media(
                id = 1,
                posterPath = "/poster1.jpg",
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
            statusMessage = "List deleted successfully"
        )
        coEvery { listsRemoteDataSource.deleteList(listId) } returns responseDto

        val result = listsRepositoryImp.deleteList(listId)

        assertThat(result).isEqualTo(
            Response(
                statusCode = 200,
                statusMessage = "List deleted successfully"
            )
        )
    }

    @Test
    fun `createList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listName = "New List"
        val responseDto = ResponseDto(
            statusCode = 201,
            statusMessage = "List created successfully"
        )
        coEvery { listsRemoteDataSource.createList(listName) } returns responseDto

        val result = listsRepositoryImp.createList(listName)

        assertThat(result).isEqualTo(
            Response(
                statusCode = 201,
                statusMessage = "List created successfully"
            )
        )
    }

    @Test
    fun `addMovieToList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listId = "list123"
        val movieId = 456
        val responseDto = ResponseDto(
            statusCode = 200,
            statusMessage = "Movie added successfully"
        )
        coEvery { listsRemoteDataSource.addMovieToList(listId, movieId) } returns responseDto

        val result = listsRepositoryImp.addMovieToList(listId, movieId)

        assertThat(result).isEqualTo(
            Response(
                statusCode = 200,
                statusMessage = "Movie added successfully"
            )
        )
    }

    @Test
    fun `removeMovieFromList when remote data source returns valid response then return mapped domain response`() = runTest {
        val listId = "list123"
        val movieId = 456
        val responseDto = ResponseDto(
            statusCode = 200,
            statusMessage = "Movie removed successfully"
        )
        coEvery { listsRemoteDataSource.removeMovieFromList(listId, movieId) } returns responseDto

        val result = listsRepositoryImp.removeMovieFromList(listId, movieId)


        assertThat(result).isEqualTo(
            Response(
                statusCode = 200,
                statusMessage = "Movie removed successfully"
            )
        )
    }

    @Test
    fun `getLists when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val page = 1
        val accountId = "account123"
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.getAccountId() } returns accountId
        coEvery { listsRemoteDataSource.getLists(page, accountId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.getLists(page)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `getLists when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val page = 1
        val accountId = "account123"
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.getAccountId() } returns accountId
        coEvery { listsRemoteDataSource.getLists(page, accountId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.getLists(page)
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
            listsRepositoryImp.getListDetails(page, listId)
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
            listsRepositoryImp.getListDetails(page, listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `deleteList when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.deleteList(listId) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.deleteList(listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `deleteList when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listId = "list123"
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.deleteList(listId) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.deleteList(listId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `createList when remote data source throws unknown exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listName = "New List"
        val errorMessage = "Unknown error occurred"
        
        coEvery { listsRemoteDataSource.createList(listName) } throws NetworkException.UnknownException(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.createList(listName)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }

    @Test
    fun `createList when remote data source throws generic exception then throw ListsNetworkException with unknown error message`() = runTest {
        val listName = "New List"
        val errorMessage = "Generic error occurred"
        
        coEvery { listsRemoteDataSource.createList(listName) } throws Exception(errorMessage)

        val exception = assertThrows<ListsNetworkException> {
            listsRepositoryImp.createList(listName)
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
            listsRepositoryImp.addMovieToList(listId, movieId)
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
            listsRepositoryImp.addMovieToList(listId, movieId)
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
            listsRepositoryImp.removeMovieFromList(listId, movieId)
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
            listsRepositoryImp.removeMovieFromList(listId, movieId)
        }
        
        assertThat(exception.message).isEqualTo(errorMessage)
    }
}