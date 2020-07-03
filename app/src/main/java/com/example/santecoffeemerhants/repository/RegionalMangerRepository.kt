package com.example.santecoffeemerhants.repository

import androidx.lifecycle.LiveData
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.RegionalManager

class RegionalMangerRepository private constructor(private val regionalManagerDao: RegionalManagerDao){
//    private val regionalManagerAccount: LiveData<RegionalManager>? = null
    //Check if password for an email exists
    fun isValidAccount(email: String, password: String): Boolean{
        val regionalManagerAccount = regionalManagerDao.getRegionalManagerByEmail(email)
        return regionalManagerAccount.password == password
    }
    fun insertRegionalManager(regionalManager: RegionalManager){
        regionalManagerDao.insert(regionalManager)
    }

    companion object {
        private var instance: RegionalMangerRepository? = null

        fun getInstance(regionalManagerDao: RegionalManagerDao): RegionalMangerRepository {
            if (instance == null) {
                instance = RegionalMangerRepository(regionalManagerDao)
            }
            return instance!!
        }
    }
}