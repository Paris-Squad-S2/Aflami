package com.parise_2.firebase.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.parise_2.firebase.repo.Logger
import com.parise_2.logger.BuildConfig

class FireBaseCrashlyticsLogger : Logger {
    override fun logException(exception: Exception) {
        if (!BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().recordException(exception)
        }
    }
}