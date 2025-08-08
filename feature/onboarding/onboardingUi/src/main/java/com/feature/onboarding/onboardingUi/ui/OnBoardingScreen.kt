package com.feature.onboarding.onboardingUi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.feature.onboarding.onboardingUi.R
import com.feature.onboarding.onboardingUi.components.OnboardingContent
import com.feature.onboarding.onboardingUi.components.OnboardingPage
import com.feature.onboarding.onboardingUi.components.PageIndicator
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnBoardingViewModel= hiltViewModel(),
) {
    val pageCount = 4
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateCurrentPage(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(Theme.colors.gradient.overlyDark),
                RoundedCornerShape(24.dp)
            )

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
                viewModel.completeOnboarding()
            },
            text = R.string.skip,
            type = ButtonType.TextButton,
            modifier = Modifier.padding(start = 16.dp, top = 56.dp)
        )

        PageIndicator(
            totalPages = 4,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(start = 12.dp, top = 600.dp, end = 144.dp)
        )



        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .align(
                    Alignment.BottomCenter
                )
                .padding(bottom = 16.dp),
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
                            ImageVector.vectorResource(R.drawable.arrow), "right arrow",
                            tint = Theme.colors.primary
                        )
                    },
                    type = ButtonType.FloatingActionButton,
                )
            } else {
                    CustomButton(
                        modifier = Modifier.size(64.dp),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        icon = {
                            AppIcon(
                                ImageVector.vectorResource(R.drawable.arrow),
                                "left arrow",
                                tint = Theme.colors.primary,
                                modifier = Modifier.rotate(180f)
                            )
                        },
                        type = ButtonType.FloatingActionButton,
                    )
                    CustomButton(
                        modifier = Modifier.size(64.dp),
                        onClick = {
                            if (pagerState.currentPage == 3) {
                                viewModel.completeOnboarding()
                            } else
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        icon = {
                            AppIcon(
                                ImageVector.vectorResource(R.drawable.arrow), "right arrow",
                                tint = Theme.colors.primary
                            )
                        },
                        type = ButtonType.FloatingActionButton,
                    )
                }
            }
        }
    }
