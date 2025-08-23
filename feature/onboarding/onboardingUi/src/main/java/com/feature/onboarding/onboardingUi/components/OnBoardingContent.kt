package com.feature.onboarding.onboardingUi.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.onboarding.onboardingUi.R
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.components.UiDrawable
import com.paris.aflami.designsystem.components.UiText
import com.paris.aflami.designsystem.components.asPainter
import com.paris.aflami.designsystem.components.asString
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun OnboardingContent(
    page: OnboardingPage,
    currentPage: Int = 0,
    totalPages: Int = 3
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface, RoundedCornerShape(24.dp))
    ) {
        page.backgroundRes.asPainter()?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(start = 12.dp, end = 12.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {

            PageIndicator(
                currentPage = currentPage,
                totalPages = totalPages,
                modifier = Modifier
                    .padding(bottom = 24.dp, end = 144.dp)
            )

            AppText(
                text = page.title.asString(),
                style = Theme.textStyle.headline.small,
                color = Theme.colors.onPrimaryColors.onPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            AppText(
                text = page.description.asString(),
                style = Theme.textStyle.body.medium,
                color = Theme.colors.onPrimaryColors.onPrimaryBody
            )
        }
    }
}
data class OnboardingPage(
    val title: UiText,
    val description: UiText,
    val backgroundRes: UiDrawable
)

@Preview
@Composable
private fun OnBoardingContentPreview() {
    AflamiTheme {
        OnboardingContent(
            page = OnboardingPage(
              title = UiText.StringResource(R.string.your_movie_journal),
                description = UiText.StringResource(R.string.keep_track_of_what_you_ve_watched_how_you_felt_and_what_you_loved_your_film_journey_remembered),
                backgroundRes = UiDrawable.Resource(R.drawable.on_boarding_one)
            )
        )
    }
}