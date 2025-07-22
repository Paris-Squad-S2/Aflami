package com.repository.home.repository

import com.domain.home.exception.NoCategoriesFoundException
import com.domain.home.model.Category
import com.domain.home.repository.CategoriesRepository
import com.repository.home.datasource.remote.GenresRemoteDataSource
import com.repository.home.mapper.toCategoryList
import java.util.Locale

class CategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource
): CategoriesRepository {
    override suspend fun getMoviesCategories(): List<Category> {
        val language = Locale.getDefault().language
        return try {
            val genresDto = genresRemoteDataSource.getMoviesGenres(language)
            val categories = genresDto.toCategoryList()
            if (categories.isEmpty()) {
                throw NoCategoriesFoundException()
            }
            categories
        } catch (e: Exception) {
            throw NoCategoriesFoundException()
        }
    }
}