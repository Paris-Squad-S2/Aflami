package com.repository.search.mapper

import com.domain.search.model.Media
import com.example.search.mapper.toMediaOrNull
import com.example.search.models.Genre
import com.example.search.models.GenreDto
import com.example.search.models.SearchDto
import com.repository.search.entity.CountryEntity

fun SearchDto.toMediaList(): List<Media> {
    return this.results?.mapNotNull { it.toMediaOrNull() } ?: emptyList()
}

fun List<Genre>.toCountriesName(): List<String> = this.map { it.name }


