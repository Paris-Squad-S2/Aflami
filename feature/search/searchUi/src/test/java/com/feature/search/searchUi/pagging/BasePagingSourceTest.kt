package com.feature.search.searchUi.pagging

import androidx.paging.PagingSource
import com.domain.search.exception.NoInternetConnectionException
import com.domain.search.exception.NoMediaForActorException
import com.domain.search.exception.NoMediaForCountryException
import com.domain.search.exception.NoMediaForSearchException
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BasePagingSourceTest {

    private val mockSearchUseCase: suspend (String, Int) -> List<String> = mockk()

    private fun createPagingSource(): BasePagingSource<String> {
        return object : BasePagingSource<String>("test_query", mockSearchUseCase) {}
    }

    @Test
    fun `load should return page with data when search is successful`() = runTest {
        // Given
        val expectedData = listOf("result1", "result2", "result3")
        coEvery { mockSearchUseCase("test_query", 1) } returns expectedData
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val pageResult = result as PagingSource.LoadResult.Page
        assertThat(pageResult.data).isEqualTo(expectedData)
        assertThat(pageResult.prevKey).isNull()
        assertThat(pageResult.nextKey).isEqualTo(2)
    }

    @Test
    fun `load should return empty page when NoMediaForSearchException is thrown`() = runTest {
        // Given
        coEvery { mockSearchUseCase("test_query", 1) } throws NoMediaForSearchException()
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val pageResult = result as PagingSource.LoadResult.Page
        assertThat(pageResult.data).isEmpty()
        assertThat(pageResult.prevKey).isNull()
        assertThat(pageResult.nextKey).isNull() // Should stop pagination
    }

    @Test
    fun `load should return empty page when NoMediaForActorException is thrown`() = runTest {
        // Given
        coEvery { mockSearchUseCase("test_query", 1) } throws NoMediaForActorException()
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val pageResult = result as PagingSource.LoadResult.Page
        assertThat(pageResult.data).isEmpty()
        assertThat(pageResult.prevKey).isNull()
        assertThat(pageResult.nextKey).isNull() // Should stop pagination
    }

    @Test
    fun `load should return empty page when NoMediaForCountryException is thrown`() = runTest {
        // Given
        coEvery { mockSearchUseCase("test_query", 1) } throws NoMediaForCountryException()
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val pageResult = result as PagingSource.LoadResult.Page
        assertThat(pageResult.data).isEmpty()
        assertThat(pageResult.prevKey).isNull()
        assertThat(pageResult.nextKey).isNull() // Should stop pagination
    }

    @Test
    fun `load should return error when other exception is thrown`() = runTest {
        // Given
        val exception = NoInternetConnectionException()
        coEvery { mockSearchUseCase("test_query", 1) } throws exception
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        val errorResult = result as PagingSource.LoadResult.Error
        assertThat(errorResult.throwable).isEqualTo(exception)
    }

    @Test
    fun `load should return page with nextKey null when response is empty`() = runTest {
        // Given
        coEvery { mockSearchUseCase("test_query", 1) } returns emptyList()
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val pageResult = result as PagingSource.LoadResult.Page
        assertThat(pageResult.data).isEmpty()
        assertThat(pageResult.prevKey).isNull()
        assertThat(pageResult.nextKey).isNull() // Should stop pagination for empty results
    }

    @Test
    fun `load should set correct prevKey for page 2`() = runTest {
        // Given
        val expectedData = listOf("result1", "result2")
        coEvery { mockSearchUseCase("test_query", 2) } returns expectedData
        val pagingSource = createPagingSource()

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 2,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val pageResult = result as PagingSource.LoadResult.Page
        assertThat(pageResult.prevKey).isEqualTo(1)
        assertThat(pageResult.nextKey).isEqualTo(3)
    }
}