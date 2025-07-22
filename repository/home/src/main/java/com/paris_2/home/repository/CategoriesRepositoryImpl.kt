package com.paris_2.home.repository

import com.domain.home.exception.NoCategoriesFoundException
import com.domain.home.repository.CategoriesRepository
import com.paris_2.home.datasource.remote.GenresRemoteDataSource
import com.paris_2.home.mapper.toCategoryList
import com.domain.home.model.Category
import java.util.Locale

class CategoriesRepositoryImpl(
    private val genresRemoteDataSource: GenresRemoteDataSource
): CategoriesRepository {
    override suspend fun getAllCategories(): List<Category> {
        val language = Locale.getDefault().language
        return try {
            val genresDto = genresRemoteDataSource.getAllGenres(language)
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