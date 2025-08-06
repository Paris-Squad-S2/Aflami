package com.feature.onboarding.onboardingUi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.feature.onboarding.onboardingUi.R
import com.feature.onboarding.onboardingUi.components.OnboardingContent
import com.feature.onboarding.onboardingUi.components.OnboardingPage
import com.feature.onboarding.onboardingUi.components.PageIndicator
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnBoardingViewModel,
    onFinish: () -> Unit
) {
    val pageCount = 4
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateCurrentPage(pagerState.currentPage)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) {
            OnboardingContent(
                OnboardingPage(
                    title = viewModel.getTitle(),
                    description = viewModel.getDescription(),
                    backgroundRes = viewModel.getBackground()
                )
            )
        }
        CustomButton(
            onClick = {
                onFinish()
            },
            text = R.string.skip,
            type = ButtonType.TextButton,
        )
//        PageIndicator(
//            totalPages = 4,
//            currentPage = pagerState.currentPage
//        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage == 0) {
                CustomButton(
                    modifier = Modifier.size(64.dp),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    icon = {
                        AppIcon(
                            Icons.Default.KeyboardArrowRight, "right arrow",
                            tint = Theme.colors.primary
                        )
                    },
                    type = ButtonType.FloatingActionButton,
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomButton(
                        modifier = Modifier.size(64.dp),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        icon = {
                            AppIcon(
                                Icons.Default.KeyboardArrowLeft, "left arrow",
                                tint = Theme.colors.primary
                            )
                        },
                        type = ButtonType.FloatingActionButton,
                    )
                    CustomButton(
                        modifier = Modifier.size(64.dp),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        icon = {
                            AppIcon(
                                Icons.Default.KeyboardArrowRight, "right arrow",
                                tint = Theme.colors.primary
                            )
                        },
                        type = ButtonType.FloatingActionButton,
                        state = if (pagerState.currentPage == 3) ButtonState.Disabled else ButtonState.Normal
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    BasePreview {
        OnboardingScreen(
            viewModel = OnBoardingViewModel()
        ) { }
    }

}