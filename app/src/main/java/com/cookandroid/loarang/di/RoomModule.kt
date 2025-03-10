package com.cookandroid.loarang.di

import android.content.Context
import androidx.room.Room
import com.cookandroid.loarang.room.CharacterDao
import com.cookandroid.loarang.room.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CharacterDatabase {
        return Room.databaseBuilder(
            context,
            CharacterDatabase::class.java,
            "Character"
        ).build()
    }

    @Provides
    fun provideCharacterDao(database: CharacterDatabase): CharacterDao {
        return database.characterDao()
    }
}