package com.repository.search.mapper

import com.paris_2.domain.media.entity.Country
import com.repository.search.entity.CountryEntity


fun List<CountryEntity>.toCountry(): List<Country> = this.map {
    Country(
        it.countryCode,
        it.englishName,
        it.arabicName
    )
}