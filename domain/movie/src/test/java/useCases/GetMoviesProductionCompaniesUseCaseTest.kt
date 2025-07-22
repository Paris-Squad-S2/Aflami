package useCases

import com.paris_2.domain.movie.repository.MovieRepository
import com.paris_2.domain.movie.useCases.GetMoviesProductionCompaniesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeProductionCompanies
import kotlin.test.assertEquals

class GetMoviesProductionCompaniesUseCaseTest {
    private lateinit var getMoviesProductionCompaniesUseCase: GetMoviesProductionCompaniesUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMoviesProductionCompaniesUseCase = GetMoviesProductionCompaniesUseCase(movieRepository)
    }

    @Test
    fun `should return movie production from repository`() = runTest {
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getCompanyProducts(movieId) } returns fakeProductionCompanies

        // Then
        val result = getMoviesProductionCompaniesUseCase(movieId)
        assertEquals(result, fakeProductionCompanies)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getCompanyProducts(movieId) } returns emptyList()

        // Then
        val result = getMoviesProductionCompaniesUseCase(movieId)
        assertEquals(result, emptyList())
    }

}