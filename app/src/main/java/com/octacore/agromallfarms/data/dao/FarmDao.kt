package com.octacore.agromallfarms.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.octacore.agromallfarms.model.Farm

@Dao
interface FarmDao {
    @Query("SELECT * FROM farm")
    fun getAllFarms(): List<Farm>
}