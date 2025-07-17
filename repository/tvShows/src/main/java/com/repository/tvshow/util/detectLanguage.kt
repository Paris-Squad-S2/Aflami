package com.repository.tvshow.util

import java.util.Locale

fun detectLanguage(): String {
    return Locale.getDefault().language
}
