package com.feature.authentication.authenticationUi.comon

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.MutableState

class WebViewClientImpl(
    private val isLoading: MutableState<Boolean>,
    private val hasError: MutableState<Boolean>,
    private val onNavigationEvent: (() -> Unit)? = null
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val url = request?.url?.toString()
        if (url != null) {
            if (url == "https://www.themoviedb.org/" || url == "https://www.themoviedb.org/login") {
                onNavigationEvent?.invoke()
                return true
            }
        }
        isLoading.value = true
        hasError.value = false
        return true
    }

    override fun onPageStarted(
        view: WebView?,
        url: String?,
        favicon: Bitmap?
    ) {
        isLoading.value = true
        hasError.value = false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        isLoading.value = false
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ){
        hasError.value = true
        isLoading.value = false
    }
}