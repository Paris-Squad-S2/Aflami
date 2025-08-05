package com.repository.media.repository

import com.paris_2.domain.media.exception.NoMediaForActorException
import com.paris_2.domain.media.exception.NoMediaForCountryException
import com.paris_2.domain.media.exception.NoMediaForSearchException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.detectLanguage
import com.repository.media.entity.SearchType
import com.repository.media.mapper.search.toMedia

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchHistoryLocalDataSource: HistoryLocalDataSource
) : SearchMediaRepository {

    override suspend fun getMediaByActor(actorName: String, page: Int): List<Media> {
        val language = detectLanguage()
        return safeCall(NoMediaForActorException()) {
            val remoteDto = searchRemoteDataSource.searchPerson(
                query = actorName,
                language = language,
                page = page
            )
            searchHistoryLocalDataSource.addSearchQuery(
                title = actorName,
                searchType = SearchType.Actor
            )
            remoteDto.results
                ?.flatMap { resultDto ->
                    resultDto.knownForDTO?.mapNotNull { it.toMedia() } ?: emptyList()
                } ?: emptyList()
        }
    }

    override suspend fun getMoviesByCountry(countryName: String, page: Int): List<Media> {
        val language = detectLanguage()
        return safeCall(NoMediaForCountryException()) {
            val remoteDto = searchRemoteDataSource.searchCountryCode(
                countryCode = countryName,
                language = language,
                page = page
            )
            searchHistoryLocalDataSource.addSearchQuery(
                title = countryName,
                searchType = SearchType.Country
            )
            remoteDto.results?.mapNotNull {
                it.toMedia()
            } ?: emptyList()
        }
    }

    override suspend fun getMediaByQuery(query: String, page: Int): List<Media> {
        val language = detectLanguage()
        return safeCall(NoMediaForSearchException()) {
            val remoteDto = searchRemoteDataSource.searchMulti(
                query = query,
                language = language,
                page = page
            )
            searchHistoryLocalDataSource.addSearchQuery(
                title = query,
                searchType = SearchType.Query
            )
            remoteDto.results?.mapNotNull { it.toMedia() } ?: emptyList()
        }
    }
    private suspend fun <T> safeCall(exception: AflamiException, call: suspend () -> T): T {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return try {
            call()
        } catch (e: AflamiException) {
            throw e
        } catch (_: Exception) {
            throw exception
        }
    }

}
