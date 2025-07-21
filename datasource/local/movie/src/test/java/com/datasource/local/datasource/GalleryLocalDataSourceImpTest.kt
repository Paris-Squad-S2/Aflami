package com.datasource.local.datasource


import com.datasource.local.dao.MovieGalleryDao
import com.google.common.truth.Truth.assertThat
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.ImageEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GalleryLocalDataSourceImpTest {
    private lateinit var galleryLocalDataSource: MovieGalleryLocalDataSourceImp
    private val galleryDao: MovieGalleryDao = mockk(relaxed = true)
    private lateinit var sampleGallery: GalleryEntity

    @BeforeEach
    fun setUp() {
        galleryLocalDataSource = MovieGalleryLocalDataSourceImp(galleryDao)
        sampleGallery = GalleryEntity(
            id = 1,
            movieId = 1,
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

        galleryLocalDataSource.addGallery(sampleGallery)


        coVerify(exactly = 1) { galleryDao.addGallery(sampleGallery) }
    }

    @Test
    fun `getGalleryByMovieId should return gallery when DAO returns gallery`() = runTest {
        val movieId = 1
        coEvery { galleryDao.getGallery(movieId) } returns sampleGallery

        val result = galleryLocalDataSource.getGalleryByMovieId(movieId)

        assertThat(result).isEqualTo(sampleGallery)
    }

    @Test
    fun `getGalleryByMovieId should call DAO method exactly once`() = runTest {
        val movieId = 1
        coEvery { galleryDao.getGallery(movieId) } returns sampleGallery

        galleryLocalDataSource.getGalleryByMovieId(movieId)

        coVerify(exactly = 1) { galleryDao.getGallery(movieId) }
    }

    @Test
    fun `getGalleryByMovieId should return null when DAO returns null`() = runTest {
        val movieId = 2
        coEvery { galleryDao.getGallery(movieId) } returns null

        val result = galleryLocalDataSource.getGalleryByMovieId(movieId)

        assertThat(result).isNull()
    }

    @Test
    fun `getGalleryByMovieId should call DAO once when returning null`() = runTest {
        val movieId = 2
        coEvery { galleryDao.getGallery(movieId) } returns null

        galleryLocalDataSource.getGalleryByMovieId(movieId)

        coVerify(exactly = 1) { galleryDao.getGallery(movieId) }
    }

}