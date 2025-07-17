package com.repository.search.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.intellij.lang.annotations.Language

@Entity(tableName = "Genres_table")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val language: String
)