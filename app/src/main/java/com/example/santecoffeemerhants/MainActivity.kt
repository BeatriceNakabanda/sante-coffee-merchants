package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity()  {
    private lateinit var regionalManager: RegionalManager
    private lateinit var user: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager
        user = findViewById(R.id.User)

//        if (regionalManager != null){
//            user.setText("Welcome " + regionalManager.name)
//        }
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle("Farmers")
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NewFarmerActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        return true
    }
}