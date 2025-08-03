package com.repository.search.repository

import com.paris_2.domain.media.exception.NoMediaForActorException
import com.paris_2.domain.media.exception.NoMediaForCountryException
import com.paris_2.domain.media.exception.NoMediaForSearchException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.mapper.toMedia
import com.repository.search.util.NetworkConnectionChecker
import com.repository.search.util.detectLanguage
class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchMediaRepository {

    override suspend fun getMediaByActor(actorName: String,page:Int): List<Media> {
        val language = detectLanguage()
        return safeCall(NoMediaForActorException()){
            val remoteDto = searchRemoteDataSource.searchPerson(query = actorName, language = language, page = page)
            remoteDto.results
            ?.flatMap { resultDto ->
                resultDto.knownForDTO?.mapNotNull { it.toMedia() } ?: emptyList()
            } ?: emptyList()
        }
    }

    override suspend fun getMoviesByCountry(countryName: String,page: Int): List<Media> {
        val language = detectLanguage()
        return safeCall(NoMediaForCountryException()){
            val remoteDto = searchRemoteDataSource.searchCountryCode(countryCode = countryName, language = language, page = page)
            remoteDto.results?.mapNotNull { it.toMedia() } ?: emptyList()
        }
    }
    override suspend fun getMediaByQuery(query: String,page: Int): List<Media> {
        val language = detectLanguage()
        return safeCall(NoMediaForSearchException()){
            val remoteDto = searchRemoteDataSource.searchMulti(query = query, language = language, page = page)
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