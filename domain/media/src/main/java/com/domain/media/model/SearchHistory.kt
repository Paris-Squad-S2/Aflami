package com.domain.media.model

data class SearchHistoryModel(
    val searchTitle: String,
    val searchDate:String,
    val searchType: SearchType
)

enum class SearchType {
    Query,
    Country,
    Actor
}