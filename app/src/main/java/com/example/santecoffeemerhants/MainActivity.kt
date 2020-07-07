package com.example.santecoffeemerhants

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.santecoffeemerhants.data.Entity.RegionalManager

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

    }
}