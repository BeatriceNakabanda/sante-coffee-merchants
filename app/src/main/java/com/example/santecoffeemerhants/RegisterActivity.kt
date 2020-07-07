package com.example.santecoffeemerhants

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.RegionalManagerViewModel
import java.util.*

class RegisterActivity : AppCompatActivity(){
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextRegion: EditText
    private lateinit var mGenderSpinner: Spinner
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit  var regionalManagerViewModel: RegionalManagerViewModel


    private val genderUnknown = "Unknown"
    private val genderMale = "Female"
    private val genderFemale = "Male"
    private var mGender = genderUnknown
    private var regionalManager: RegionalManager? = null

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

        setUpGenderSpinner()

        regionalManagerViewModel = ViewModelProvider(this).get(RegionalManagerViewModel::class.java)

        val button = findViewById<Button>(R.id.signUpButton)
        button.setOnClickListener {
            val name = editTextName.getText().toString().trim()
            val email = editTextEmail.getText().toString().trim()
            val region = editTextRegion.getText().toString().trim()
            val password = editTextPassword.getText().toString().trim()
            val confirmPassword = editTextConfirmPassword.getText().toString().trim()

            val regionalManager1 = RegionalManager(
                name = name,
                gender = mGender,
                email = email,
                region = region,
                password = password,
                createdAt = Date()
            )
            val regionalManager1Email = regionalManager1.email

            regionalManagerViewModel.insert(regionalManager1)
            val returnedRegionalManager = regionalManagerViewModel.getRegionalMangerByEmail(regionalManager1Email)

            if (returnedRegionalManager != null){
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