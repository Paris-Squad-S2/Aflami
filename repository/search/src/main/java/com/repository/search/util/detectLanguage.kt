package com.repository.search.util

import java.util.Locale

fun detectLanguage(): String {
    return Locale.getDefault().language
}
