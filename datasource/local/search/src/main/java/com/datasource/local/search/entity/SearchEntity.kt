package com.datasource.local.search.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(tableName = "search_table")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val searchHistory: String,
    val lastDateSearched: LocalDate? = null,
)
