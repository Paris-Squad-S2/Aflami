package com.parise_2.firebase.repo

import com.parise_2.firebase.Firebase.FireBaseCrashlyticsLogger

interface Logger {
    fun logException(exception: Exception)
}