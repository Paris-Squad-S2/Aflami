package com.repository.search.mapper

import com.domain.search.model.Media
import com.example.search.mapper.toMediaOrNull
import com.example.search.models.SearchDto

fun SearchDto.toMediaList(): List<Media> {
    return this.results?.mapNotNull { it.toMediaOrNull() } ?: emptyList()
}



