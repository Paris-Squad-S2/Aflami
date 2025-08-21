package com.repository.guessgame.utils

fun String.isEnglish(): Boolean {
    return this.matches(Regex("^[A-Za-z\\d\\s\\p{Punct}]+$"))
}