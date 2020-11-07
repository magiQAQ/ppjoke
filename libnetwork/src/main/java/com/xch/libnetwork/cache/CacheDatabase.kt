package com.xch.libnetwork.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xch.libcommon.AppGlobals

@Database(entities = [Cache::class], version = 1, exportSchema = true)
abstract class CacheDatabase: RoomDatabase() {

    companion object{
        val database by lazy {
            Room.databaseBuilder(AppGlobals.application, CacheDatabase::class.java,"ppjoke_cache")
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun cacheDao(): CacheDao

}