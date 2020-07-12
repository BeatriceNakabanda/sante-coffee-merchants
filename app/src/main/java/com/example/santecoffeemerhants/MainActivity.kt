package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_new_farmer.*

class MainActivity : AppCompatActivity()  {
    private var regionalManager: RegionalManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle("Farmers")
        }
        regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager
        val email = regionalManager?.email
        val name = regionalManager?.name
        val regionalManagerId = regionalManager?.regional_manager_id
        Toast.makeText(
            this,
            "User Email: $email \n User Id: $regionalManagerId \n UserName: $name",
            Toast.LENGTH_SHORT
        ).show()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NewFarmerActivity::class.java)
            intent.putExtra("Regional_Manager", regionalManager)
            startActivity(intent)

        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        return true
    }
}