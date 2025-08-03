package com.repository.media.repository

import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.repository.CategoriesRepository
import com.repository.media.datasource.local.GenresLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.mapper.search.toCategories
import com.repository.media.mapper.search.toEntity
import com.repository.media.util.NetworkConnectionChecker
import java.util.Locale

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresLocalDataSource: GenresLocalDataSource,
    private val genresRemoteDataSource: GenresRemoteDataSource,
) : CategoriesRepository {

    override suspend fun getAllCategories(): List<Category> {
        val language = Locale.getDefault().language
        return try {
            val genres = genresLocalDataSource.getGenres(language)
            if (genres.isNotEmpty()) return genres.toCategories()

            if (!networkConnectionChecker.isConnected.value) {
                throw NoInternetConnectionException()
            }

            val remoteGenres = genresRemoteDataSource.getMoviesGenres(language).genreDto
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
