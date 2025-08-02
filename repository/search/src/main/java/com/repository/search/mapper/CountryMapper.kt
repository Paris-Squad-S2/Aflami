package com.repository.search.mapper

import com.domain.media.entity.Country
import com.repository.search.entity.CountryEntity


fun List<CountryEntity>.toCountry(): List<Country> = this.map {
    Country(
        it.countryCode,
        it.englishName,
        it.arabicName
    )
}