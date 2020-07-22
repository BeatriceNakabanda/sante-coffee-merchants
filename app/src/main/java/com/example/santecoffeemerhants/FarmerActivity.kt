package com.example.santecoffeemerhants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.example.santecoffeemerhants.utils.*
import com.example.santecoffeemerhants.viewmodel.FarmerViewModel
import kotlinx.android.synthetic.main.activity_new_farmer.*
import kotlinx.android.synthetic.main.activity_preview_image.*
import java.util.*

class FarmerActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var mGenderSpinner: Spinner
    private lateinit var farmerViewModel: FarmerViewModel

    private lateinit var savedUri: String

    private var mGender = GENDER_UNKOWN
    private  var farmer: Farmer? = null

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val intent = result.data
                    // Handle the Intent
                    val extras = intent?.extras

                    when(extras != null){
                        true -> {
                            when {
                                extras.containsKey(BIRTH_CERT_URI) -> {
                                    savedUri = intent.getStringExtra(BIRTH_CERT_URI) as String

                                    Log.i("TAG", "Birth Cert: $savedUri")
                                    Toast.makeText(
                                        this,
                                        "Saved Birth certificate",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                extras.containsKey(NATIONAL_ID_URI) -> {
                                    savedUri = intent.getStringExtra(NATIONAL_ID_URI) as String
                                    Log.i("TAG", "National Id: $savedUri")
                                    Toast.makeText(
                                        this,
                                        "Saved National Id",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                        }
                        false -> {
                            Toast.makeText(
                                this,
                                "saved uri null",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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
            val intent = Intent(this, CaptureDocumentActivity::class.java)
            intent.putExtra(IS_NATIONAL_ID, true)
            startForResult.launch(intent)

        }
        captureBirthCertificateButton.setOnClickListener {
            val intent = Intent(this, CaptureDocumentActivity::class.java)
            intent.putExtra(IS_BIRTH_CERT, true)
            startForResult.launch(intent)
        }
        farmer = intent?.extras?.get("farmer") as Farmer?

        when (farmer) {
            null -> {
                addFarmer()
            }
            else -> {
                editFarmer()
            }
        }


    }
    private fun addFarmer(){
        captureBirthCertificateButton.visibility = View.VISIBLE
        captureNationalIdButton.visibility = View.VISIBLE
        addNewFarmerTextView.visibility = View.VISIBLE
        //Get details of logged in regional manager
        val regionalManager = intent.getSerializableExtra("Regional_Manager") as RegionalManager?

        val regionalManagerId = regionalManager?.regional_manager_id
        Log.i("TAG", "Regional Manager Id: $regionalManagerId")

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
                    Log.i("TAG", "Id in ${newFarmer.farmer_id}")

                    farmerViewModel.insert(newFarmer)

                    val addedFarmer =
                        farmerViewModel.getFarmerByDateAndTimeCreated(newFarmer.createdAt)

                    Log.i("TAG", "Id out ${addedFarmer?.farmer_id}")

                    when(addedFarmer !=null ){
                        true -> {
                            Log.i("TAG", "Birth Certificate ${addedFarmer.birth_certificate} \n" +
                                    " National Id: ${addedFarmer.national_id}")

                            Toast.makeText(
                                this,
                                "Farmer successfully added",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        false -> {
                            Toast.makeText(
                                this,
                                "Farmer not added",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
    private fun editFarmer(){
        farmer = intent.extras?.get("farmer") as Farmer?

        editNewFarmerTextView.visibility = View.VISIBLE
        previewNationalIdTextView.visibility = View.VISIBLE
        previewBirthCertificateTextView.visibility = View.VISIBLE


        val regionalManagerId = farmer?.manager_id
        Log.i("TAG", "Regional Manager Id: $regionalManagerId")

        editTextName.setText(farmer?.name)
        phoneNumber.setText(farmer?.phone_number)
        farmer?.gender?.let { mGenderSpinner.setSelection(it) }

        Log.i("TAG", "Birth certificate: ${farmer?.birth_certificate} \n National id ${farmer?.national_id}")

        //Preview Birth certificate
        previewBirthCertificateTextView.setOnClickListener {
            val returnedBirthCertificate = farmer?.birth_certificate
            val intent = Intent(this@FarmerActivity, PreviewFarmerActivity::class.java)
            intent.putExtra(IS_BIRTH_CERT, returnedBirthCertificate )
            startActivity(intent)
        }
        //Preview National id
        previewNationalIdTextView.setOnClickListener {
            val returnedNationalId = farmer?.national_id
            val intent = Intent(this@FarmerActivity, PreviewFarmerActivity::class.java)
            intent.putExtra(IS_NATIONAL_ID, returnedNationalId )
            startActivity(intent)
        }

        //Edit farmer
        val saveFarmerButton = findViewById<Button>(R.id.addFarmerButton)
        saveFarmerButton.setOnClickListener {

            when (farmer?.farmer_id) {
                null -> {
                    return@setOnClickListener
                    Log.i("TAG", "Null farmer opened")
                }
                else -> {
                    val name = editTextName.text.toString().trim()
                    val phoneNumber = phoneNumber.text.toString().trim()

//                    val extras = intent.extras



                    farmer?.name = name
                    farmer?.gender = mGender
                    farmer?.phone_number = phoneNumber
                    farmer?.birth_certificate = savedUri
                    farmer?.national_id = savedUri
                    Log.i("TAG", "farmer opened")


                    farmerViewModel.updateFarmer(farmer)

                    val updatedFarmer = farmerViewModel.getSingleFarmer(farmer?.farmer_id)

                    when(updatedFarmer != null ){
                        true -> {
                            Toast.makeText(
                                this,
                                "Farmer successfully updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        false -> {
                            Toast.makeText(
                                this,
                                "Farmer not updated",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
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
                                GENDER_MALE
                            }
                            getString(R.string.gender_female) -> {
                                GENDER_FEMALE
                            }
                            else -> {
                                GENDER_UNKOWN
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

