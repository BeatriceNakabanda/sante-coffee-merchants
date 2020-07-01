package com.example.santecoffeemerhants.data.Entity

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.converter.Converters
import java.io.Serializable
import java.util.*


@Entity(tableName = "regional_manager")
data class RegionalManager(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var regional_manager_id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "region") var region: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(Converters::class)
    var createdAt: Date?
    )
    :Serializable, RegionalManagerDao {

    override fun getRegionalManager(email: String): RegionalManager {
        TODO("Not yet implemented")
//        var regionalManager = RegionalManager(regional_manager_id=this.regional_manager_id,
//            name = this.name, gender = this.gender, email = this.email,
//            region = this.region, password = this.password, createdAt = this.createdAt)
//        return regionalManager
    }

//    override fun getAllRegionalMangers(): LiveData<List<RegionalManager>> {
//        TODO("Not yet implemented")
//    }

    override fun getEmail(email: String): String {
        return this.email
    }

    override fun insert(regionalManager: RegionalManager) {
        TODO("Not yet implemented")
    }

}