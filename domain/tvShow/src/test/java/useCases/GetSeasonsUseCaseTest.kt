package useCases

import com.paris_2.domain.tvshow.repository.TvShowRepository
import com.paris_2.domain.tvshow.useCases.GetSeasonDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeSeasons
import kotlin.test.assertEquals

class GetSeasonsUseCaseTest {

    private lateinit var getSeasonDetailsUseCase: GetSeasonDetailsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getSeasonDetailsUseCase = GetSeasonDetailsUseCase(tvShowRepository)
    }

    @Test
    fun `should return season details from repository`() = runTest {
        // Given
        val tvShowId = 1
        val seasonNumber = 1

        // when
        coEvery { tvShowRepository.getSeasonDetails(tvShowId, seasonNumber) } returns fakeSeasons.first()

        // Then
        val result = getSeasonDetailsUseCase(tvShowId,seasonNumber)
        assertEquals(result, fakeSeasons.first())

    }

}