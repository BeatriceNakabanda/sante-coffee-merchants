package com.example.santecoffeemerhants

import android.util.Log
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
    private lateinit var  farmerDao: FarmerDao
    private lateinit var db: SanteRoomDatabase
    private lateinit var regionalManagerDao: RegionalManagerDao
    private lateinit var regionalManager: RegionalManager
    private lateinit var newFarmer: Farmer
    private lateinit var createdManager: RegionalManager

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

        regionalManager = RegionalManager(
            name = "liz",
            gender = "Male",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )
        regionalManagerDao.insert(regionalManager)
        createdManager = regionalManagerDao.getRegionalManagerByEmail(regionalManager.email)

        newFarmer = Farmer(
            manager_id = createdManager.regional_manager_id,
            name = "William",
            phone_number = "0770990978",
            gender = 1,
            birth_certificate = "file:///storage/emulated/0/Android/media/com.example.santecoffeemerhants/Sante%20Coffee%20Merhants/2020-07-13-08-49-07-286.jpg",
            national_id = "file:///storage/emulated/0/Android/media/com.example.santecoffeemerhants/Sante%20Coffee%20Merhants/2020-07-13-08-49-07-286.jpg",
            createdAt = Date()
        )

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertFarmer(){
        //Arrange
        // Create farmer
        Log.i("newFarmerIn", "${newFarmer.farmer_id}")
        Log.i("Manager id", "${newFarmer.manager_id}")
        val newFarmerCreatedAt = newFarmer.createdAt

        //Act
        //Insert Farmer
        farmerDao.insert(newFarmer)

        val addedFarmer = farmerDao.getFarmerByDateAndTimeCreated(newFarmerCreatedAt)
        Log.i("newFarmerOut", "${addedFarmer.farmer_id}")
        val addedFarmerCreatedAt = addedFarmer.createdAt

        //Assert that added farmer is the same as farmer created
        assert(addedFarmer !=  null)
        assertThat(newFarmerCreatedAt, equalTo(addedFarmerCreatedAt))
    }
    @Test
    fun testUpdateFarmer() {
        //Arrange
        //1.  create the new farmer
        //2. insert new farmer
        farmerDao?.insert(newFarmer)

        Log.i("newFarmerIn", "${newFarmer.farmer_id}")
        Log.i("Manager id", "${newFarmer.manager_id}")

        //Act
        // 3. get new farmer from database
        val createdFarmer = farmerDao?.getFarmerByDateAndTimeCreated(newFarmer.createdAt)

        // 4. update new farmer
        createdFarmer?.name = "William shakespear"
        createdFarmer?.let { farmerDao?.updateFarmer(it) }

        val updatedFarmer = farmerDao?.getFarmerByDateAndTimeCreated(newFarmer.createdAt)

        //5. assert that new farmer in database has updated name
        assert(updatedFarmer != null)
        assertThat(updatedFarmer?.name, equalTo(createdFarmer?.name))
    }

    @Test
    fun getAllFarmers(){
        //Arrange
        //1. Create farmer
        //2. Insert Farmer
        farmerDao.insert(newFarmer)
        Log.i("Farmer id in", "${newFarmer.farmer_id}")
        val newFarmerCreatedAt = newFarmer.createdAt

        // Act
        // 3. Get all farmers by regional manager
        val allFarmers = farmerDao.getAllFarmersByRegionalManagerId(createdManager.regional_manager_id)
        val addedFarmer = farmerDao.getFarmerByDateAndTimeCreated(newFarmer.createdAt)
        val addedFarmerCreatedAt = addedFarmer.createdAt
        Log.i("Farmer id out", "${addedFarmer.farmer_id}")

        //Assert that the number of farmers is the same number of farmers creates
        assert(allFarmers != null)
        assert(addedFarmer != null )
        Log.i("All Users", "${addedFarmer}")
        assertThat(newFarmerCreatedAt, equalTo(addedFarmerCreatedAt))
    }

}



