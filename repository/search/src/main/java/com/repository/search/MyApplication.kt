package com.repository.search

import android.Manifest
import android.app.Application
import androidx.annotation.RequiresPermission

class MyApplication : Application() {
    private val networkChecker by lazy { NetworkConnectionChecker(this) }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onCreate() {
        super.onCreate()
        networkChecker.startChecker()
    }
}