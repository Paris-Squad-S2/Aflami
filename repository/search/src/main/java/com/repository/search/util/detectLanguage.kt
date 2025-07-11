package com.repository.search.util

fun detectLanguage(query: String): String {
    val arabicCharRange = '\u0600'..'\u06FF'
    val firstChar = query.firstOrNull { it.isLetter() } ?: return "en"
    return if (firstChar in arabicCharRange) "ar" else "en"
}
