package com.ivansosa.recetingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMealEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
