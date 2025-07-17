package com.repository.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "gallery_table",
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GalleryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tvShowId: Int,
    val images: List<ImageEntity>,
    val galleryCacheDate: LocalDateTime = getCurrentDate()
)