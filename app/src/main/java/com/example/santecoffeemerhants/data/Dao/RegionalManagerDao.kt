package com.example.santecoffeemerhants.data.Dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.santecoffeemerhants.data.Entity.RegionalManager

@Dao
interface RegionalManagerDao {
    @Query("SELECT * FROM regional_manager WHERE regional_manager.id LIKE :email")
    fun getRegionalManager(email: String): RegionalManager

//    @Query("SELECT * FROM regional_manager")
//    fun getAllRegionalMangers():LiveData<List<RegionalManager>>

    @Query("SELECT * FROM regional_manager WHERE regional_manager.id LIKE :email")
    fun getEmail(email: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(regionalManager: RegionalManager)

}