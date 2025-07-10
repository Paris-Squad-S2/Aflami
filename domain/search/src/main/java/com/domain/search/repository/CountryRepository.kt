package com.domain.search.repository

import com.domain.search.model.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
}