package useCases

import com.paris_2.domain.movie.repository.MovieRepository
import com.paris_2.domain.movie.useCases.GetMovieCastUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import testUtils.fakeMovieCasts
import kotlin.test.Test
import kotlin.test.assertEquals


class GetMovieCastUseCaseTest {
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieCastUseCase = GetMovieCastUseCase(movieRepository)
    }

    @Test
    fun `should return movie cast from repository`() = runTest {
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getMovieCast(movieId) } returns fakeMovieCasts

        // Then
        val result = getMovieCastUseCase(movieId)
        assertEquals(result, fakeMovieCasts)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getMovieCast(movieId) } returns emptyList()

        // Then
        val result = getMovieCastUseCase(movieId)
        assertEquals(result, emptyList())
    }
}