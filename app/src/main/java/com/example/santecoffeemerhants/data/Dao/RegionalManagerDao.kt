package com.example.santecoffeemerhants.data.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.santecoffeemerhants.data.Entity.RegionalManager

interface RegionalManagerDao {
    @Query("SELECT * FROM regional_manager WHERE email = :email AND password= :password")
    fun getRegionalManager(email: String, password: String): RegionalManager

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(regionalManager: RegionalManager)
}