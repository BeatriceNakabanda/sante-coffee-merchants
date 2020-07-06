package com.example.santecoffeemerhants

import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
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
class RegionalManagerDaoTest {

    private var regionalManagerDao: RegionalManagerDao? = null
    private lateinit var db: SanteRoomDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = inMemoryDatabaseBuilder(context, SanteRoomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        regionalManagerDao = db.regionalManagerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @Test
    fun testInsertRegionalManager(){
        //Arrange
        val regionalManager1 = RegionalManager(
            name = "liz",
            gender = "Male",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )
        val email = regionalManager1.email
        //Act
        regionalManagerDao?.insert(regionalManager1)
        val returnedRegionalManage = regionalManagerDao?.getRegionalManagerByEmail(email)
        val returnedEmail = returnedRegionalManage?.email
        //Assert
        assertThat(returnedEmail, equalTo(email))
    }
    @Test
    fun testGetRegionalManagerByEmail() {
        //Arrange
        val regionalManager1 = RegionalManager(
            name = "liz",
            gender = "Male",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )
        val email = regionalManager1.email
        regionalManagerDao?.insert(regionalManager1)

        //Act
        val regionalManager = email?.let { regionalManagerDao?.getRegionalManagerByEmail(it) }
        val returnedRegionalManagerEmail = regionalManager?.email

        //Assert
        assertThat(returnedRegionalManagerEmail, equalTo(email))

    }

    @Test
    fun getAllRegionalMangersTest(){
        //Arrange
        val regionalManager1 = RegionalManager(
            name = "liz",
            gender = "Female",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )
        val regionalManager2 = RegionalManager(
            name = "Sande",
            gender = "Male",
            email = "sande@gmail.com",
            region = "Bbunga",
            password = "12345",
            createdAt = Date()
        )
        regionalManagerDao?.insert(regionalManager1)
        //Act
        val regionalManagers = regionalManagerDao?.getAllRegionalMangers()
        val regionalManagerList = (regionalManagerDao?.getAllRegionalMangers())?.value

        //Assert
        if (regionalManagerList != null) {
            assertThat(regionalManagerList.size, equalTo(3))
        }
    }
    @Test
    fun getRegionalManagerByEmailAndPassword(){
        //Arrange
        val regionalManager1 = RegionalManager(
            name = "liz",
            gender = "Male",
            email = "liz@gmail.com",
            region = "Lwengo",
            password = "12345",
            createdAt = Date()
        )
        val email = regionalManager1.email
        val password = regionalManager1.password

        regionalManagerDao?.insert(regionalManager1)

        //Act
        val regionalManager =
            regionalManagerDao?.getRegionalManagerByEmailAndPassword(email, password)

        val returnedRegionalManagerEmail = regionalManager?.email
        val returnedRegionalManagerPassword = regionalManager?.password

        //Assert
        assertThat(returnedRegionalManagerEmail, equalTo(email))
        assertThat(returnedRegionalManagerPassword, equalTo(password))
    }
}