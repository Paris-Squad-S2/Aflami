package com.repository.search.repository

import com.domain.search.model.CategoryModel
import com.domain.search.repository.CategoriesRepository
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.exception.NoCategoriesFoundException
import com.repository.search.exception.NoInternetConnectionException
import com.repository.search.mapper.toCategories
import com.repository.search.mapper.toEntity

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresLocalDataSource: GenresLocalDataSource,
    private val genresRemoteDataSource: GenresRemoteDataSource,
) : CategoriesRepository {

    override suspend fun getAllCategories(language: String): List<CategoryModel> {
        return try {
            val genres = genresLocalDataSource.getGenres(language)
            if (genres.isNotEmpty()) return genres.toCategories()

            if (!networkConnectionChecker.isConnected.value) {
                throw NoInternetConnectionException()
            }

            val remoteGenres = genresRemoteDataSource.getAllGenres(language).genreDto
            val genreEntities = remoteGenres?.map { it.toEntity(language) }

            if (genreEntities != null) {
                genresLocalDataSource.addGenres(genreEntities)
            }

            genresLocalDataSource.getGenres(language).toCategories()

        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (e: Exception) {
            throw NoCategoriesFoundException()
        }
    }

}

