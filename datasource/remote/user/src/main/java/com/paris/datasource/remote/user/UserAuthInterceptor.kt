package com.paris.datasource.remote.user

import okhttp3.Interceptor
import okhttp3.Response

class UserAuthInterceptor(
    private val getSessionId: () -> String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionId = getSessionId()
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val newUrl = sessionId?.let {
            originalUrl.newBuilder().addQueryParameter(SESSION_ID, it).build()
        } ?: originalUrl
        val newRequest = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }

    private companion object{
        const val SESSION_ID = "session_id"
    }
}
