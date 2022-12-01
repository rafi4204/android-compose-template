package com.composetemplate.core.pagination.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.composetemplate.core.pagination.model.Resource

@Dao
interface ResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resource: List<Resource>)

    @Query("SELECT * FROM resources")
    fun getAllResources(): PagingSource<Int, Resource>

    @Query("DELETE FROM resources")
    suspend fun clearAllResources()
}