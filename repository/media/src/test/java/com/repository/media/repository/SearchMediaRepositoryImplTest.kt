package com.repository.media.repository

import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.exception.NoMediaForActorException
import com.paris_2.domain.media.exception.NoMediaForCountryException
import com.paris_2.domain.media.exception.NoMediaForSearchException
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.dto.search.KnownForDto
import com.repository.media.dto.search.ResultDto
import com.repository.media.dto.search.SearchDto
import com.repository.media.entity.SearchHistoryEntity
import com.repository.media.entity.SearchType
import com.repository.media.util.NetworkConnectionChecker
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
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
    private val searchRemoteDataSource = mockk<SearchRemoteDataSource>()
    private val historyLocalDataSource = mockk<HistoryLocalDataSource>()
    private val settingLocalDataSource = mockk<SettingLocalDataSource>()

    @BeforeEach
    fun setup() {
        repository = SearchMediaRepositoryImpl(
            networkConnectionChecker,
            searchRemoteDataSource,
            historyLocalDataSource,
            settingLocalDataSource
        )
    }

    // getMediaByActor Test Cases

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getMediaByActor should fetch from remote when online`() = runTest {
        val actorName = "Tom"
        val page = 1

        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor) } returns null
        every { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")

        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        val knownForDto = KnownForDto(
            id = 1,
            title = "madrid",
            mediaType = "movie",
            releaseDate = "2022-01-01",
            voteAverage = 8.0,
            genreIds = listOf(1, 2)
        )

        val resultDto = ResultDto(knownForDTO = listOf(knownForDto))

        val mockDto = SearchDto(
            page = 1,
            results = listOf(resultDto),
            totalPages = 1,
            totalResults = 1
        )

        coEvery { searchRemoteDataSource.searchPerson(actorName, page = page, language = any()) } returns mockDto

        coEvery { historyLocalDataSource.addSearchQuery(actorName, SearchType.Actor) } just Runs

        val result = repository.getMediaByActor(actorName, page)
        advanceUntilIdle()
        assertEquals("madrid", result.first().title)

        coVerify{
            searchRemoteDataSource.searchPerson(actorName, page = page, language = any())
            historyLocalDataSource.addSearchQuery(actorName, SearchType.Actor)
        }
    }

    @Test
    fun `getMediaByActor should throw NoInternetConnectionException if no internet`() = runTest {
        val actorName = "Will Smith"
        val page = 1

        coEvery { historyLocalDataSource.getSearchHistoryQuery(actorName, SearchType.Actor) } returns null
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)
        every { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")

        val exception = assertFailsWith<NoInternetConnectionException> {
            repository.getMediaByActor(actorName, page)
        }
        assertEquals("No internet connection", exception.message)
    }
    @Test
    fun `getMediaByActor should throw NoDataForActorException on error`() = runTest {
        // Arrange
        val actorName = "Jim Carrey"
        val page = 1

        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")

        coEvery {
            searchRemoteDataSource.searchPerson(
                actorName,
                page = page,
                language = any()
            )
        } throws Exception("Some error")

        assertFailsWith<NoMediaForActorException> {
            repository.getMediaByActor(actorName, page)
        }
    }

    // getMoviesByCountry Test Cases
    @OptIn(ExperimentalTime::class)
    @Test
    fun `getMoviesByCountry should fetch remotely`() = runTest {
        val countryName = "Egypt"
        val page = 1
        val expiredDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns SearchHistoryEntity(
            countryName,
            SearchType.Country, expiredDate
        )
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery {
            searchRemoteDataSource.searchCountryCode(
                page = page,
                countryCode = countryName,
                language = any()
            )
        } returns mockk(relaxed = true)
        coEvery { historyLocalDataSource.addSearchQuery(countryName, SearchType.Country) } just Runs

        val result = repository.getMoviesByCountry(countryName, page)
        assertEquals(emptyList(), result)
    }
    @Test
    fun `getMoviesByCountry should throw NoInternetConnectionException if no internet`() = runTest {
        val countryName = "France"
        val page = 1

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { historyLocalDataSource.getSearchHistoryQuery(countryName, SearchType.Country) } returns null
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        val exception = assertFailsWith<NoInternetConnectionException> {
            repository.getMoviesByCountry(countryName, page)
        }
        assertEquals("No internet connection", exception.message)
    }

    @Test
    fun `getMoviesByCountry should throw NoDataForCountryException on exception`() = runTest {
        val countryName = "Spain"
        val page = 1
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            searchRemoteDataSource.searchCountryCode(
                countryCode = countryName,
                language = any(),
                page = page
            )
        } throws NoMediaForCountryException()

        assertFailsWith<NoMediaForCountryException> {
            repository.getMoviesByCountry(countryName, page)
        }
    }

    // getMediaByQuery Test Cases

    @Test
    fun `getMediaByQuery should throw NoInternetConnectionException if no internet`() = runTest {
        val page = 1
        val query = "Titanic"
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { historyLocalDataSource.getSearchHistoryQuery(query, SearchType.Query) } returns null
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        val exception = assertFailsWith<NoInternetConnectionException> {
            repository.getMediaByQuery(query, page)
        }
        assertEquals("No internet connection", exception.message)
    }

    @Test
    fun `getMediaByQuery should throw NoDataForSearchException on error`() = runTest {
        val page = 1
        val query = "Avatar"

        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            searchRemoteDataSource.searchMulti(
                query = query,
                language = any(),
                page = page
            )
        } throws NoMediaForSearchException()

        assertFailsWith<NoMediaForSearchException> {
            repository.getMediaByQuery(query, page)
        }
    }

}
