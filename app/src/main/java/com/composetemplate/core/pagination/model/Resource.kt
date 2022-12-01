package com.composetemplate.core.pagination.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resources")
data class Resource(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String

)