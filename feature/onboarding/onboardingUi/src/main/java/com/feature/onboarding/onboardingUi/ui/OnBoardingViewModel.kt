package com.feature.onboarding.onboardingUi.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.onboarding.onboardingUi.R
import com.paris_2.aflami.designsystem.components.UiDrawable
import com.paris_2.aflami.designsystem.components.UiText
import com.paris_2.domain.user.usecase.CompleteOnboardingUseCase
import com.paris_2.domain.user.usecase.IsOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase,
    private val authenticationFeatureAPI: AuthenticationFeatureAPI,
) : ViewModel() {


    fun updateCurrentPage(index: Int) {
        _currentScreen.intValue = index
    }

    private val _currentScreen = mutableIntStateOf(0)
    val currentScreen: State<Int> get() = _currentScreen

    init {
        viewModelScope.launch {
            if (isOnboardingCompletedUseCase()) {
                authenticationFeatureAPI()
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            completeOnboardingUseCase()
            authenticationFeatureAPI()
        }
    }

    fun getTitle(): UiText {
        return when (_currentScreen.intValue) {

            0 -> UiText.StringResource(R.string.movies_that_feel_you)
            1 -> UiText.StringResource(R.string.build_your_watchlist_show_love)
            2 -> UiText.StringResource(R.string.your_movie_journal)
            3 -> UiText.StringResource(R.string.guess_play)
            else -> UiText.DynamicString("")
        }
    }

    fun getDescription(): UiText {
        return when (_currentScreen.intValue) {
            0 -> UiText.StringResource(R.string.this_isn_t_just_a_movie_app_it_s_a_mood_experience_tap_your_vibe_and_let_the_story_begin)
            1 -> UiText.StringResource(R.string.save_your_favorite_picks_track_your_moods_and_come_back_to_find_something_new_every_day)
            2 -> UiText.StringResource(R.string.keep_track_of_what_you_ve_watched_how_you_felt_and_what_you_loved_your_film_journey_remembered)
            3 -> UiText.StringResource(R.string.put_your_film_knowledge_to_the_test_with_exciting_quizzes_every_right_guess_brings_you_closer_to_the_next_movie_adventure)
            else -> UiText.DynamicString("")
        }
    }

    fun getBackground(): UiDrawable {
        return when (_currentScreen.intValue) {
            0 -> UiDrawable.Resource(R.drawable.on_boarding_one)
            1 -> UiDrawable.Resource(R.drawable.on_boarding_two)
            2 -> UiDrawable.Resource(R.drawable.on_boarding_three)
            3 -> UiDrawable.Resource(R.drawable.on_boarding_four)
            else -> UiDrawable.Resource(R.drawable.on_boarding_one)

        }
    }
}
