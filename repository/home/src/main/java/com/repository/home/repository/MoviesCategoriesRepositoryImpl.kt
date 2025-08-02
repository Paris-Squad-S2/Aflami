package com.repository.home.repository

import com.domain.media.exception.AflamiException
import com.domain.media.exception.NoCategoriesFoundException
import com.domain.media.exception.NoInternetConnectionException
import com.domain.media.entity.Category
import com.domain.media.repository.MoviesCategoriesRepository
import com.repository.home.datasource.remote.GenresRemoteDataSource
import com.repository.home.mapper.toCategoryList
import com.repository.home.util.NetworkConnectionChecker
import com.repository.home.util.detectLanguage

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