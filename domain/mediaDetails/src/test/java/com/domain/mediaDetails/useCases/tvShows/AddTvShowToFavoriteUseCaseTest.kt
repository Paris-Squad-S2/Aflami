package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddTvShowToFavoriteUseCaseTest {
    private lateinit var addTvShowToFavoriteUseCase: AddTvShowToFavoriteUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        addTvShowToFavoriteUseCase = AddTvShowToFavoriteUseCase(tvShowRepository)
    }

    @Test
    fun `should add tv show to favorite successfully`() = runTest {
        // Given
        val tvShowId = 1

        // when
        addTvShowToFavoriteUseCase(tvShowId)

        // Then
        coVerify {
            tvShowRepository.addTvShowToFavorite(tvShowId)
        }

    }

}