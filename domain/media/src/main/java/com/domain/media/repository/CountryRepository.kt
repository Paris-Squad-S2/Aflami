package com.domain.media.repository

import com.domain.media.model.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
}