package com.example.santecoffeemerhants.repository

import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Dao.CoopManagerDao
import com.example.santecoffeemerhants.data.Entity.CooperativeManager
import java.util.*

class CoopManagerRepository constructor(private val coopManagerDao: CoopManagerDao) {
    val allCooperativeManagers: LiveData<List<CooperativeManager>> = coopManagerDao.allCooperativeManagers()

    fun getAllCooperativeManagersByRegionalManager(regionalManagerId: Int): LiveData<List<CooperativeManager>>{
        return coopManagerDao.getAllCooperativeManagersByRegionalManager(regionalManagerId)
    }
    fun getCooperativeManagerByCreatedAt(created_at: Date?): CooperativeManager{
        return coopManagerDao.getCooperativeManagerByCreatedAt(created_at)
    }
    fun insertNewCoopManager(cooperativeManager: CooperativeManager){
        coopManagerDao.insert(cooperativeManager)
    }
    fun updateCoopManager(cooperativeManager: CooperativeManager){
        coopManagerDao.updateFarmer(cooperativeManager)
    }
}