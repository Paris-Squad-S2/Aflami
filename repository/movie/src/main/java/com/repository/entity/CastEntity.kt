package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cast_Table", primaryKeys = ["id"])
data class CastEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUri: String,
)
