package com.feature.authentication.authenticationUi.comon

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.MutableState

object AuthUrls {
    const val BASE = "https://www.themoviedb.org"
    const val LOGIN = "$BASE/login"
}

open class WebViewClientImpl(
    private val isLoading: MutableState<Boolean>,
    private val hasError: MutableState<Boolean>,
    private val onNavigationEvent: (() -> Unit)? = null
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val url = request?.url?.toString().orEmpty()
        return when {

            url == AuthUrls.BASE || url.startsWith(AuthUrls.LOGIN) -> {
                onNavigationEvent?.invoke()
                true
            }

            else -> {
                isLoading.value = true
                hasError.value = false
                false
            }
        }

    }

    override fun onPageStarted(
        view: WebView?,
        url: String?,
        favicon: Bitmap?
    ) {
        super.onPageStarted(view, url, favicon)
        isLoading.value = true
        hasError.value = false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        isLoading.value = false
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (request?.isForMainFrame == true) {
            hasError.value = true
            isLoading.value = false
        }
    }
}
