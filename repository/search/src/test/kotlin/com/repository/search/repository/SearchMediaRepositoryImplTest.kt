package com.repository.search.repository

import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import com.domain.search.exception.NoDataForActorException
import com.domain.search.exception.NoDataForCountryException
import com.domain.search.exception.NoDataForSearchException
import com.domain.search.exception.NoInternetConnectionException
import com.repository.search.dto.ResultDto
import com.repository.search.dto.SearchDto
import com.repository.search.mapper.toMedia
import com.repository.search.mapper.toMediaEntitiesForActors
import com.repository.search.mapper.toMedias
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
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
    private val language = "en"

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
                searchType = SearchType.Actor,
                language = language,
                page = 1
            )
        )
        val oldDate = Clock.System.now()
            .minus(30, DateTimeUnit.MINUTE)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByActor(actorName, page = 1, language = language) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor) } returns SearchHistoryEntity(
            actorName,
           SearchType.Query, oldDate
        )

        val result = repository.getMediaByActor(actorName, page = 1)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @Test
    fun `getMediaByActor should fetch from remote and save to local when online and cache expired`() = runTest {
        val actorName = "Tom"
        val page = 1

        coEvery { mediaLocalDataSource.getMediaByActor(actorName, page,language) } returns emptyList()
        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor) } returns null

        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)

        val mockDto = SearchDto(
            page = 1,
            results =listOf(
                ResultDto(
                    title = "madrid"
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        val mockEntities = listOf(
            MediaEntity(
                id = 1,
                imageUri = "",
                title = "madrid",
                type = MediaTypeEntity.MOVIE,
                category = listOf(),
                yearOfRelease = LocalDate(2022, 1, 1),
                rating = 2.2,
                searchQuery = actorName,
                searchType = SearchType.Actor,
                page = 1,
                language = "en"
            )
        )
        coEvery { searchRemoteDataSource.searchPerson(actorName, page = page, language = any()) } returns mockDto
        mockkStatic("com.repository.search.mapper.SearchMediaMapperKt")
        every { mockDto.toMediaEntitiesForActors(actorName, page,language) } returns mockEntities

        coEvery { historyLocalDataSource.addSearchQuery(actorName, SearchType.Actor) } just Runs
        coEvery { mediaLocalDataSource.addAllMedia(mockEntities) } just Runs
        coEvery { mediaLocalDataSource.getMediaByActor(actorName, page,language) } returns mockEntities

        val result = repository.getMediaByActor(actorName, page)
        advanceUntilIdle()

        assertEquals("madrid", result.first().title)

        coVerify{
            searchRemoteDataSource.searchPerson(actorName, page = page, language = any())
            mediaLocalDataSource.addAllMedia(mockEntities)
            historyLocalDataSource.addSearchQuery(actorName, SearchType.Actor)
        }
    }

    @Test
    fun `getMediaByActor should throw NoInternetConnectionException if no internet`() = runTest {
        val actorName = "Will Smith"
        val page = 1

        coEvery { mediaLocalDataSource.getMediaByActor(actorName, page,language) } returns emptyList()
        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor) } returns null
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        val exception = assertFailsWith<NoInternetConnectionException> {
            repository.getMediaByActor(actorName, page)
        }

        assertEquals("Please connect your device to the internet", exception.message)
    }

    @Test
    fun `getMediaByActor should throw NoDataForActorException on error`() = runTest {
        val actorName = "Jim Carrey"
        val page = 1

        coEvery { mediaLocalDataSource.getMediaByActor(actorName, page,language) } throws RuntimeException()

        assertFailsWith<NoDataForActorException> {
            repository.getMediaByActor(actorName, page)
        }
    }

    // getMoviesByCountry Test Cases

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMoviesByCountry should return cached media if not expired`() = runTest {
        val countryName = "USA"
        val page = 1
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
                searchType = SearchType.Country,
                page = 1,
                language = language
            )
        )
        val validDate = Clock.System.now()
            .minus(30, DateTimeUnit.MINUTE)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName, page,language) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns SearchHistoryEntity(
            countryName,
           SearchType.Country, validDate
        )

        val result = repository.getMoviesByCountry(countryName, page)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMoviesByCountry should fetch remotely if cache expired`() = runTest {
        val countryName = "Egypt"
        val page = 1
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
                searchType = SearchType.Country,
                page = 1,
                language = language
            )
        )

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName, page,language) } returns oldMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns SearchHistoryEntity(
            countryName,
           SearchType.Country, expiredDate
        )
        coEvery { mediaLocalDataSource.clearAllMediaBySearchQuery(countryName, SearchType.Country) } just Runs
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery {
            searchRemoteDataSource.searchCountryCode(
                query = countryName,
                page = page,
                countryCode = countryName,
                language = any()
            )
        } returns mockk(relaxed = true)
        coEvery { mediaLocalDataSource.addAllMedia(any()) } just Runs
        coEvery { historyLocalDataSource.addSearchQuery(countryName, SearchType.Country) } just Runs
        coEvery { mediaLocalDataSource.getMediaByCountry(countryName, page,language) } returns emptyList()

        val result = repository.getMoviesByCountry(countryName, page)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `getMoviesByCountry should throw NoInternetConnectionException if no internet`() = runTest {
        val countryName = "France"
        val page = 1

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName, page,language) } returns emptyList()
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns null
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        val exception = assertFailsWith<NoInternetConnectionException> {
            repository.getMoviesByCountry(countryName, page)
        }

        assertEquals("Please connect your device to the internet", exception.message)
    }

    @Test
    fun `getMoviesByCountry should throw NoDataForCountryException on exception`() = runTest {
        val countryName = "Spain"
        val page = 1

        coEvery { mediaLocalDataSource.getMediaByCountry(countryName, page,language) } throws RuntimeException("DB error")

        assertFailsWith<NoDataForCountryException> {
            repository.getMoviesByCountry(countryName, page)
        }
    }

    // getMediaByQuery Test Cases
    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByQuery should return cached media if not expired`() = runTest {
        val query = "Inception"
        val page = 1
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
                searchType = SearchType.Query,
                page = 1,
                language = language
            )
        )
        val validDate = Clock.System.now()
            .minus(30, DateTimeUnit.MINUTE)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query, page,language) } returns cachedMedia
        coEvery { historyLocalDataSource.getSearchHistoryQuery(query, SearchType.Query) } returns SearchHistoryEntity(
            query,
           SearchType.Query, validDate
        )

        val result = repository.getMediaByQuery(query, page)
        assertEquals(cachedMedia.toMedias(), result)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMediaByQuery should fetch remotely if cache is expired`() = runTest {
        val page = 1
        val query = "Matrix"
        val expiredDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val entity = MediaEntity(
            1,
            "img",
            "Old",
            MediaTypeEntity.MOVIE,
            listOf(1),
            LocalDate(2000, 1, 1),
            8.0, query, SearchType.Query,
            page,
            language,
        )

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query, page,language) } returns listOf(entity) andThen emptyList()
        coEvery { historyLocalDataSource.getSearchHistoryQuery(query, SearchType.Query) } returns SearchHistoryEntity(
            query,
           SearchType.Query, expiredDate
        )
        coEvery { mediaLocalDataSource.clearAllMediaBySearchQuery(query, SearchType.Query) } just Runs
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { searchRemoteDataSource.searchMulti(query, page = page, language = any()) } returns mockk(
            relaxed = true
        )
        coEvery { mediaLocalDataSource.addAllMedia(any()) } just Runs
        coEvery { historyLocalDataSource.addSearchQuery(query, SearchType.Query) } just Runs

        val result = repository.getMediaByQuery(query, page)
        assertEquals(listOf(entity.toMedia()), result)
    }

    @Test
    fun `getMediaByQuery should throw NoInternetConnectionException if no internet`() = runTest {
        val page = 1
        val query = "Titanic"

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query, page,language) } returns emptyList()
        coEvery { historyLocalDataSource.getSearchHistoryQuery(query, SearchType.Query) } returns null
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        val exception = assertFailsWith<NoInternetConnectionException> {
            repository.getMediaByQuery(query, page)
        }

        assertEquals("Please connect your device to the internet", exception.message)
    }

    @Test
    fun `getMediaByQuery should throw NoDataForSearchException on error`() = runTest {
        val page = 1
        val query = "Avatar"

        coEvery { mediaLocalDataSource.getMediaByTitleQuery(query, page,language) } throws RuntimeException("DB error")

        assertFailsWith<NoDataForSearchException> {
            repository.getMediaByQuery(query, page)
        }
    }

}
