package com.example.santecoffeemerhants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.FarmerViewModel
import kotlinx.android.synthetic.main.activity_new_farmer.*
import java.util.*

class NewFarmerActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var mGenderSpinner: Spinner
    private lateinit var farmerViewModel: FarmerViewModel
    private var regionalManager: RegionalManager? = null
    private lateinit var birthCertificate:  String

    private val genderUnknown = "Unknown"
    private val genderMale = "Female"
    private val genderFemale = "Male"
    private var mGender = genderUnknown


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_farmer)

        editTextName = findViewById(R.id.farmerNameEditText)
        phoneNumber = findViewById(R.id.phoneEditText)
        mGenderSpinner = findViewById(R.id.farmerGenderSpinner)

        farmerViewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)

        setUpGenderSpinner()

        captureNationalIdButton.setOnClickListener {
            val intent = Intent(this, CaptureDocumentActivity::class.java)
            startActivity(intent)

        }
        captureBirthCertificateButton.setOnClickListener {
            val intent = Intent(this, CaptureDocumentActivity::class.java)
            startActivity(intent)
        }
        regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager
        val email = regionalManager?.email
        val name = regionalManager?.name
        val regionalManagerId = regionalManager?.regional_manager_id!!
        Toast.makeText(
            this,
            "User Email: $email \n User Id: $regionalManagerId \n UserName: $name",
            Toast.LENGTH_SHORT
        ).show()

        //Create new farmer
        val addFarmerButton = findViewById<Button>(R.id.addFarmerButton)
        addFarmerButton.setOnClickListener {
            val name = editTextName.getText().toString().trim()
            val phoneNumber = phoneNumber.getText().toString().trim()
//            val filePath = intent.getStringExtra("Directory")
//            birthCertificate = filePath
            val savedPhotoUri = intent?.getStringExtra("Photo_uri").toString()
            birthCertificate = savedPhotoUri
//            birthCertificate = intent.getStringExtra("Photo_uri")

            val newFarmer = Farmer(
                manager_id = regionalManagerId,
                name = name,
                phone_number = phoneNumber,
                gender = mGender,
//                birth_certificate = birthCertificate,
                createdAt = Date()
            )
            val newFarmerCreatedAt = newFarmer.createdAt
            val phoneNo = newFarmer.phone_number
            farmerViewModel.insert(newFarmer)
//            val addedFarmer = farmerViewModel.getFarmerByDateAndTimeCreated(newFarmer.createdAt)
            val addedFarmer = farmerViewModel.getFarmerByPhoneNumber(phoneNo)
            val addedFarmerPhoneNo = addedFarmer.phone_number
            val addedFarmerDateCreated = addedFarmer.createdAt

//            if(phoneNo == addedFarmerPhoneNo){
//                Toast.makeText(
//                    this,
//                    "Farmer successfully added \n FarmerId: ${addedFarmer.farmer_id} \n farmer name: ${addedFarmer.name}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }else{
//                Toast.makeText(
//                    this,
//                    "Farmer not added",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            if (addedFarmer != null) {
                Toast.makeText(
                    this,
                    "Farmer successfully added \n FarmerId: ${addedFarmer.farmer_id} \n farmer name: ${addedFarmer.name} \n farmer birthCertificate: ",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Farmer not added",
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
                        findViewById<View>(R.id.farmerInvalidGenderTextView) as TextView
                    invalidGenderText.visibility = View.GONE

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    mGender
                }
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

