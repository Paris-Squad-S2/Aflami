package com.paris_2.aflami

import android.util.Log
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val localDataSource: AuthenticationLocalDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionId = localDataSource.getSessionId()
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val requestBuilder = originalRequest.newBuilder()
        val newUrl = sessionId?.let {
            originalUrl.newBuilder().addQueryParameter("session_id", it).build()
        } ?: originalUrl
        requestBuilder.url(newUrl)
        return chain.proceed(requestBuilder.build())
    }
}
