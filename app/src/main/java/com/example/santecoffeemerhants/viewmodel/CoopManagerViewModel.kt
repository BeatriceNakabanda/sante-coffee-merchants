package com.example.santecoffeemerhants.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Entity.CooperativeManager
import com.example.santecoffeemerhants.data.SanteRoomDatabase
import com.example.santecoffeemerhants.repository.CoopManagerRepository
import java.util.*

class CoopManagerViewModel(application: Application): AndroidViewModel(application)  {
    private val repository: CoopManagerRepository
    val allCooperativeManagers: LiveData<List<CooperativeManager>>

    init {
        val coopManagerDao = SanteRoomDatabase.getDatabase(application).coopManagerDao()
        repository = CoopManagerRepository(coopManagerDao)
        allCooperativeManagers = repository.allCooperativeManagers
    }
    fun insert(cooperativeManager: CooperativeManager){
        repository.insertNewCoopManager(cooperativeManager)
    }
    fun getCooperativeManagerByCreatedAt(created_at: Date?): CooperativeManager?{
        return repository.getCooperativeManagerByCreatedAt(created_at)
    }
    fun update(cooperativeManager: CooperativeManager){
        repository.updateCoopManager(cooperativeManager)
    }
    fun getCoopManagersByRegionalManager(regionalManagerId: Int): LiveData<List<CooperativeManager>>{
        return repository.getAllCooperativeManagersByRegionalManager(regionalManagerId)
    }
}