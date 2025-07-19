package com.repository.movie.util

fun String?.toImageUrl(): String? {
    return this?.let { "https://image.tmdb.org/t/p/w500/$it" }
}