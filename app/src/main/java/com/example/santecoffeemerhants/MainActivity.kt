package com.example.santecoffeemerhants

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.FarmerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar_main.*


class MainActivity : AppCompatActivity()  {
    private lateinit var regionalManager: RegionalManager
    private lateinit var farmerViewModel: FarmerViewModel
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adapterNavigation: NavigationListAdapter

    private var items = arrayListOf(
        NavigationItemModel("Farmers"),
        NavigationItemModel("Managers")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        //set toolbar
        setSupportActionBar(activity_main_toolbar)

        //setup recyclerview's layout
        navigation_recyclerView.layoutManager = LinearLayoutManager(this)
        navigation_recyclerView.setHasFixedSize(true)

        //Add item touch listener
        navigation_recyclerView.addOnItemTouchListener(NavigationItemTouchListener(this, object : ClickListener{
            override fun onClick(view: View, position: Int) {
                when(position) {
                    0 -> {
                        //Farmers Activity
                        setUpFarmersList()
                    }
                    1 -> {
                        //Managers Activity
                        val intent = Intent(this@MainActivity, ManagersListActivity::class.java)
                        intent.putExtra("Regional_Manager", regionalManager)
                        startActivity(intent)
                    }
                }
            }

        }))
        regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager
//        Toast.makeText(
//            this,
//            "Welcome ${regionalManager.name}",
//            Toast.LENGTH_SHORT
//        ).show()
        managerName.text = regionalManager.name
        managerRegion.text = regionalManager.region

        // Update Adapter with item data and highlight the default menu item (Farmers activity)
        updateAdapter(0)
        //set farmers activity as default activity when app starts
        //...
        setUpFarmersList()

        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout,
            activity_main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

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
    private fun setUpFarmersList(){

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
        farmerViewModel.getAllFarmersByRegionalManagerId(regionalManager.regional_manager_id).observe(this, Observer { farmers ->
            adapter.setFarmers(farmers)
        })

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Farmers"


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
    private fun updateAdapter(highlightItemPos: Int) {
        adapterNavigation = NavigationListAdapter(items, highlightItemPos)
        navigation_recyclerView.adapter = adapterNavigation
        adapterNavigation.notifyDataSetChanged()
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

}