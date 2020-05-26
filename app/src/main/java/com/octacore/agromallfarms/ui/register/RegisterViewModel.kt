package com.octacore.agromallfarms.ui.register

import android.app.Application
import androidx.lifecycle.*
import com.octacore.agromallfarms.Repository
import com.octacore.agromallfarms.data.FarmersDatabase
import com.octacore.agromallfarms.model.Farmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: Repository

    init {
        val farmerDao = FarmersDatabase.getDatabase(app).farmerDao()
        repo = Repository(farmerDao)
    }

    fun registerFarmer(farmer: Farmer) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertFarmer(farmer)
    }

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is Register Fragment"
    }
    val text: LiveData<String> = _text*/
}