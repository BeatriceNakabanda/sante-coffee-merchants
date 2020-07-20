package com.example.santecoffeemerhants

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val name = "liz"
    private val gender = "Male"
    private val email = "liz@gmail.com"
    private val region = "Lwengo"
    private val password = "12345"

    @get: Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

//    @Test
//    fun clickFAB_OpensAddNewFarmerActivity(){
//        //Perform click
//        onView(withId(R.id.fab)).perform(click())
//        //Check if opened activity has text
//        onView(withText("Add New Farmer")).check(matches(isDisplayed()))
//
//        //Add farmer
//
//
//    }

//    @Test
//    fun onOpenMainActivityLoadsListOfFarmers(){
//
//    }


}