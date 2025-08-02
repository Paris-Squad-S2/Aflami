package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
}