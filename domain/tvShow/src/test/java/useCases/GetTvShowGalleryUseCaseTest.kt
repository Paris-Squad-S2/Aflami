package useCases

import com.paris_2.domain.tvshow.repository.TvShowRepository
import com.paris_2.domain.tvshow.useCases.GetTvShowGalleryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import testUtils.fakeTvShowGallery

class GetTvShowGalleryUseCaseTest {

    private lateinit var getTvShowGalleryUseCase: GetTvShowGalleryUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowGalleryUseCase = GetTvShowGalleryUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show gallery from repository`() = runTest {
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowGallery(tvShowId) } returns fakeTvShowGallery

        // Then
        val result = getTvShowGalleryUseCase(tvShowId)
        assertEquals(result, fakeTvShowGallery)

    }

}