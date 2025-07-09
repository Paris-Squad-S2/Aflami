package com.repository.search.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.repository.search.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "search_history_table")
data class SearchHistoryEntity(
    @PrimaryKey()
    @ColumnInfo(name = "search_query")
    val searchQuery: String,
    @ColumnInfo(name = "search_date")
    val searchDate: LocalDateTime = getCurrentDate()
)