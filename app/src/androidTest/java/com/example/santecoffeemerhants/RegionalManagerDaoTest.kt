package com.example.santecoffeemerhants

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.SanteRoomDatabase
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class RegionalManagerDaoTest {
    private lateinit var regionalManagerDao: RegionalManagerDao
    private lateinit var database: SanteRoomDatabase

    private val regionalManager1 = RegionalManager(
        name = "Sande",
        gender = "Male",
        region = "Lwengo",
        password = "12345",
        email = "sande@gmail.com",
        createdAt = Date()
    )



    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, SanteRoomDatabase::class.java).build()
        regionalManagerDao = database.regionalManagerDao()

        regionalManagerDao.insert(regionalManager1)
    }

    @After fun closeDb() {
        database.close()
    }

    @Test
    fun testGetRegionalManager() {
        val email = regionalManager1.email

        val regionalManager = regionalManagerDao.getEmail(email = email)
        assertThat(regionalManager, equalTo(regionalManager1))


    }

}

