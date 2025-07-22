package useCases

import com.paris_2.domain.movie.repository.MovieRepository
import com.paris_2.domain.movie.useCases.GetMovieReviewsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeMovieReviews
import kotlin.test.assertEquals

class GetMovieReviewsUseCaseTest {
    private lateinit var getMovieReviewsUseCase: GetMovieReviewsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieReviewsUseCase = GetMovieReviewsUseCase(movieRepository)
    }

    @Test
    fun `should return movie reviews from repository`() = runTest {
        // Given
        val movieId = 1
        val page = 1

        // when
        coEvery { movieRepository.getMovieReview(movieId,page) } returns fakeMovieReviews

        // Then
        val result = getMovieReviewsUseCase(movieId,page)
        assertEquals(result, fakeMovieReviews)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        val movieId = 1
        val page = 1
        // when
        coEvery { movieRepository.getMovieReview(movieId,page) } returns emptyList()

        // Then
        val result = getMovieReviewsUseCase(movieId,page)
        assertEquals(result, emptyList())
    }

}