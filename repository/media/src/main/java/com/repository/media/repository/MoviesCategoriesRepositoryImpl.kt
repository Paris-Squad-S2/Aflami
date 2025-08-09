package com.repository.media.repository

import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.search.toGenresList
import com.repository.media.util.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class MoviesCategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val languageLocalDataSourceRepository: LanguageLocalDataSourceRepository
): MoviesCategoriesRepository {
    override suspend fun getMoviesCategories(): List<Genre> {
        val language = languageLocalDataSourceRepository.getLanguage().first()
        return safeCall(NoCategoriesFoundException()) {
            val genresDto = genresRemoteDataSource.getMoviesGenres(language)
            val genres = genresDto.toGenresList()
            if (genres.isEmpty()) {
                throw NoCategoriesFoundException()
            }
            genres
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