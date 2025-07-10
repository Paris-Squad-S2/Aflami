package com.datasource.local.search.datasource

import com.datasource.local.search.dao.MediaDao
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MediaLocalDataSourceImplTest {
    private lateinit var mediaLocalDataSourceImpl: MediaLocalDataSourceImpl
    private val mediaDao: MediaDao = mockk(relaxed = true)

    private lateinit var sampleMedia: MediaEntity

    @BeforeEach
    fun setUp() {
        mediaLocalDataSourceImpl = MediaLocalDataSourceImpl(mediaDao)
        sampleMedia = MediaEntity(
            id = 0,
            searchQuery = "aa",
            imageUri = "www.image.com",
            title = "batman",
            type = MediaTypeEntity.MOVIE,
            category = listOf("action", "adventure"),
            yearOfRelease = LocalDate.parse("2023-01-01"),
            rating = 4.5,
            country = "usa",
            actor = listOf("aaa", "bbb")
        )
    }

    @Test
    fun `getAllMedia should return media when getAll in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.getAllMedia() } returns listOf(sampleMedia)

            val result = mediaLocalDataSourceImpl.getAllMedia()

            assertEquals(sampleMedia.searchQuery, result[0].searchQuery)
        }

    @Test
    fun `addAllMedia should add AllMedia when add in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.addAllMedia(any()) } returns Unit

            mediaLocalDataSourceImpl.addAllMedia(listOf(sampleMedia))

            coVerify { mediaDao.addAllMedia(any()) }
        }

    @Test
    fun `getCachedMedia should get CachedMedia when get in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.getCachedMedia() } returns listOf(sampleMedia)

            val result = mediaLocalDataSourceImpl.getCachedMedia()

            assertEquals(sampleMedia, result)
        }

    @Test
    fun `getMediaByActor should get MediaByActor when get in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.getMediaByActor("aaa") } returns listOf(sampleMedia)

            val result = mediaLocalDataSourceImpl.getMediaByActor("aaa")

            assertEquals(sampleMedia.actor, result[0].actor)
        }

    @Test
    fun `getMediaByCountry should get MediaByCountry when get in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.getMediaByCountry("usa") } returns listOf(sampleMedia)

            val result = mediaLocalDataSourceImpl.getMediaByCountry("usa")

            assertEquals(sampleMedia.country, result[0].country)
        }

    @Test
    fun `getMediaByTitleQuery should get MediaByTitleQuery when get in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.getMediaByTitleQuery("batman") } returns listOf(sampleMedia)

            val result = mediaLocalDataSourceImpl.getMediaByTitleQuery("batman")

            assertEquals(sampleMedia.title, result[0].title)
        }

    @Test
    fun `clearAllMediaBySearchQuery should clear AllMediaBySearchQuery when clear in MediaDao called successfully`() =
        runTest {
            coEvery { mediaDao.clearAllMediaBySearchQuery("aa") } returns Unit

            mediaLocalDataSourceImpl.clearAllMediaBySearchQuery("aa")

            coVerify { mediaDao.clearAllMediaBySearchQuery("aa") }
        }

}