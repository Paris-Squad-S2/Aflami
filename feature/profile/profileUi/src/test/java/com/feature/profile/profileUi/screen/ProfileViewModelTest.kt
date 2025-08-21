package com.feature.profile.profileUi.screen

import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.profile.profileUi.screen.profile.Appearance
import com.feature.profile.profileUi.screen.profile.ContentRestriction
import com.feature.profile.profileUi.screen.profile.Language
import com.feature.profile.profileUi.screen.profile.ProfileViewModel
import com.paris.domain.game.usecases.GetUserPointUseCase
import com.paris.domain.user.usecase.auth.DeleteSessionIdUseCase
import com.paris.domain.user.usecase.auth.GetAccountIdUseCase
import com.paris.domain.user.usecase.auth.IsLoggedInUseCase
import com.paris.domain.user.usecase.ManageSettingsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val manageSettingsUseCase = mockk<ManageSettingsUseCase>()
    private val isLoggedInUseCase = mockk<IsLoggedInUseCase>()
    private val authenticationFeatureAPI = mockk<AuthenticationFeatureAPI>()
    private val deleteSessionIdUseCase = mockk<DeleteSessionIdUseCase>()
    private val getUserPointsUseCase = mockk<GetUserPointUseCase>()
    private val getAccountIdUseCase = mockk<GetAccountIdUseCase>()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProfileViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { manageSettingsUseCase.isDarkTheme() } returns flowOf(false)
        coEvery { manageSettingsUseCase.getLanguage() } returns flowOf("en")
        every { manageSettingsUseCase.getUserName() } returns "Test User"
        coEvery { manageSettingsUseCase.getRestriction() } returns ContentRestriction.Off.name
        every { isLoggedInUseCase.invoke() } returns true
        coEvery { getUserPointsUseCase(any()) } returns flowOf(100)

        viewModel = ProfileViewModel(
            manageSettingsUseCase = manageSettingsUseCase,
            isLoggedInUseCase = isLoggedInUseCase,
            authenticationFeatureAPI = authenticationFeatureAPI,
            deleteSessionIdUseCase = deleteSessionIdUseCase,
            getUserPointsUseCase = getUserPointsUseCase,
            getAccountIdUseCase = getAccountIdUseCase
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onChooseLanguageClicked opens language dialog`() = runTest {
        // When
        viewModel.onChooseLanguageClicked()

        // Then
        assertEquals(true, viewModel.screenState.value.profile.isLanguageDialogOpen)
    }

    @Test
    fun `onChooseAppearanceClicked opens theme dialog`() = runTest {
        // Given
        coEvery { manageSettingsUseCase.isDarkTheme() } returns flowOf(false)

        // When
        viewModel.onChooseAppearanceClicked()
        advanceUntilIdle()

        // Then
        assertEquals(true, viewModel.screenState.value.profile.isThemeDialogOpen)
        assertEquals(Appearance.LIGHT, viewModel.screenState.value.profile.theme)
    }

    @Test
    fun `onSettingClicked opens settings dialog`() = runTest {
        // When
        viewModel.onSettingClicked()

        // Then
        assertEquals(true, viewModel.screenState.value.profile.isSettingDialogOpen)
    }

    @Test
    fun `onLogoutClicked opens logout dialog and closes settings dialog`() = runTest {
        // When
        viewModel.onLogoutClicked()
        advanceUntilIdle()

        // Then
        assertEquals(true, viewModel.screenState.value.profile.isLogoutDialogOpen)
        assertEquals(false, viewModel.screenState.value.profile.isSettingDialogOpen)
    }

    @Test
    fun `onContentRestrictionClicked toggles content restriction dialog`() = runTest {
        // Given
        val initialState = viewModel.screenState.value.profile.isContentRestrictionDialogOpen

        // When
        viewModel.onContentRestrictionClicked()
        advanceUntilIdle()

        // Then
        assertEquals(!initialState, viewModel.screenState.value.profile.isContentRestrictionDialogOpen)
    }

    @Test
    fun `onAppearanceApplyClicked updates theme and saves it`() = runTest {
        // Given
        val appearance = Appearance.DARK

        // When
        viewModel.onAppearanceApplyClicked(appearance)
        advanceUntilIdle()


        // Then
        assertEquals(Appearance.DARK, viewModel.screenState.value.profile.theme)
        coVerify(exactly = 1) { manageSettingsUseCase.setTheme(true) }
    }

    @Test
    fun `onLanguageApplyClicked saves new language`() = runTest {
        // Given
        val language = Language.ARABIC

        // When
        viewModel.onLanguageApplyClicked(language)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { manageSettingsUseCase.setLanguage(language.local) }
    }

    @Test
    fun `onLogoutApplyClicked deletes session and triggers login flow`() = runTest {

        //given
        coEvery { deleteSessionIdUseCase() } returns true

        // When
        viewModel.onLogoutApplyClicked()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { deleteSessionIdUseCase() }
        coVerify(exactly = 1) { authenticationFeatureAPI.invoke() }
    }

    @Test
    fun `onDismissAppearanceDialog closes theme dialog`() = runTest {
        // Given
        viewModel.updateState(
            viewModel.screenState.value.copy(
                profile = viewModel.screenState.value.profile.copy(isThemeDialogOpen = true)
            )
        )

        // When
        viewModel.onDismissAppearanceDialog()
        advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.screenState.value.profile.isThemeDialogOpen)
    }

    @Test
    fun `onDismissLanguageDialog closes language dialog`() = runTest {
        // Given
        viewModel.updateState(
            viewModel.screenState.value.copy(
                profile = viewModel.screenState.value.profile.copy(isLanguageDialogOpen = true)
            )
        )

        // When
        viewModel.onDismissLanguageDialog()
        advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.screenState.value.profile.isLanguageDialogOpen)
    }

    @Test
    fun `onDismissSettingDialog closes settings dialog`() = runTest {
        // Given
        viewModel.updateState(
            viewModel.screenState.value.copy(
                profile = viewModel.screenState.value.profile.copy(isSettingDialogOpen = true)
            )
        )

        // When
        viewModel.onDismissSettingDialog()

        // Then
        assertEquals(false, viewModel.screenState.value.profile.isSettingDialogOpen)
    }

    @Test
    fun `onDismissLogoutDialog closes logout dialog`() = runTest {
        // Given
        viewModel.updateState(
            viewModel.screenState.value.copy(
                profile = viewModel.screenState.value.profile.copy(isLogoutDialogOpen = true)
            )
        )

        // When
        viewModel.onDismissLogoutDialog()

        // Then
        assertEquals(false, viewModel.screenState.value.profile.isLogoutDialogOpen)
    }

    @Test
    fun `onDismissContentRestrictionDialog closes restriction dialog`() = runTest {
        // Given
        viewModel.updateState(
            viewModel.screenState.value.copy(
                profile = viewModel.screenState.value.profile.copy(isContentRestrictionDialogOpen = true)
            )
        )

        // When
        viewModel.onDismissContentRestrictionDialog()
        advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.screenState.value.profile.isContentRestrictionDialogOpen)
    }

    @Test
    fun `onLanguageSelected updates selected language`() = runTest {
        // Given
        val newLanguage = Language.ARABIC

        // When
        viewModel.onLanguageSelected(newLanguage)
        advanceUntilIdle()

        // Then
        assertEquals(newLanguage, viewModel.screenState.value.profile.language)
    }

    @Test
    fun `onRestrictionSelected saves restriction`() = runTest {
        // Given
        val restriction = ContentRestriction.Moderate

        // When
        viewModel.onRestrictionSelected(restriction)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { manageSettingsUseCase.setRestriction(ContentRestriction.Moderate.name) }
    }
}