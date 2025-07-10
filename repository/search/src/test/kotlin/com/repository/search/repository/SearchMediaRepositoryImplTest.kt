package com.repository.search.repository

import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.exception.NoDataForActorException
import com.repository.search.exception.NoDataForCountryException
import com.repository.search.exception.NoDataForSearchException
import com.repository.search.exception.NoInternetConnectionException
import com.repository.search.mapper.toMedias
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus

class SearchMediaRepositoryImplTest {

    private lateinit var repository: SearchMediaRepositoryImpl
    private val networkConnectionChecker = mockk<NetworkConnectionChecker>()
    private val mediaLocalDataSource = mockk<MediaLocalDataSource>()
    private val searchRemoteDataSource = mockk<SearchRemoteDataSource>()
    private val historyLocalDataSource = mockk<HistoryLocalDataSource>()

    @BeforeEach
    fun setup() {
        repository = SearchMediaRepositoryImpl(
            networkConnectionChecker,
            mediaLocalDataSource,
            searchRemoteDataSource,
            historyLocalDataSource
        )
    }

    // getMediaByActor Test Cases

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByActor should return cached media if not expired`() = runTest {
        val actorName = "Tom Hanks"
        val cachedMedia = listOf(
            MediaEntity(
                id = 1,
                searchQuery = actorName,
                imageUri = "image.jpg",
                title = "Movie 1",
                type = MediaTypeEntity.MOVIE,
                category = listOf("Action"),
                yearOfRelease = LocalDate(2024, 1, 1),
                rating = 8.5,
                country = "USA",
                actor = listOf(actorName)
            )
        )
        val oldDate = Clock.System.now()
            .minus(2, DateTimeUnit.HOUR)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName) } returns SearchHistoryEntity(actorName, oldDate)

        val result = repository.getMediaByActor(actorName)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByActor should fetch remotely if cache is expired`() = runTest {
        val actorName = "Tom Cruise"

        val expiredDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } returns listOf(
            MediaEntity(
                id = 1,
                searchQuery = actorName,
                imageUri = "old.jpg",
                title = "Old Movie",
                type = MediaTypeEntity.MOVIE,
                category = listOf("Action"),
                yearOfRelease = LocalDate(2023, 1, 1),
                rating = 7.8,
                country = "USA",
                actor = listOf(actorName)
            )
        )
        coEvery { mediaLocalDataSource.clearAllMediaBySearchQuery(actorName) } just Runs
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { searchRemoteDataSource.searchPerson(actorName) } returns mockk(relaxed = true)
        coEvery { mediaLocalDataSource.addAllMedia(any()) } just Runs
        coEvery { historyLocalDataSource.addSearchQuery(actorName) } just Runs
        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } returns emptyList()

        val result = repository.getMediaByActor(actorName)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `getMediaByActor should throw NoInternetConnectionException if no internet`() = runTest {
        val actorName = "Will Smith"

        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } returns emptyList()
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        assertFailsWith<NoInternetConnectionException> {
            repository.getMediaByActor(actorName)
        }
    }

    @Test
    fun `getMediaByActor should throw NoDataForActorException on error`() = runTest {
        val actorName = "Jim Carrey"

        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } throws RuntimeException()

        assertFailsWith<NoDataForActorException> {
            repository.getMediaByActor(actorName)
        }
    }

    // getMoviesByCountry Test Cases

    // getMediaByQuery Test Cases
}
