package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM countries_table")
    suspend fun getCountries(): List<CountryEntity>
}