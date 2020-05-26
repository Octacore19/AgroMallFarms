package com.octacore.agromallfarms.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.model.FarmerAndFarm

@Dao
interface FarmerDao {
    @Transaction
    @Query("SELECT * FROM farmer")
    fun getAllFarmersAndTheirFarm(): LiveData<List<FarmerAndFarm>>

    @Insert
    suspend fun insertFarmerAndFarm(farmer: Farmer, farm: Farm)
}