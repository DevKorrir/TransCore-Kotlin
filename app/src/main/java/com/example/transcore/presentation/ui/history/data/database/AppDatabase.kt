package com.example.transcore.presentation.ui.history.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.transcore.presentation.ui.history.data.dao.TranslationHistoryDao
import com.example.transcore.presentation.ui.history.data.entity.TranslationHistoryEntity

@Database(
    entities = [TranslationHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): TranslationHistoryDao
}
