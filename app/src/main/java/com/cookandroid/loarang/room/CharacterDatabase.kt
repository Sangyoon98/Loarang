package com.cookandroid.loarang.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.HiltAndroidApp

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao

    /*companion object {
        private var instance : CharacterDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CharacterDatabase? {
            if (instance == null) {
                synchronized(CharacterDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharacterDatabase::class.java,
                        "Character"
                    ).build()
                }
            }
            return instance
        }
    }*/
}