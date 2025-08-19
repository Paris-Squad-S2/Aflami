package com.feature.profile.profileUi.screen.changepassword

import com.paris_2.domain.user.usecase.GetForgetPasswordUrlUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangePasswordViewModelTest {

    private val getForgetPasswordUrlUseCase = mockk<GetForgetPasswordUrlUseCase>()

    @Test
    fun `when init a new object of ChangePasswordViewModel then getForgetPasswordUrlUseCase should be called`() {
        every { getForgetPasswordUrlUseCase() } returns "https//aflami.password.com"
        val viewModel = ChangePasswordViewModel(getForgetPasswordUrlUseCase)
        val state = viewModel.screenState.value.resetPasswordUrl
        assertEquals(state, "https//aflami.password.com")
    }

}