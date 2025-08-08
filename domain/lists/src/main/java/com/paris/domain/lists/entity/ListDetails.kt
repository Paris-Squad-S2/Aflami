package com.paris.domain.lists.entity

data class ListDetails(
    val id: Int,
    val name: String,
    val items: List<Media>
)