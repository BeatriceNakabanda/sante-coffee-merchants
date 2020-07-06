package com.example.santecoffeemerhants

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.data.SanteRoomDatabase
import com.example.santecoffeemerhants.repository.RegionalManagerRepository
import com.example.santecoffeemerhants.viewmodel.RegionalManagerViewModel
import java.util.*

class RegisterActivity : AppCompatActivity(){
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextRegion: EditText
    private lateinit var mGenderSpinner: Spinner
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private val managerDao: RegionalManagerDao? = null
//    private lateinit var db: SanteRoomDatabase
    private var regionalManagerDao: RegionalManagerDao? = null
    private var dataBase: SanteRoomDatabase? = null
//    private lateinit var regionalManagerDao: RegionalManagerDao
    private  var regionalManagerViewModel: RegionalManagerViewModel? = null

    private val genderUnknown = "Unknown"
    private val genderMale = "Female"
    private val genderFemale = "Male"
    private var mGender = genderUnknown
    private var regionalManager: RegionalManager? = null

    private lateinit var regionalManagerRepository: RegionalManagerRepository
    private val regionalManagerId = regionalManager?.regional_manager_id


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        // hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the title bar
        getSupportActionBar()?.hide();
        //enable full screen
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //inflate layout
        setContentView(R.layout.activity_register)

        editTextName = findViewById(R.id.nameEditText)
        editTextEmail = findViewById(R.id.registerEmailAddressEditText)
        editTextRegion = findViewById(R.id.regionEditText)
        mGenderSpinner = findViewById(R.id.genderSpinner)
        editTextPassword = findViewById(R.id.registerPasswordEditText)
        editTextConfirmPassword = findViewById(R.id.confirmPasswordEditText)

//        regionalManagerDao = db.regionalManagerDao()


        setUpGenderSpinner()
//        insertRegionalManager()
        val button = findViewById<Button>(R.id.signUpButton)
        button.setOnClickListener {
            val name = editTextName.getText().toString().trim()
            val email = editTextEmail.getText().toString().trim()
            val region = editTextRegion.getText().toString().trim()
            val password = editTextPassword.getText().toString().trim()
            val confirmPassword = editTextConfirmPassword.getText().toString().trim()

            regionalManagerDao =
                Room.databaseBuilder<SanteRoomDatabase>(this, SanteRoomDatabase::class.java, "sante_database.db")
                    .allowMainThreadQueries()
                    .build().regionalManagerDao()

            val regionalManager1 = RegionalManager(
                name = name,
                gender = mGender,
                email = email,
                region = region,
                password = password,
                createdAt = Date()
            )
            val regionalManager1Email = regionalManager1.email

            regionalManagerDao?.insert(regionalManager1)
            val returned = regionalManagerDao?.getRegionalManagerByEmail(regionalManager1.email)
            val returnedEmail = returned?.email

            if (regionalManager1Email == returnedEmail){
                Toast.makeText(
                    this,
                    "Successfully added regional manager",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    this,
                    "Regional manager not added",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }
    private fun insertRegionalManager(){
        val button = findViewById<Button>(R.id.signUpButton)
        button.setOnClickListener{
            val name = editTextName.getText().toString().trim()
            val email = editTextEmail.getText().toString().trim()
            val region = editTextRegion.getText().toString().trim()
            val password = editTextPassword.getText().toString().trim()
            val confirmPassword = editTextConfirmPassword.getText().toString().trim()

            val regionalManager = RegionalManager(
                name = name,
                email = email,
                region = region,
                gender = mGender,
                password = password,
                createdAt = Date()
            )
            val regionalManager1 = RegionalManager(
                name = name,
                gender = mGender,
                email = email,
                region = region,
                password = password,
                createdAt = Date()
            )
//            Log.i("TAG",  regionalManager1.name)
//            Log.i("TAG",  regionalManager1.email)
//            Log.i("TAG",  regionalManager1.gender)
//            Log.i("TAG",  regionalManager1.region)
//            Log.i("TAG",  regionalManager1.createdAt.toString())
//            Log.i("TAG",  regionalManager1.password)

            regionalManagerDao?.insert(regionalManager1)



            val returnedUser = regionalManagerDao?.getRegionalManagerByEmail(regionalManager1.email)

            if (returnedUser  != null){
                Toast.makeText(
                    this,
                    "Successfully added regional manager",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    this,
                    "Regional manager not added",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
    private fun setUpGenderSpinner(){
        if (mGenderSpinner != null ){
            //Create adapter for spinner
            val adapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options,
                android.R.layout.simple_spinner_item
            )
            // Specify dropdown layout style - simple list view with 1 item per line
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            // Apply the adapter to the spinner
            mGenderSpinner.adapter = adapter

            mGenderSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    val selection =
                        parent.getItemAtPosition(position) as String
                    if (!TextUtils.isEmpty(selection)) {
                        mGender = when (selection) {
                            getString(R.string.gender_male) -> {
                                genderMale
                            }
                            getString(R.string.gender_female) -> {
                                genderFemale
                            }
                            else -> {
                                genderUnknown
                            }
                        }
                    }
                    val invalidGenderText =
                        findViewById<View>(R.id.invalidGenderTextView) as TextView
                    invalidGenderText.visibility = View.GONE

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    mGender
                }
            }
        }
    }
}