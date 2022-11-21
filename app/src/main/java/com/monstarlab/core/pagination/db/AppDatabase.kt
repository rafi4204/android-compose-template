package com.monstarlab.core.pagination.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monstarlab.core.pagination.model.RemoteKeys
import com.monstarlab.core.pagination.model.Resource
import com.monstarlab.core.pagination.dao.RemoteKeysDao
import com.monstarlab.core.pagination.dao.ResourceDao
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
@Database(version = 1, entities = [Resource::class, RemoteKeys::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RemoteKeysDao
    abstract fun getImageModelDao(): ResourceDao

    companion object {

        private const val RESOURCE_DB = "resource.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, RESOURCE_DB)
                .build()
    }

}