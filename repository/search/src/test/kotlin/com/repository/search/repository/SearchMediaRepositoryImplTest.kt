package com.repository.search.repository

import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import com.repository.search.exception.NoDataForActorException
import com.repository.search.exception.NoDataForCountryException
import com.repository.search.exception.NoDataForSearchException
import com.repository.search.exception.NoInternetConnectionException
import com.repository.search.mapper.toMedia
import com.repository.search.mapper.toMedias
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
                category = listOf(1),
                yearOfRelease = LocalDate(2024, 1, 1),
                rating = 8.5,
                searchType = SearchType.Actor
            )
        )
        val oldDate = Clock.System.now()
            .minus(30, DateTimeUnit.MINUTE)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor) } returns SearchHistoryEntity(
            actorName,
           SearchType.Query, oldDate
        )

        val result = repository.getMediaByActor(actorName)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByActor should fetch remotely if cache is expired`() = runTest {
        val actorName = "Tom Cruise"

        coEvery { mediaLocalDataSource.getMediaByActor(actorName) } returns listOf(
            MediaEntity(
                id = 1,
                searchQuery = actorName,
                imageUri = "old.jpg",
                title = "Old Movie",
                type = MediaTypeEntity.MOVIE,
                category = listOf(1),
                yearOfRelease = LocalDate(2023, 1, 1),
                rating = 7.8,
                searchType = SearchType.Actor
            )
        )
        coEvery { mediaLocalDataSource.clearAllMediaBySearchQuery(actorName, SearchType.Actor) } just Runs
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { searchRemoteDataSource.searchPerson(actorName, language = any()) } returns mockk(
            relaxed = true
        )
        coEvery { mediaLocalDataSource.addAllMedia(any()) } just Runs
        coEvery { historyLocalDataSource.addSearchQuery(actorName, SearchType.Actor) } just Runs
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

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMoviesByCountry should return cached media if not expired`() = runTest {
        val countryName = "USA"
        val cachedMedia = listOf(
            MediaEntity(
                id = 1,
                searchQuery = countryName,
                imageUri = "image.jpg",
                title = "Movie 1",
                type = MediaTypeEntity.MOVIE,
                category = listOf(3),
                yearOfRelease = LocalDate(2022, 5, 20),
                rating = 7.8,
                searchType = SearchType.Country
            )
        )
        val validDate = Clock.System.now()
            .minus(30, DateTimeUnit.MINUTE)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns SearchHistoryEntity(
            countryName,
           SearchType.Country, validDate
        )

        val result = repository.getMoviesByCountry(countryName)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMoviesByCountry should fetch remotely if cache expired`() = runTest {
        val countryName = "Egypt"
        val expiredDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val oldMedia = listOf(
            MediaEntity(
                id = 2,
                searchQuery = countryName,
                imageUri = "old.jpg",
                title = "Old Movie",
                type = MediaTypeEntity.MOVIE,
                category = listOf(1),
                yearOfRelease = LocalDate(2020, 1, 1),
                rating = 6.0,
                searchType = SearchType.Country
            )
        )

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName) } returns oldMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns SearchHistoryEntity(
            countryName,
           SearchType.Country, expiredDate
        )
        coEvery { mediaLocalDataSource.clearAllMediaBySearchQuery(countryName, SearchType.Country) } just Runs
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery {
            searchRemoteDataSource.searchCountryCode(
                query = countryName,
                countryCode = countryName,
                language = any()
            )
        } returns mockk(relaxed = true)
        coEvery { mediaLocalDataSource.addAllMedia(any()) } just Runs
        coEvery { historyLocalDataSource.addSearchQuery(countryName, SearchType.Country) } just Runs
        coEvery { mediaLocalDataSource.getMediaByCountry(countryName) } returns emptyList()

        val result = repository.getMoviesByCountry(countryName)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `getMoviesByCountry should throw NoInternetConnectionException if no internet`() = runTest {
        val countryName = "France"

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName) } returns emptyList()
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        assertFailsWith<NoInternetConnectionException> {
            repository.getMoviesByCountry(countryName)
        }
    }

    @Test
    fun `getMoviesByCountry should throw NoDataForCountryException on exception`() = runTest {
        val countryName = "Spain"

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName) } throws RuntimeException("DB error")

        assertFailsWith<NoDataForCountryException> {
            repository.getMoviesByCountry(countryName)
        }
    }

    // getMediaByQuery Test Cases
    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByQuery should return cached media if not expired`() = runTest {
        val query = "Inception"
        val cachedMedia = listOf(
            MediaEntity(
                id = 1,
                searchQuery = query,
                imageUri = "image.jpg",
                title = "Inception",
                type = MediaTypeEntity.MOVIE,
                category = listOf(2),
                yearOfRelease = LocalDate(2010, 7, 16),
                rating = 8.8,
                searchType = SearchType.Query
            )
        )
        val validDate = Clock.System.now()
            .minus(30, DateTimeUnit.MINUTE)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(query, SearchType.Query) } returns SearchHistoryEntity(
            query,
           SearchType.Query, validDate
        )

        val result = repository.getMediaByQuery(query)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByQuery should fetch remotely if cache is expired`() = runTest {
        val query = "Matrix"
        val expiredDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val entity = MediaEntity(
            1,

            "img",
            "Old",
            MediaTypeEntity.MOVIE,
            listOf(1),
            LocalDate(2000, 1, 1),
            8.0
        , query, SearchType.Query)

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query) } returns listOf(entity) andThen emptyList()
        coEvery { historyLocalDataSource.getSearchHistoryQuery(query, SearchType.Query) } returns SearchHistoryEntity(
            query,
           SearchType.Query, expiredDate
        )
        coEvery { mediaLocalDataSource.clearAllMediaBySearchQuery(query, SearchType.Query) } just Runs
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { searchRemoteDataSource.searchMulti(query, language = any()) } returns mockk(
            relaxed = true
        )
        coEvery { mediaLocalDataSource.addAllMedia(any()) } just Runs
        coEvery { historyLocalDataSource.addSearchQuery(query, SearchType.Query) } just Runs

        val result = repository.getMediaByQuery(query)
        assertEquals(listOf(entity.toMedia()), result)
    }

    @Test
    fun `getMediaByQuery should throw NoInternetConnectionException if no internet`() = runTest {
        val query = "Titanic"

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query) } returns emptyList()
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        assertFailsWith<NoInternetConnectionException> {
            repository.getMediaByQuery(query)
        }
    }

    @Test
    fun `getMediaByQuery should throw NoDataForSearchException on error`() = runTest {
        val query = "Avatar"

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query) } throws RuntimeException("DB error")

        assertFailsWith<NoDataForSearchException> {
            repository.getMediaByQuery(query)
        }
    }

}
