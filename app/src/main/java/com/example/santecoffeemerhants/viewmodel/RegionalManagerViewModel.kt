package com.example.santecoffeemerhants.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.data.SanteRoomDatabase

class RegionalManagerViewModel(application: Application): AndroidViewModel(application) {
    private val repository: RegionalManagerRepository


    val allRegionalManagers: LiveData<List<RegionalManager>>

    init {
        val regionalManagerDao = SanteRoomDatabase.getDatabase(application).regionalManagerDao()
        repository = RegionalManagerRepository(regionalManagerDao)
        allRegionalManagers = repository.allRegionalManagers
    }
    fun insert(regionalManager: RegionalManager){
        repository.insertRegionalManager(regionalManager)
    }
    fun getAllRegionalManagersByRegion(region: String): LiveData<List<RegionalManager>>{
        return repository.getRegionalManagerByRegion(region)
    }
    fun getRegionalManager(email: String, password: String): RegionalManager{
        return repository.getRegionalManager(email, password)
    }
    fun getRegionalMangerByEmail(regionalManager: String){
        repository.getRegionalManagerByEmail(regionalManager)
    }
    fun checkIfValidAccount(email: String, password: String): Boolean{
        return repository.isValidAccount(email, password)
    }
    fun getRegionalManagerDetails(email: String): RegionalManager{
        return repository.getRegionalManagerDetails(email)
    }
}