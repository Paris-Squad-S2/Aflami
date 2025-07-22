package useCases

import com.paris_2.domain.movie.repository.MovieRepository
import com.paris_2.domain.movie.useCases.AddMovieToFavoriteUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddMovieToFavoriteUseCaseTest {
    private lateinit var addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        addMovieToFavoriteUseCase = AddMovieToFavoriteUseCase(movieRepository)
    }

    @Test
    fun `should add movie to favorite successfully`() = runTest {
        // Given
        val movieId = 1

        // when
         addMovieToFavoriteUseCase(movieId)

        // Then
        coVerify {
            movieRepository.addMovieToFavorite(movieId)
        }

    }

}