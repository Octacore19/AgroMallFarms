package com.octacore.agromallfarms.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.octacore.agromallfarms.model.Farmer

@Dao
interface FarmerDao {
    @Query("SELECT * FROM farmer")
    fun getAllFarmers(): LiveData<List<Farmer>>

    @Insert
    suspend fun insertFarmer(farmer: Farmer)
}