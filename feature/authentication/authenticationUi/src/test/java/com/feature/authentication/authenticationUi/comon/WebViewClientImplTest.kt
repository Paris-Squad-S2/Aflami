package com.feature.authentication.authenticationUi.comon

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WebViewClientImplTest {

    private lateinit var isLoading: MutableState<Boolean>
    private lateinit var hasError: MutableState<Boolean>
    private lateinit var webViewClient: WebViewClientImpl

    @BeforeEach
    fun setUp() {
        isLoading = mutableStateOf(false)
        hasError = mutableStateOf(false)
        webViewClient = WebViewClientImpl(isLoading, hasError)
    }

    @Test
    fun `onPageStarted sets isLoading true and hasError false`() {
        webViewClient.onPageStarted(mockk(), "http://test.com", null)
        Assertions.assertTrue(isLoading.value)
        Assertions.assertFalse(hasError.value)
    }

    @Test
    fun `onPageFinished sets isLoading false`() {
        isLoading.value = true
        webViewClient.onPageFinished(mockk(), "http://test.com")
        Assertions.assertFalse(isLoading.value)
    }

    @Test
    fun `onReceivedError sets hasError true and isLoading false`() {
        isLoading.value = true
        hasError.value = false
        webViewClient.onReceivedError(mockk(), mockk(), mockk())
        Assertions.assertTrue(hasError.value)
        Assertions.assertFalse(isLoading.value)
    }

    @Test
    fun `shouldOverrideUrlLoading returns false to allow normal navigation`() {
        val result = webViewClient.shouldOverrideUrlLoading(mockk(), mockk())
        Assertions.assertFalse(result)
    }
}