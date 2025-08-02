package com.domain.media.repository

import com.domain.media.entity.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
}