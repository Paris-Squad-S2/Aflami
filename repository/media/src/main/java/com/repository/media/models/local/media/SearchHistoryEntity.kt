package com.repository.media.models.local.media

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.repository.media.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "search_history_table",
    primaryKeys = ["search_query", "search_type"]
)
data class SearchHistoryEntity(
    @ColumnInfo(name = "search_query")
    val searchQuery: String,
    @ColumnInfo(name = "search_type")
    val searchType: SearchType,
    @ColumnInfo(name = "search_date")
    val searchDate: LocalDateTime = getCurrentDate(),
)

enum class SearchType {
    Query,
    Country,
    Actor
}