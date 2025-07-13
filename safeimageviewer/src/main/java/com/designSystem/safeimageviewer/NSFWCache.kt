package com.designSystem.safeimageviewer

import android.util.LruCache

internal object NSFWCache {
    private const val CACHE_SIZE = 100
    private val cache = LruCache<Any, Boolean>(CACHE_SIZE)

    fun get(model: Any): Boolean? {
        return cache[model]
    }

    fun put(model: Any, isNSFW: Boolean) {
        cache.put(model, isNSFW)
    }
}

