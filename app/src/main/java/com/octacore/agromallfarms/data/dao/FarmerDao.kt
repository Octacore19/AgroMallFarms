package com.octacore.agromallfarms.data.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.model.FarmerAndFarm

@Dao
abstract class FarmerDao {

    @Query("SELECT * FROM farm")
    abstract fun getAllFarms(): LiveData<List<Farm>>

    suspend fun insertFarmer(farmer: Farmer){
        insertFarmWithFarmer(farmer, farmer.farm)
        _insertFarmer(farmer)
    }

    fun getAllFarmersAndTheirFarm(): LiveData<List<Farmer>>{
        val farmersAndFarm = _getAllFarmersAndTheirFarm()
        val farmer = mutableListOf<Farmer>()
        val liveDataFarmer = MutableLiveData<List<Farmer>>()
        farmersAndFarm.forEach { farmerAndFarm ->
            farmerAndFarm.farmer.farm = farmerAndFarm.farm
            farmer.add(farmerAndFarm.farmer)
        }
        liveDataFarmer.value = farmer
        return liveDataFarmer
    }

    @Insert
    abstract suspend fun _insertFarmer(farmer: Farmer)

    @Insert
    abstract suspend fun _insertFarm(farm: Farm)

    @Transaction
    @Query("SELECT * FROM farmer")
    abstract fun _getAllFarmersAndTheirFarm(): List<FarmerAndFarm>

    private suspend fun insertFarmWithFarmer(farmer: Farmer, farm: Farm){
        farm.farmerFarmId = farmer.farmerId
        _insertFarm(farm)
    }
}