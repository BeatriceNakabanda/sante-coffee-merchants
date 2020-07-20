package com.example.santecoffeemerhants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.FarmerViewModel
import kotlinx.android.synthetic.main.activity_new_farmer.*
import java.util.*

class NewFarmerActivity : AppCompatActivity() {
    private val BIRTH_CERT_CODE = 101
    private val NATIONAL_ID_CODE = 102
    private lateinit var editTextName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var mGenderSpinner: Spinner
    private lateinit var farmerViewModel: FarmerViewModel
    private var regionalManager: RegionalManager? = null

    //    private lateinit var birthCertificate:  String
    private lateinit var savedUri: String

    private val genderUnknown = 0
    private val genderMale = 1
    private val genderFemale = 2

    private var mGender = genderUnknown
    private var farmer: Farmer? = null

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == BIRTH_CERT_CODE) {
                val intent = result.data
                // Handle the Intent
                val extras = intent?.extras
                if (extras != null && extras.containsKey("Birth_Cert_Uri")) {
                    savedUri = intent.getStringExtra("Photo_Uri") as String
                    Toast.makeText(
                        this,
                        "saved uri: $savedUri",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "saved uri null",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_farmer)
        editTextName = findViewById(R.id.farmerNameEditText)
        phoneNumber = findViewById(R.id.phoneEditText)
        mGenderSpinner = findViewById(R.id.farmerGenderSpinner)

        farmerViewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)

        setUpGenderSpinner()

        captureNationalIdButton.setOnClickListener {
            startForResult.launch(Intent(this, CaptureDocumentActivity::class.java))

        }
        captureBirthCertificateButton.setOnClickListener {
            startForResult.launch(Intent(this, CaptureDocumentActivity::class.java))
        }

        farmer = intent?.extras?.get("farmer") as Farmer?

        if (farmer == null) {
            captureBirthCertificateButton.visibility = View.VISIBLE
            captureNationalIdButton.visibility = View.VISIBLE
            addNewFarmerTextView.visibility = View.VISIBLE
            //Get details of logged in regional manager
            regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager?

            val regionalManagerId = regionalManager?.regional_manager_id
            Toast.makeText(
                this,
                " $regionalManagerId",
                Toast.LENGTH_SHORT
            ).show()

            //Create new farmer
            val addFarmerButton = findViewById<Button>(R.id.addFarmerButton)
            addFarmerButton.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val phoneNumber = phoneNumber.text.toString().trim()
                val birthCertificate = savedUri
                val nationalId = savedUri

                when (regionalManagerId) {
                    null -> {
                        return@setOnClickListener
                    }
                    else -> {

                        val newFarmer = Farmer(
                            manager_id = regionalManagerId,
                            name = name,
                            phone_number = phoneNumber,
                            gender = mGender,
                            birth_certificate = birthCertificate,
                            national_id = nationalId,
                            createdAt = Date()
                        )

                        farmerViewModel.insert(newFarmer)

                        val addedFarmer =
                            farmerViewModel.getFarmerByDateAndTimeCreated(newFarmer.createdAt)

                        if (addedFarmer != null) {
                            val addedFarmerDateCreated = addedFarmer.createdAt

                            Toast.makeText(
                                this,
                                "Farmer successfully added. BirthCertificate ${addedFarmer.birth_certificate} \n" +
                                        " farmer Name: ${addedFarmer.name}",
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
            }
        } else {
            editNewFarmerTextView.visibility = View.VISIBLE
            previewNationalIdTextView.visibility = View.VISIBLE
            previewBirthCertificateTextView.visibility = View.VISIBLE

            editNewFarmerTextView.visibility = View.VISIBLE
            val regionalManagerId = farmer?.manager_id
            Toast.makeText(
                this,
                " $regionalManagerId ",
                Toast.LENGTH_SHORT
            ).show()
            editTextName.setText(farmer?.name)
            phoneNumber.setText(farmer?.phone_number)
            farmer?.gender?.let { mGenderSpinner.setSelection(it) }

            //Edit farmer
            val addFarmerButton = findViewById<Button>(R.id.addFarmerButton)
            addFarmerButton.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val phoneNumber = phoneNumber.text.toString().trim()
//                birthCertificate = photoUri
//                val nationalId = photoUri

                farmer?.name = name
                farmer?.gender = mGender
                farmer?.phone_number = phoneNumber
                farmer?.birth_certificate = savedUri
                farmer?.national_id = savedUri

                farmerViewModel.updateFarmer(farmer)

                val updatedFarmer = farmerViewModel.getSingleFarmer(farmer!!.farmer_id)


                if (updatedFarmer != null) {
                    Toast.makeText(
                        this,
                        "Farmer successfully updated",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Farmer not updated",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

        }


    }

    private fun setUpGenderSpinner() {
        if (mGenderSpinner != null) {
            //Create adapter for spinner
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.array_gender_options,
                android.R.layout.simple_spinner_item
            )
            // Specify dropdown layout style - simple list view with 1 item per line
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            // Apply the adapter to the spinner
            mGenderSpinner.adapter = adapter

            mGenderSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
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
        val id: Int = item.itemId
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