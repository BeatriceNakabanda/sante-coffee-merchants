package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.FarmerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity()  {
    private var regionalManager: RegionalManager? = null
    private lateinit var farmerViewModel: FarmerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager
        val email = regionalManager?.email
        val name = regionalManager?.name
        val regionalManagerId = regionalManager?.regional_manager_id
        Toast.makeText(
            this,
            "Welcome $name",
            Toast.LENGTH_SHORT
        ).show()

        //Add recyclerView and list adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FarmerListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        //Divider
        recyclerView.addItemDecoration(
            DividerItemDecoration(this,
            LinearLayoutManager.VERTICAL)
        )

        //set up live data observer
        farmerViewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)
        regionalManagerId?.let {
            farmerViewModel.getAllFarmersByRegionalManagerId(it).observe(this, Observer { farmers ->
                farmers.let { adapter.setFarmers(it) }
            })
        }


        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle("Farmers")
        }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            if (regionalManager == null) {
                return@setOnClickListener
            }
            else {
                val intent = Intent(this, FarmerActivity::class.java)
                intent.putExtra("Regional_Manager", regionalManager)
                startActivity(intent)
            }



        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.logout) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}