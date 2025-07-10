package com.feature.search.searchUi.screen.worldTour

import com.domain.search.useCases.AddRecentSearchUseCase
import com.domain.search.useCases.AutoCompleteCountryUseCase
import com.domain.search.useCases.GetCountryCodeByNameUseCase
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class WorldTourViewModelTest {
    private lateinit var viewModel: WorldTourViewModel
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase
    private lateinit var addRecentSearchesUseCase: AddRecentSearchUseCase
    private lateinit var autoCompleteCountryUseCase: AutoCompleteCountryUseCase
    private lateinit var getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase
    private lateinit var getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaByActorNameUseCase = mockk(relaxed = true)
        addRecentSearchesUseCase = mockk(relaxed = true)
        autoCompleteCountryUseCase = mockk(relaxed = true)
        getMoviesByCountryUseCase = mockk(relaxed = true)
        getCountryCodeByNameUseCase = mockk(relaxed = true)

        viewModel = WorldTourViewModel(
            autoCompleteCountryUseCase = autoCompleteCountryUseCase,
            getCountryCodeByNameUseCase = getCountryCodeByNameUseCase,
            getMoviesByCountryUseCase = getMoviesByCountryUseCase,
            addRecentSearchesUseCase = addRecentSearchesUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

}