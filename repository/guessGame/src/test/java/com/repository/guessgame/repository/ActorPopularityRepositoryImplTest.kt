package com.repository.guessgame.repository

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.exception.FailedException
import com.paris_2.domain.game.exception.NoInternetConnectionException
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.guessgame.dto.ActorDto
import com.repository.guessgame.dto.ActorMediaDto
import com.repository.guessgame.dto.ActorPopularityListDto
import com.repository.guessgame.utils.NetworkConnectionChecker
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ActorPopularityRepositoryImplTest {

    private lateinit var repository: ActorPopularityRepositoryImpl
    private val networkChecker: NetworkConnectionChecker = mockk()
    private val remote: ActorPopularityRemoteDataSource = mockk()

    @BeforeEach
    fun setUp() {
        every { networkChecker.isConnected } returns MutableStateFlow(true)
        repository = ActorPopularityRepositoryImpl(networkChecker, remote)
    }


    @Test
    fun `getPopularActor returns mapped actors when online`() = runTest {
        // Given
        val dto = ActorPopularityListDto(results = sampleActorDtos)
        coEvery { remote.getPopularActors() } returns dto

        // When
        val result = repository.getPopularActor()

        // Then
        val expectedActors = sampleActorDtos.filter {
            it.profilePath?.isNotEmpty() == true && !it.knownFor.isNullOrEmpty() && it.name?.isNotEmpty() == true
        }

        assertThat(result).hasSize(expectedActors.size)
        assertThat(result.map { it.name })
            .containsExactlyElementsIn(expectedActors.map { it.name!! })

        coVerify(exactly = 1) { remote.getPopularActors() }
    }

    @Test
    fun `getPopularActor returns empty list when remote returns null results`() = runTest {
        // Given
        coEvery { remote.getPopularActors() } returns ActorPopularityListDto(results = null)
        // When
        val result = repository.getPopularActor()
        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getPopularActor throws NoInternetConnectionException when offline`() = runTest {
        every { networkChecker.isConnected } returns MutableStateFlow(false)
        assertThrows<NoInternetConnectionException> {
            repository.getPopularActor()
        }
    }

    @Test
    fun `getPopularActor wraps unknown exception as FailedException`() = runTest {
        coEvery { remote.getPopularActors() } throws RuntimeException("boom")
        assertThrows<FailedException> {
            repository.getPopularActor()
        }
    }

    @Test
    fun `getRandomActors returns requested number of actors`() = runTest {
        // Given
        coEvery { remote.getPopularActors() } returns ActorPopularityListDto(results = sampleActorDtos)
        // When
        val result = repository.getRandomActors(3)
        // Then
        val expectedActors = sampleActorDtos.filter {
            it.profilePath?.isNotEmpty() == true &&
                    !it.knownFor.isNullOrEmpty() &&
                    it.name?.isNotEmpty() == true
        }

        assertThat(result).hasSize(expectedActors.size)
        assertThat(expectedActors.mapNotNull { it.name }
            .toSet()).containsAtLeastElementsIn(result.map { it.name }.toSet())
        coVerify(exactly = 1) { remote.getPopularActors() }
    }

    @Test
    fun `getRandomActors throws NoInternetConnectionException when offline`() = runTest {
        every { networkChecker.isConnected } returns MutableStateFlow(false)
        assertThrows<NoInternetConnectionException> {
            repository.getRandomActors(2)
        }
    }

    private companion object {
        private val sampleActorDtos = listOf(
            ActorDto(
                id = 1,
                name = "Actor One",
                profilePath = "/img1.jpg",
                knownFor = listOf(
                    ActorMediaDto(
                        id = 10,
                        title = "Movie A",
                        posterPath = "/pA.jpg",
                        releaseDate = "2020-01-01",
                        genreIds = listOf(28)
                    )
                )
            ),
            ActorDto(
                id = 2,
                name = "Actor Two",
                profilePath = "/img2.jpg",
                knownFor = listOf(
                    ActorMediaDto(
                        id = 20,
                        title = "Movie B",
                        posterPath = "/pB.jpg",
                        releaseDate = "2021-02-02",
                        genreIds = listOf(18)
                    )
                )
            ),
            ActorDto(
                id = 3,
                name = "Actor Three",
                profilePath = "/img3.jpg",
                knownFor = emptyList()
            ),
            ActorDto(
                id = 4,
                name = "Actor Four",
                profilePath = "/img4.jpg",
                knownFor = emptyList()
            ),
            ActorDto(
                id = 5,
                name = "Actor Five",
                profilePath = "/img5.jpg",
                knownFor = emptyList()
            )
        )
    }
}
