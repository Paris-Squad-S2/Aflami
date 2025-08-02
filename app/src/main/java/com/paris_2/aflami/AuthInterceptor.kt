package com.paris_2.aflami

import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val localDataSource: AuthenticationLocalDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionId = localDataSource.getSessionId()
        val requestBuilder = chain.request().newBuilder()
        sessionId?.let { requestBuilder.addHeader("session_id", it) }
        return chain.proceed(requestBuilder.build())
    }
}
