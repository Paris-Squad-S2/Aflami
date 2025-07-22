package com.datasource.local.datasource


import com.datasource.local.dao.TvShowGalleryDao
import com.google.common.truth.Truth.assertThat
import com.repository.model.local.GalleryEntity
import com.repository.model.local.ImageEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test


class GalleryLocalDataSourceImpTest {

    private lateinit var galleryLocalDataSource: TvShowGalleryLocalDataSourceImp
    private val galleryDao: TvShowGalleryDao = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        galleryLocalDataSource = TvShowGalleryLocalDataSourceImp(galleryDao)
    }


    @Test
    fun `should add gallery when addGallery is called`() = runTest {
        // Given
        galleryLocalDataSource.addGallery(sampleGallery)

        // When&Then
        coVerify(exactly = 1) { galleryDao.addGallery(sampleGallery) }
    }

    @Test
    fun `should get gallery by movie id when getGalleryByMovieId is called`() = runTest {
        //Given
        val movieId = 1
        coEvery { galleryDao.getGalleryByTvShowId(movieId) } returns sampleGallery

        //When
        val result = galleryLocalDataSource.getGalleryByTvShowId(movieId)

        //Then
        assertThat(result).isEqualTo(sampleGallery)
    }

    @Test
    fun `should verify getGalleryByTvShowId is called once for movie id`() = runTest {
        // GIVEN
        val movieId = 1
        coEvery { galleryDao.getGalleryByTvShowId(movieId) } returns sampleGallery

        // WHEN
        galleryLocalDataSource.getGalleryByTvShowId(movieId)

        // THEN
        coVerify(exactly = 1) { galleryDao.getGalleryByTvShowId(movieId) }
    }

    @Test
    fun `should return null when getGalleryByMovieId returns null`() = runTest {
        //Given
        val movieId = 2
        coEvery { galleryDao.getGalleryByTvShowId(movieId) } returns null

        //When
        val result = galleryLocalDataSource.getGalleryByTvShowId(movieId)

        //Then
        assertThat(result).isNull()
    }

    @Test
    fun `should verify getGalleryByTvShowId is called once for movie id when gallery is null`() = runTest {
        // GIVEN
        val movieId = 2
        coEvery { galleryDao.getGalleryByTvShowId(movieId) } returns null

        // WHEN
        galleryLocalDataSource.getGalleryByTvShowId(movieId)

        // THEN
        coVerify(exactly = 1) { galleryDao.getGalleryByTvShowId(movieId) }
    }

    private companion object {
        val sampleGallery = GalleryEntity(
            id = 1,
            tvShowId = 1,
            images = listOf(
                ImageEntity(
                    id = 10,
                    url = "uri"
                ),
                ImageEntity(
                    id = 20,
                    url = "uri"
                ),
            )
        )
    }

}