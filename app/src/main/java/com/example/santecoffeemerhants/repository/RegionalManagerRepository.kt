package com.example.santecoffeemerhants.repository

import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.RegionalManager

class RegionalManagerRepository private constructor(private val regionalManagerDao: RegionalManagerDao){
    val allRegionalManagers: LiveData<List<RegionalManager>> = regionalManagerDao.getAllRegionalMangers()
    //Check if password for an email exists
    fun isValidAccount(email: String, password: String): Boolean{
        val regionalManagerAccount = regionalManagerDao.getRegionalManagerByEmail(email)
        return regionalManagerAccount.password == password
    }
    fun insertRegionalManager(regionalManager: RegionalManager){
        regionalManagerDao.insert(regionalManager)
    }

    companion object {
        private var instance: RegionalManagerRepository? = null

        fun getInstance(regionalManagerDao: RegionalManagerDao): RegionalManagerRepository {
            if (instance == null) {
                instance = RegionalManagerRepository(regionalManagerDao)
            }
            return instance!!
        }
    }
}