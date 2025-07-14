package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.entity.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM countries_table")
    suspend fun getAllCountries(): List<CountryEntity>
}