package com.octacore.agromallfarms.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.octacore.agromallfarms.Repository
import com.octacore.agromallfarms.data.FarmersDatabase
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.model.FarmerAndFarm

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: Repository
    val farmersAndFarm: LiveData<List<FarmerAndFarm>>
    val farmers: LiveData<List<Farmer>>
    val farms: LiveData<List<Farm>>

    init {
        val farmerDao = FarmersDatabase.getDatabase(app).farmerDao()
        repo = Repository(farmerDao)
        farmersAndFarm = repo.farmersAndFarm
        farmers = repo.farmers
        farms = repo.farms
    }

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is Dashboard Fragment"
    }
    val text: LiveData<String> = _text*/

}