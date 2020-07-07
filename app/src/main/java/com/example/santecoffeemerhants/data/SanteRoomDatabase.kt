package com.example.santecoffeemerhants.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.santecoffeemerhants.data.Dao.FarmerDao
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.data.converter.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.chrono.HijrahChronology.INSTANCE

@Database(entities = [RegionalManager::class, Farmer::class], version = 2)
@TypeConverters(Converters::class)
abstract class SanteRoomDatabase: RoomDatabase() {

    abstract fun regionalManagerDao(): RegionalManagerDao
    abstract fun farmerDao(): FarmerDao

companion object{
    // Singleton prevents multiple instances of database opening at the
    // same time.
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
            )   .allowMainThreadQueries()
                .build()
            INSTANCE = instance
            return instance
        }
    }
}
}
