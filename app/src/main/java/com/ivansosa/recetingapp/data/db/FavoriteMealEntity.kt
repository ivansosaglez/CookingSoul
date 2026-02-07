package com.ivansosa.recetingapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumbUrl: String?,
    val savedAt: Long = System.currentTimeMillis()
)
