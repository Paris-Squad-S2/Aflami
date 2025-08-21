package com.paris.domain.media.repository

import com.paris.domain.media.entity.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
}