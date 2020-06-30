package com.example.santecoffeemerhants.data

import android.content.Context
import androidx.room.*
import com.example.santecoffeemerhants.data.Dao.FarmerDao
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.data.converter.Converters

@Database(entities = [Farmer::class, RegionalManager::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SanteRoomDatabase: RoomDatabase() {

    abstract fun regionalManagerDao(): RegionalManagerDao
    abstract fun farmerDao(): FarmerDao

    companion object {
        @Volatile
        private var INSTANCE: SanteRoomDatabase? = null

        fun getDatabase(context: Context): SanteRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SanteRoomDatabase::class.java,
                    "sante_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}