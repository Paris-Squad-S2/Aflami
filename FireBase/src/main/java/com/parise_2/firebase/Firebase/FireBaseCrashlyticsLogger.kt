package com.parise_2.firebase.Firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.parise_2.firebase.repo.Logger

class FireBaseCrashlyticsLogger : Logger {
    override fun logException(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }
}