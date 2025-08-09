package com.datasource.local.datasource

import androidx.work.WorkManager
import com.datasource.local.dao.TvShowDao
import com.google.common.truth.Truth.assertThat
import com.repository.model.local.CastEntity
import com.repository.model.local.EpisodeEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.GenreEntity
import com.repository.model.local.ImageEntity
import com.repository.model.local.ProductionCompanyEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class TvShowLocalDataSourceImpTest {
    private lateinit var tvShowLocalDataSourceImp: TvShowLocalDataSourceImp
    private var workManager: WorkManager = mockk(relaxed = true)
    private lateinit var tvShowDao: TvShowDao

    @BeforeEach
    fun setUp() {
        tvShowDao = mockk(relaxed = true)
        tvShowLocalDataSourceImp = TvShowLocalDataSourceImp(workManager, tvShowDao)

    }

    @Nested
    inner class TvShowTests {
        @Test
        fun `addTvShow should add tv show when addTvShow in TvShowDao is called`() = runTest {
            //Given
            tvShowLocalDataSourceImp.addTvShow(sampleTvShow)

            //When&Then
            coVerify(exactly = 1) { tvShowDao.addTvShow(sampleTvShow) }
        }

        @Test
        fun `getTvShowId should call getTvShowById on DAO and return its result`() = runTest {
            //Given
            val tvShowId = 1
            coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns sampleTvShow

            //When
            val result = tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

            //Then
            assertEquals(sampleTvShow, result)
        }

        @Test
        fun `getTvShowId should verify DAO is called with correct ID when it returns TvShowEntity`() =
            runTest {
                //Given
                val tvShowId = 1
                coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns sampleTvShow

                //When
                tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

                //Then
                coVerify(exactly = 1) { tvShowDao.getTvShowById(tvShowId, language) }
            }

        @Test
        fun `getTvShowId should return null when DAO returns null`() = runTest {
            //Given
            val tvShowId = 2
            coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns null

            //When
            val result = tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

            //Then
            assertNull(result)

        }

        @Test
        fun `getTvShowId should verify DAO is called with correct ID when it returns null`() =
            runTest {
                //Given
                val tvShowId = 2
                coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns null

                //When
                tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

                //Then
                coVerify(exactly = 1) { tvShowDao.getTvShowById(tvShowId, language) }
            }
    }

    @Nested
    inner class TvShowCastTests {
        @Test
        fun `addCast should add cast when addCast in CastDao called successfully`() = runTest {
            //Given
            tvShowLocalDataSourceImp.addTvShowCast(
                listOf(
                    sampleCast,
                    sampleCast2
                )
            )

            //When&Then
            coVerify(exactly = 1) {
                tvShowLocalDataSourceImp.addTvShowCast(
                    listOf(
                        sampleCast,
                        sampleCast2
                    )
                )
            }
        }

        @Test
        fun `getCastByMovieId should call getCastByTvShowId on DAO and return its result`() =
            runTest {
                //Given
                val tvShowId = 10
                every {
                    runBlocking {
                        tvShowDao.getCastByTvShowId(
                            tvShowId,
                            language
                        )
                    }
                } returns sampleCastList

                //When
                val result = tvShowLocalDataSourceImp.getCastByTvShowId(
                    tvShowId,
                    language
                )

                //Then
                kotlin.test.assertEquals(sampleCastList, result)
            }

        @Test
        fun `getCastByTvShowId should call DAO method`() = runTest {
            //Given
            val tvShowId = 10
            every {
                runBlocking {
                    tvShowDao.getCastByTvShowId(
                        tvShowId,
                        language
                    )
                }
            } returns sampleCastList

            //When
            tvShowLocalDataSourceImp.getCastByTvShowId(tvShowId, language)

            //Then
            coVerify(exactly = 1) { tvShowDao.getCastByTvShowId(tvShowId, language) }
        }

        @Test
        fun `getCastByMovieId should return empty list if DAO returns empty list`() = runTest {
            //Given
            val tvShowIdWithNoCast = 200
            val emptyList = emptyList<CastEntity>()
            every {
                runBlocking {
                    tvShowDao.getCastByTvShowId(
                        tvShowIdWithNoCast,
                        language
                    )
                }
            } returns emptyList

            //When
            val result = tvShowLocalDataSourceImp.getCastByTvShowId(tvShowIdWithNoCast, language)

            //Then
            assertEquals(emptyList, result)
        }

        @Test
        fun `getCastByTvShowId should call DAO method when cast list is empty`() = runTest {
            //Given
            val tvShowIdWithNoCast = 200
            every {
                runBlocking {
                    tvShowDao.getCastByTvShowId(
                        tvShowIdWithNoCast,
                        language
                    )
                }
            } returns emptyList()

            //When
            tvShowLocalDataSourceImp.getCastByTvShowId(tvShowIdWithNoCast, language)

            //Then
            coVerify(exactly = 1) { tvShowDao.getCastByTvShowId(tvShowIdWithNoCast, language) }
        }
    }

    @Nested
    inner class TvShowGalleryTests {
        @Test
        fun `should add gallery when addGallery is called`() = runTest {
            // Given
            tvShowLocalDataSourceImp.addTvShowGallery(sampleGallery)

            // When&Then
            coVerify(exactly = 1) { tvShowDao.addTvShowGallery(sampleGallery) }
        }

        @Test
        fun `should get gallery by movie id when getGalleryByMovieId is called`() = runTest {
            //Given
            val movieId = 1
            coEvery { tvShowDao.getGalleryByTvShowId(movieId) } returns sampleGallery

            //When
            val result = tvShowLocalDataSourceImp.getGalleryByTvShowId(movieId)

            //Then
            assertThat(result).isEqualTo(sampleGallery)
        }

        @Test
        fun `should verify getGalleryByTvShowId is called once for movie id`() = runTest {
            // GIVEN
            val movieId = 1
            coEvery { tvShowDao.getGalleryByTvShowId(movieId) } returns sampleGallery

            // WHEN
            tvShowLocalDataSourceImp.getGalleryByTvShowId(movieId)

            // THEN
            coVerify(exactly = 1) { tvShowDao.getGalleryByTvShowId(movieId) }
        }

        @Test
        fun `should return null when getGalleryByMovieId returns null`() = runTest {
            //Given
            val movieId = 2
            coEvery { tvShowDao.getGalleryByTvShowId(movieId) } returns null

            //When
            val result = tvShowLocalDataSourceImp.getGalleryByTvShowId(movieId)

            //Then
            assertThat(result).isNull()
        }

        @Test
        fun `should verify getGalleryByTvShowId is called once for movie id when gallery is null`() =
            runTest {
                // GIVEN
                val movieId = 2
                coEvery { tvShowDao.getGalleryByTvShowId(movieId) } returns null

                // WHEN
                tvShowLocalDataSourceImp.getGalleryByTvShowId(movieId)

                // THEN
                coVerify(exactly = 1) { tvShowDao.getGalleryByTvShowId(movieId) }
            }
    }

    @Nested
    inner class TvShowReviewTests {
        @Test
        fun `addReview should add review when addReview in ReviewDao is called`() = runTest {
            //Given
            tvShowLocalDataSourceImp.addTvShowReviews(listOf(sampleReview))

            //When&Then
            coVerify(exactly = 1) { tvShowDao.addTvShowReviews(listOf(sampleReview)) }
        }

        @Test
        fun `getReviewsByTvShowId should return list of reviews from DAO`() = runTest {
            //Given
            val tvShowId = 2
            coEvery { tvShowDao.getReviewsByTvShowId(tvShowId, language) } returns listOf(
                sampleReview
            )

            //When
            val result = tvShowLocalDataSourceImp.getReviewsByTvShowId(
                tvShowId,
                language
            )

            //Then
            assert(result == listOf(sampleReview))
        }


        @Test
        fun `getReviewsByTvShowId should verify DAO is called`() = runTest {
            // GIVEN
            val tvShowId = 2
            coEvery {
                tvShowDao.getReviewsByTvShowId(
                    tvShowId,
                    language
                )
            } returns listOf(sampleReview)

            // WHEN
            tvShowLocalDataSourceImp.getReviewsByTvShowId(
                tvShowId,
                language
            )

            // THEN
            coVerify(exactly = 1) {
                tvShowDao.getReviewsByTvShowId(
                    tvShowId,
                    language
                )
            }
        }

        @Test
        fun `getReviewsByTvShowId should return empty list when DAO returns empty list`() =
            runTest {
                // GIVEN
                val tvShowId = 3
                coEvery {
                    tvShowDao.getReviewsByTvShowId(
                        tvShowId,
                        language
                    )
                } returns emptyList()

                // WHEN
                val result = tvShowLocalDataSourceImp.getReviewsByTvShowId(
                    tvShowId,
                    language
                )

                // THEN
                assertThat(result).isEmpty()
            }

        @Test
        fun `getReviewsByTvShowId should verify DAO is called when returning empty list`() =
            runTest {
                // GIVEN
                val tvShowId = 3
                coEvery { tvShowDao.getReviewsByTvShowId(tvShowId, language) } returns emptyList()

                // WHEN
                tvShowLocalDataSourceImp.getReviewsByTvShowId(tvShowId, language)

                // THEN
                coVerify(exactly = 1) { tvShowDao.getReviewsByTvShowId(tvShowId, language) }
            }
    }

    @Nested
    inner class TvShowSeasonTests {
        @Test
        fun `addSeason should add season when addSeason in SeasonDao called successfully`() =
            runTest {
                //Given
                tvShowLocalDataSourceImp.addTvShowSeason(Companion.sampleSeason)

                //When&Then
                coVerify(exactly = 1) { tvShowLocalDataSourceImp.addTvShowSeason(Companion.sampleSeason) }
            }

        @Test
        fun `getSeasonsByTvShowId should call getSeasonByTvShowId on DAO and return its result`() =
            runTest {
                //Given
                val tvShowId = 10
                val seasonNumber = 1
                coEvery {
                    tvShowDao.getSeasonByTvShowIdAndSeasonNumber(
                        tvShowId,
                        seasonNumber
                    )
                } returns Companion.sampleSeason

                //When
                val result = tvShowLocalDataSourceImp.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
                //Then
                assert(result == Companion.sampleSeason)
            }

        @Test
        fun `getSeasonByTvShowId should verify DAO is called with correct ID`() = runTest {
            // Given
            val tvShowId = 10
            val seasonNumber = 1
            coEvery {
                tvShowDao.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
            } returns sampleSeason

            // When
            tvShowLocalDataSourceImp.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

            // Then
            coVerify(exactly = 1) {
                tvShowDao.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
            }
        }

        @Test
        fun `getSeasonsByTvShowId should return null when DAO returns null`() = runTest {
            //Given
            val tvShowId = 1
            val seasonNumber = 1
            coEvery {
                tvShowDao.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
            } returns null

            //When
            val result =
                tvShowLocalDataSourceImp.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

            //Then
            assert(result == null)
        }

        @Test
        fun `getSeasonDetailsByTvShowId should verify DAO is called when DAO returns null`() =
            runTest {
                // Given
                val tvShowId = 1
                val seasonNumber = 1
                coEvery {
                    tvShowDao.getSeasonByTvShowIdAndSeasonNumber(
                        tvShowId,
                        seasonNumber
                    )
                } returns null

                // When
                tvShowLocalDataSourceImp.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

                // Then
                coVerify(exactly = 1) {
                    tvShowDao.getSeasonByTvShowIdAndSeasonNumber(
                        tvShowId,
                        seasonNumber
                    )
                }
            }
    }

    @Nested
    inner class TvShowSimilarTests {
        @Test
        fun `addSimilarTvShows should add tv show when addSimilarTvShows in tvShowSimilarDao is called`() =
            runTest {
                //Given
                tvShowLocalDataSourceImp.addSimilarTvShows(tvShowSimilarEntity)

                //When&Then
                coVerify(exactly = 1) { tvShowDao.addSimilarTvShows(tvShowSimilarEntity) }
            }

        @Test
        fun `getSimilarTvShows should call getSimilarTvShows on DAO and return its result`() =
            runTest {
                //Given
                val tvShowId = 2
                coEvery {
                    tvShowDao.getSimilarTvShows(
                        tvShowId,
                        page,
                        language
                    )
                } returns tvShowSimilarEntity

                //When
                val result = tvShowLocalDataSourceImp.getSimilarTvShows(
                    tvShowId, page,
                    language
                )

                //Then
                assert(result == tvShowSimilarEntity)
            }

        @Test
        fun `getSimilarTvShows should call DAO with correct parameters`() = runTest {
            // Given
            val tvShowId = 2
            coEvery {
                tvShowDao.getSimilarTvShows(
                    tvShowId, page,
                    language
                )
            } returns tvShowSimilarEntity

            // When
            tvShowLocalDataSourceImp.getSimilarTvShows(
                tvShowId, page,
                language
            )

            // Then
            coVerify(exactly = 1) {
                tvShowDao.getSimilarTvShows(
                    tvShowId, page,
                    language
                )
            }
        }


        @Test
        fun `getTvShowId should return null when DAO returns null`() = runTest {
            //Given
            val tvShowId = 3
            coEvery {
                tvShowDao.getSimilarTvShows(
                    tvShowId, page,
                    language
                )
            } returns emptyList()

            //When
            val result = tvShowLocalDataSourceImp.getSimilarTvShows(
                tvShowId, page,
                language
            )

            //Then
            assert(result.isEmpty())

        }

        @Test
        fun `getTvShowId should verify DAO is called when it returns empty list`() = runTest {
            // Given
            val tvShowId = 3
            coEvery {
                tvShowDao.getSimilarTvShows(
                    tvShowId, page,
                    language
                )
            } returns emptyList()

            // When
            tvShowLocalDataSourceImp.getSimilarTvShows(
                tvShowId, page,
                language
            )

            // Then
            coVerify(exactly = 1) {
                tvShowDao.getSimilarTvShows(
                    tvShowId, page,
                    language
                )
            }
        }
    }

    private companion object {
        val language = "en"
        val sampleTvShow = TvShowEntity(
            id = 101,
            title = "Stranger Things",
            voteAverage = 8.7,
            description = "A group of young friends witness supernatural forces and secret government exploits.",
            posterPath = "/stranger_things_poster.jpg",
            genres = listOf(
                GenreEntity(id = 1, name = "Drama"),
                GenreEntity(id = 2, name = "Fantasy"),
                GenreEntity(id = 3, name = "Horror")
            ),
            releaseDate = "2016-07-15",
            runtime = 50,
            country = "Egypt",
            productionCompanies = listOf(
                ProductionCompanyEntity(
                    id = 1001,
                    logoPath = "/netflix_logo.png",
                    name = "Netflix",
                    originCountry = "US"
                ),
                ProductionCompanyEntity(
                    id = 1002,
                    logoPath = "/21laps_logo.png",
                    name = "21 Laps Entertainment",
                    originCountry = "US"
                )
            ),
            seasons = listOf(),
            language = language
        )
        val sampleCast = CastEntity(
            id = 1,
            tvShowId = 2,
            name = "name",
            imageUri = "path",
            language = language
        )
        val sampleCast2 = CastEntity(
            id = 2,
            tvShowId = 4,
            name = "Maze",
            imageUri = "path",
            language = language
        )
        val sampleCastList = listOf(sampleCast, sampleCast2)
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
        val sampleReview = ReviewEntity(
            id = 1,
            tvShowId = 2,
            name = "الاسطوره",
            createdAt = LocalDate(year = 2020, month = 2, day = 2),
            avatarUrl = "path",
            username = "username",
            rating = 1.9,
            description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White.",
            language = language
        )
        val sampleSeason = SeasonEntity(
            id = 1,
            tvShowId = 10,
            name = "The Red Wedding",
            episodes = listOf(
                EpisodeEntity(
                    id = 1,
                    episodeNumber = 2,
                    posterUrl = "path",
                    voteAverage = 9.9,
                    airDate = LocalDate(year = 2020, month = 1, day = 1),
                    runtime = 20,
                    description = "GGGGGG",
                    stillUrl = "path"
                )
            ),
            episodeCount = 0,
            seasonNumber = 1
        )
        val page = 1
        val tvShowSimilarEntity = listOf(
            TvShowSimilarEntity(
                id = 1,
                tvShowId = 30,
                title = "spider man",
                voteAverage = 5.5,
                posterPath = "www.image.com",
                releaseDate = "2025-05-05",
                language = language,
                page = page
            )
        )
    }
}