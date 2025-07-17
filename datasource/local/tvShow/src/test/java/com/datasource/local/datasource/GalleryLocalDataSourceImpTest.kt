package com.datasource.local.datasource

import com.datasource.local.dao.GalleryDao
import com.google.common.truth.Truth.assertThat
import com.repository.entity.GalleryEntity
import com.repository.entity.ImageEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test


class GalleryLocalDataSourceImpTest {

    private lateinit var galleryLocalDataSource: GalleryLocalDataSourceImp
    private val galleryDao: GalleryDao = mockk(relaxed = true)
    private lateinit var sampleGallery: GalleryEntity

    @BeforeEach
    fun setUp() {
        galleryLocalDataSource = GalleryLocalDataSourceImp(galleryDao)
        sampleGallery = GalleryEntity(
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


    @Test
    fun `should add gallery when addGallery is called`() = runTest {
        // Act
        galleryLocalDataSource.addGallery(sampleGallery)

        // Assert
        coVerify(exactly = 1) { galleryDao.addGallery(sampleGallery) }
    }

    @Test
    fun `should get gallery by movie id when getGalleryByMovieId is called`() = runTest {
        val movieId = 1
        coEvery { galleryDao.getGalleryByTvShowId(movieId) } returns sampleGallery

        val result = galleryLocalDataSource.getGalleryByTvShowId(movieId)

        coVerify(exactly = 1) { galleryDao.getGalleryByTvShowId(movieId) }
        assertThat(result).isEqualTo(sampleGallery)
    }


    @Test
    fun `should return null when getGalleryByMovieId returns null`() = runTest {
        val movieId = 2
        coEvery { galleryDao.getGalleryByTvShowId(movieId) } returns null

        val result = galleryLocalDataSource.getGalleryByTvShowId(movieId)

        coVerify(exactly = 1) { galleryDao.getGalleryByTvShowId(movieId) }
        assertThat(result).isNull()

    }

}