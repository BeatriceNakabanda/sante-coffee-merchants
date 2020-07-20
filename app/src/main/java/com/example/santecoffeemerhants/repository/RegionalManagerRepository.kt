package com.example.santecoffeemerhants.viewmodel

import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.RegionalManager

class RegionalManagerRepository constructor(private val regionalManagerDao: RegionalManagerDao){
    val allRegionalManagers: LiveData<List<RegionalManager>> = regionalManagerDao.getAllRegionalMangers()

    fun insertRegionalManager(regionalManager: RegionalManager){
        regionalManagerDao.insert(regionalManager)
    }
    fun getRegionalManager(email: String, password: String): RegionalManager{
        val regionalManager = regionalManagerDao.getRegionalManager(email, password)
        return regionalManager
    }
    fun getRegionalManagerByEmail(email: String) {
        regionalManagerDao.getRegionalManagerByEmail(email)
    }
    fun getRegionalManagerByEmailAndPassword(regionalManager: RegionalManager){
        val email = regionalManager.email
        val password = regionalManager.password
        regionalManagerDao.getRegionalManagerByEmailAndPassword(email, password)
    }
    fun getAllRegionalManagers(){
        regionalManagerDao.getAllRegionalMangers()
    }
    //Check if password for an email exists
    fun isValidAccount(email: String, password: String): Boolean{
        val regionalManagerAccount = regionalManagerDao.getRegionalManager(email, password)
        return regionalManagerAccount.password == password
    }
    fun getRegionalManagerDetails(email: String): RegionalManager {
        val regionalManagerAccount = regionalManagerDao.getRegionalManagerByEmail(email)
        return regionalManagerAccount
    }

}