package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_farmer.*

class NewFarmerActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var phoneNumber: EditText

    private lateinit var mGenderSpinner: Spinner
    private val genderUnknown = "Unknown"
    private val genderMale = "Female"
    private val genderFemale = "Male"
    private var mGender = genderUnknown
    private var birthCertificate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_farmer)

        mGenderSpinner = findViewById(R.id.farmerGenderSpinner)

        setUpGenderSpinner()
//        birthCertificate = intent.getStringExtra("Directory")
//        val filePath = intent.getStringExtra("Directory")
        //set on click listeners for attach documents buttons
        captureNationalIdButton.setOnClickListener{
            val intent = Intent(this, CaptureDocumentActivity::class.java)
            startActivity(intent)

        }
        captureBirthCertificateButton.setOnClickListener {
            val intent = Intent(this, CaptureDocumentActivity::class.java)
            startActivity(intent)
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
}

