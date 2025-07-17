package com.repository.movie.util

import java.util.Locale

fun detectLanguage(): String {
    return Locale.getDefault().language
}
