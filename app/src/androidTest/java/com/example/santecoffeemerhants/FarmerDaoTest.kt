package com.example.santecoffeemerhants

import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.santecoffeemerhants.data.Dao.FarmerDao
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.data.SanteRoomDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class FarmerDaoTest  {
    private var farmerDao: FarmerDao ? = null
    private lateinit var db: SanteRoomDatabase
    private var regionalManagerDao: RegionalManagerDao? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SanteRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        farmerDao = db.farmerDao()
        regionalManagerDao = db.regionalManagerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertFarmer(){
        //Arrange
        val regionalManager = RegionalManager(
            name = "liz",
            gender = "Male",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )

        val newFarmer = Farmer(
            manager_id = 1,
            name = "William",
            phone_number = "0770990978",
            gender = "Male",
            birth_certificate = "file:///storage/emulated/0/Android/media/com.example.santecoffeemerhants/Sante%20Coffee%20Merhants/2020-07-13-08-49-07-286.jpg",
            national_id = "file:///storage/emulated/0/Android/media/com.example.santecoffeemerhants/Sante%20Coffee%20Merhants/2020-07-13-08-49-07-286.jpg",
            createdAt = Date()
        )
        val phoneNo = newFarmer.phone_number
        val newFarmerCreatedAt = newFarmer.createdAt
        regionalManagerDao?.insert(regionalManager)

        //Act
        farmerDao?.insert(newFarmer)

        val addedFarmer = farmerDao?.getFarmerByDateAndTimeCreated(newFarmerCreatedAt)
        val addedFarmerCreatedAt = addedFarmer?.createdAt
        //Assert
        assertThat(newFarmerCreatedAt, equalTo(addedFarmerCreatedAt))
    }
//    @Test
//

    @Test
    fun testGetFarmersByRegionalManagerID(){
        //Arrange
        val regionalManager = RegionalManager(
            name = "liz",
            gender = "Male",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )
        val newFarmer1 = Farmer(
            manager_id = 1,
            name = "William",
            phone_number = "0770990978",
            gender = "Male",
            birth_certificate = "file:///storage/emulated/0/Android/media/com.example.santecoffeemerhants/Sante%20Coffee%20Merhants/2020-07-13-08-49-07-286.jpg",
            national_id = "file:///storage/emulated/0/Android/media/com.example.santecoffeemerhants/Sante%20Coffee%20Merhants/2020-07-13-08-49-07-286.jpg",
            createdAt = Date()
        )
        regionalManagerDao?.insert(regionalManager)
        farmerDao?.insert(newFarmer1)

        //Act
        farmerDao?.getAllFarmersByRegionalManagerId(1)
        val farmersList= farmerDao?.getAllFarmersByRegionalManagerId(1)?.value


        //Assert
        if (farmersList != null){
            assertThat(farmersList.size, equalTo(1))
        }
    }

}


