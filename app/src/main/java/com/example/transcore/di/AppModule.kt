package com.example.transcore.di

import android.content.Context
import androidx.room.Room
import com.example.transcore.presentation.ui.history.data.dao.TranslationHistoryDao
import com.example.transcore.presentation.ui.history.data.database.AppDatabase
import com.example.transcore.presentation.ui.history.data.repository.TranslationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "transcore_db").build()

    @Provides
    fun provideHistoryDao(db: AppDatabase): TranslationHistoryDao = db.historyDao()

    @Provides
    @Singleton
    fun provideRepository(
        dao: TranslationHistoryDao
    ): TranslationRepositoryImpl = TranslationRepositoryImpl(dao)
}
