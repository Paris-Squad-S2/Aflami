package com.repository.media.repository

import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.toCategoryList
import com.repository.media.util.NetworkConnectionChecker
import com.repository.media.util.detectLanguage

class MoviesCategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
): MoviesCategoriesRepository {
    override suspend fun getMoviesCategories(): List<Category> {
        val language = detectLanguage()
        return safeCall(NoCategoriesFoundException()) {
            val genresDto = genresRemoteDataSource.getMoviesGenres(language)
            val categories = genresDto.toCategoryList()
            if (categories.isEmpty()) {
                throw NoCategoriesFoundException()
            }
            categories
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