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
    @Query("SELECT * FROM farmer LIMIT 5")
    fun getAllFarmersAndTheirFarm(): LiveData<List<FarmerAndFarm>>

    @Query("SELECT * FROM farmer")
    fun getAllFarmers(): LiveData<List<Farmer>>

    @Query("SELECT * FROM farm")
    fun getAllFarms(): LiveData<List<Farm>>

    @Insert
    suspend fun insertFarmerAndFarm(farmer: Farmer, farm: Farm)
}