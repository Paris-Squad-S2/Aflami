package com.feature.authentication.authenticationUi.comon

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.common.truth.Truth.assertThat
import io.mockk.every
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
    fun `should invoke onNavigationEvent and return true for target urls`() {
        val isLoading = mutableStateOf(false)
        val hasError = mutableStateOf(false)
        var eventCalled = false
        val onNavigationEvent = { eventCalled = true }
        val client = WebViewClientImpl(isLoading, hasError, onNavigationEvent)

        val mockUri = mockk<Uri>()
        every { mockUri.toString() } returns "https://www.themoviedb.org/"
        val request = mockk<WebResourceRequest>()
        every { request.url } returns mockUri

        val result = client.shouldOverrideUrlLoading(mockk<WebView>(), request)

        assertThat(eventCalled).isTrue()
        assertThat(result).isTrue()
    }
}