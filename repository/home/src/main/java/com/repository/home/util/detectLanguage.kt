package com.repository.home.util

import java.util.Locale

fun detectLanguage(): String {
    return Locale.getDefault().language
}
