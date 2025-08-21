package com.feature.authentication.authenticationUi.comon

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
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
        webViewClient = WebViewClientImpl(isLoading, hasError, skipSuper = true)
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
        val isLoading = mutableStateOf(true)
        val hasError = mutableStateOf(false)

        val webViewClient = spyk(WebViewClientImpl(isLoading, hasError))

        every { webViewClient.onReceivedError(any(), any(), any()) } answers {
            isLoading.value = false
            hasError.value = true
        }

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
        every { mockUri.toString() } returns "https://www.themoviedb.org"

        val request = mockk<WebResourceRequest>()
        every { request.url } returns mockUri

        val result = client.shouldOverrideUrlLoading(mockk<WebView>(), request)

        assertThat(eventCalled).isTrue()
        assertThat(result).isTrue()
    }

    @Test
    fun `should invoke onNavigationEvent and return true for login url`() {
        val isLoading = mutableStateOf(false)
        val hasError = mutableStateOf(false)
        var eventCalled = false
        val onNavigationEvent = { eventCalled = true }
        val client = WebViewClientImpl(isLoading, hasError, onNavigationEvent)

        val mockUri = mockk<Uri>()
        every { mockUri.toString() } returns "https://www.themoviedb.org/login"
        val request = mockk<WebResourceRequest>()
        every { request.url } returns mockUri

        val result = client.shouldOverrideUrlLoading(mockk<WebView>(), request)

        assertThat(eventCalled).isTrue()
        assertThat(result).isTrue()
    }

    @Test
    fun `should set isLoading false and hasError false for non-matching url`() {
        val isLoading = mutableStateOf(false)
        val hasError = mutableStateOf(true)
        val client = WebViewClientImpl(isLoading, hasError)

        val mockUri = mockk<Uri>()
        every { mockUri.toString() } returns "https://www.example.com/"
        val request = mockk<WebResourceRequest>()
        every { request.url } returns mockUri

        val result = client.shouldOverrideUrlLoading(mockk<WebView>(), request)

        assertThat(isLoading.value).isTrue()
        assertThat(hasError.value).isFalse()
        assertThat(result).isFalse()
    }

    @Test
    fun `shouldOverrideUrlLoading handles null request safely`() {
        val isLoading = mutableStateOf(false)
        val hasError = mutableStateOf(false)
        val client = WebViewClientImpl(isLoading, hasError)
        val result = client.shouldOverrideUrlLoading(mockk<WebView>(), null as WebResourceRequest?)
        assertThat(isLoading.value).isTrue()
        assertThat(hasError.value).isFalse()
        assertThat(result).isFalse()
    }

    @Test
    fun `shouldOverrideUrlLoading handles null url safely`() {
        val isLoading = mutableStateOf(false)
        val hasError = mutableStateOf(false)
        val request = mockk<WebResourceRequest>()
        every { request.url } returns null
        val client = WebViewClientImpl(isLoading, hasError)
        val result = client.shouldOverrideUrlLoading(mockk<WebView>(), request)
        assertThat(isLoading.value).isTrue()
        assertThat(hasError.value).isFalse()
        assertThat(result).isFalse()
    }



    @Test
    fun `shouldOverrideUrlLoading does not invoke onNavigationEvent for non-target url`() {
        val isLoading = mutableStateOf(false)
        val hasError = mutableStateOf(false)
        var eventCalled = false
        val onNavigationEvent = { eventCalled = true }
        val client = WebViewClientImpl(isLoading, hasError, onNavigationEvent)
        val mockUri = mockk<Uri>()
        every { mockUri.toString() } returns "https://www.example.com/"
        val request = mockk<WebResourceRequest>()
        every { request.url } returns mockUri
        client.shouldOverrideUrlLoading(mockk<WebView>(), request)
        assertThat(eventCalled).isFalse()
    }
}