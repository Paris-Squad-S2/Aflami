package com.repository.media.mapper.search

import com.paris.domain.media.entity.Country
import com.repository.media.models.local.media.CountryEntity


fun List<CountryEntity>.toCountry(): List<Country> = this.map {
    Country(
        it.countryCode,
        it.englishName,
        it.arabicName
    )
}