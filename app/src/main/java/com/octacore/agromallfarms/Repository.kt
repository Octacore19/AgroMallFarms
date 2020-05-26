package com.octacore.agromallfarms

import com.octacore.agromallfarms.data.dao.FarmerDao
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer

class Repository(private val farmerDao: FarmerDao) {

    val farmersAndFarm = farmerDao.getAllFarmersAndTheirFarm()

    val farmers = farmerDao.getAllFarmers()

    val farms = farmerDao.getAllFarms()

    suspend fun insertFarmerAndFarm(farmer: Farmer, farm: Farm){
        farmerDao.insertFarmerAndFarm(farmer, farm)
    }
}