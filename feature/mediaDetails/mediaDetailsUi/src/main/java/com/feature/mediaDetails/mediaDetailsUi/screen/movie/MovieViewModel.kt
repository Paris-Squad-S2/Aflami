package com.feature.mediaDetails.mediaDetailsUi.screen.movie

import com.domain.mediaDetails.useCases.movie.AddMovieToFavoriteUseCase
import com.domain.mediaDetails.useCases.movie.AddMovieToListUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieDetailsUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieMediaUseCase
import com.domain.mediaDetails.useCases.movie.GetMovieReviewsUseCase
import com.domain.mediaDetails.useCases.movie.GetMoviesProductionCompaniesUseCase

class MovieViewModel(
    private val getMovieMediaUseCase: GetMovieMediaUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase : GetMovieCastUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getMoviesProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase,
    private val addMovieToFavoriteUseCase : AddMovieToFavoriteUseCase,
    private val getMovieReviewsUseCase : GetMovieReviewsUseCase
) {

}