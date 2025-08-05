package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Country
import com.repository.media.entity.CountryEntity


fun List<CountryEntity>.toCountry(): List<Country> = this.map {
    Country(
        it.countryCode,
        it.englishName,
        it.arabicName
    )
}