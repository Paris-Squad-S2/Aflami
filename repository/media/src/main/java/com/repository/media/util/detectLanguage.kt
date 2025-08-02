package com.repository.media.util

import java.util.Locale

fun detectLanguage(): String {
    return Locale.getDefault().language
}
