package com.octacore.agromallfarms.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.octacore.agromallfarms.Repository
import com.octacore.agromallfarms.data.FarmersDatabase
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.model.FarmerAndFarm

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: Repository
    val farmers: LiveData<List<FarmerAndFarm>>

    init {
        val farmerDao = FarmersDatabase.getDatabase(app).farmerDao()
        repo = Repository(farmerDao)
        farmers = repo.farmers
    }

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is Dashboard Fragment"
    }
    val text: LiveData<String> = _text*/

}