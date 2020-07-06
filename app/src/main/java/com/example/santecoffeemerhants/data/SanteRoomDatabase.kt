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

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: SanteRoomDatabase? = null

//        fun getInstance(context: Context): SanteRoomDatabase {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
        fun getDatabase(
    context: Context
): SanteRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SanteRoomDatabase::class.java,
                    "sante_database"
                )
                    .build()
                Companion.instance = instance
                // return instance
                instance
            }
        }

    }

}