//package com.repository.search
//
//import com.repository.media.datasource.remote.GenresRemoteDataSource
//import com.repository.media.dto.GenresDto
//import com.repository.search.service.implementation.RetrofitGenresApiServices
//import javax.inject.Inject
//
//class GenresRemoteDataSourceImp @Inject constructor(private val retrofitGenresApiServices: RetrofitGenresApiServices) :
//    GenresRemoteDataSource {
//    override suspend fun getMoviesGenres(language: String): GenresDto {
//        val movieDto = retrofitGenresApiServices.getAllGenresMovie(language)
//        val tvShowDto = retrofitGenresApiServices.getAllGenresTvShow(language)
//        return movieDto.copy(genreDto = movieDto.genreDto.orEmpty() + tvShowDto.genreDto.orEmpty())
//    }
//}
