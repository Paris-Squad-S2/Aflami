package com.feature.home.homeUi.mapper

import com.paris_2.domain.media.entity.Category


fun Category.toDisplayName(): String = this.displayName

fun String.toGenerEnum(): Category {
    return Category.entries.firstOrNull { it.displayName.equals(this, ignoreCase = true) }
        ?: Category.UNKNOWN
}

