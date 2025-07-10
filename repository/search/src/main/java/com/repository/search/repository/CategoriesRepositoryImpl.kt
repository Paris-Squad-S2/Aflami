package com.repository.search.repository

import com.domain.search.model.CategoryModel
import com.domain.search.repository.CategoriesRepository
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.Genre
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.entity.GenreEntity

class CategoriesRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val genresLocalDataSource: GenresLocalDataSource,
    private val genresRemoteDataSource: GenresRemoteDataSource,
) : CategoriesRepository {

    override suspend fun getAllCategories(): List<CategoryModel> {

        return try {
            val genres = genresLocalDataSource.getGenres()
            if (genres.isNotEmpty()) return genres.toCategories()

            if (networkConnectionChecker.isConnected.value){
                val remoteGenres = genresRemoteDataSource.getAllGenres().genres
                val genreEntities = remoteGenres.map { it.toEntity() }
                genresLocalDataSource.addGenres(genreEntities)
            }else{
                throw Exception()
            }
                genresLocalDataSource.getGenres().toCategories()
        } catch (e: Exception) {
            throw e
        }

    }

}


fun List<GenreEntity>.toCategories(): List<CategoryModel> {
    return this.map { it.toCategoryModel() }
}


fun GenreEntity.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = this.id.toInt(), // need to change
        name = this.name
    )
}

fun Genre.toEntity(): GenreEntity {
    return GenreEntity(
        id = this.id.toString(),
        name = this.name
    )
}