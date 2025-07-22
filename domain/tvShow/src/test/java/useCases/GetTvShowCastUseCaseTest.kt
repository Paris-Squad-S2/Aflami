package useCases

import com.paris_2.domain.tvshow.repository.TvShowRepository
import com.paris_2.domain.tvshow.useCases.GetTvShowCastUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import testUtils.fakeTvShowCasts

class GetTvShowCastUseCaseTest {

    private lateinit var getTvShowCastUseCase: GetTvShowCastUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowCastUseCase = GetTvShowCastUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show cast from repository`() = runTest {
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns fakeTvShowCasts

        // Then
        val result = getTvShowCastUseCase(tvShowId)
        assertEquals(result, fakeTvShowCasts)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns emptyList()

        // Then
        val result = getTvShowCastUseCase(tvShowId)
        assertTrue(result.isEmpty())
    }
}