package com.example.santecoffeemerhants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.Farmer
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.utils.*
import com.example.santecoffeemerhants.viewmodel.FarmerViewModel
import kotlinx.android.synthetic.main.activity_new_farmer.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*


class FarmerActivity : AppCompatActivity() {
    private lateinit var farmerViewModel: FarmerViewModel

    private lateinit var birthCertificate: String
    private lateinit var nationalId: String

    private var mGender = GENDER_UNKOWN
    private var farmer: Farmer? = null

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val intent = result.data
                    // Handle the Intent
                    val extras = intent?.extras

                    when (extras != null) {
                        true -> {
                            when {
                                extras.containsKey(BIRTH_CERT_URI) -> {
                                    birthCertificate =
                                        intent.getStringExtra(BIRTH_CERT_URI) as String
                                    Log.i("TAG", "saved Birth Cert: $birthCertificate")
                                    imagePreview.setImageURI(Uri.parse(birthCertificate))
                                    Toast.makeText(
                                        this,
                                        "Saved Birth certificate",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    captureBirthCertificateButton.visibility = View.GONE
                                    birthCertificateNameTextView.visibility = View.VISIBLE
                                    recaptureBirthCertificateButton.visibility = View.VISIBLE
                                }
                                extras.containsKey(NATIONAL_ID_URI) -> {
                                    nationalId = intent.getStringExtra(NATIONAL_ID_URI) as String
                                    Log.i("TAG", "National Id: $nationalId")
                                    imagePreview.setImageURI(Uri.parse(nationalId))
                                    Toast.makeText(
                                        this,
                                        "Saved National Id",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    captureNationalIdButton.visibility = View.GONE
                                    nationalIdNameTextView.visibility = View.VISIBLE
                                    recaptureNationalIdButton.visibility = View.VISIBLE
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

        //set toolbar
        setSupportActionBar(activity_main_toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Farmers"

        farmerViewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)

        farmerNameEditText.addTextChangedListener(nameTextWatcher)
        phoneEditText.addTextChangedListener(phoneNumberTextWatcher)

        setUpGenderSpinner()

        farmer = intent?.extras?.get("farmer") as Farmer?

        when(farmer) {
            null -> {
                addFarmer()
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
                recaptureBirthCertificateButton.setOnClickListener {
                    val intent = Intent(this, CaptureDocumentActivity::class.java)
                    intent.putExtra(IS_NATIONAL_ID, true)
                    startForResult.launch(intent)
                }
                recaptureNationalIdButton.setOnClickListener {
                    val intent = Intent(this, CaptureDocumentActivity::class.java)
                    intent.putExtra(IS_NATIONAL_ID, true)
                    startForResult.launch(intent)
                }
                birthCertificateNameTextView.setOnClickListener {
                    imagePreview.visibility = View.VISIBLE
                    imagePreview.setImageURI(Uri.parse(birthCertificate))
                    saveFarmerButton.visibility = View.GONE
                    recaptureBirthCertificateButton.visibility = View.GONE
                    captureBirthCertificateButton.visibility = View.GONE
                    captureNationalIdButton.visibility = View.GONE
                    preview_back_button.visibility = View.VISIBLE
                    preview_back_button.setOnClickListener {
                        imagePreview.visibility = View.GONE
                        saveFarmerButton.visibility = View.VISIBLE
                        recaptureBirthCertificateButton.visibility = View.VISIBLE
                        captureNationalIdButton.visibility = View.VISIBLE
                    }
                }
                nationalIdNameTextView.setOnClickListener {
                    imagePreview.visibility = View.VISIBLE
                    imagePreview.setImageURI(Uri.parse(nationalId))
                    saveFarmerButton.visibility = View.GONE
                    captureBirthCertificateButton.visibility = View.GONE
                    recaptureNationalIdButton.visibility = View.GONE
                    recaptureBirthCertificateButton.visibility = View.GONE
                    preview_back_button.visibility = View.VISIBLE
                    preview_back_button.setOnClickListener {
                        imagePreview.visibility = View.GONE
                        saveFarmerButton.visibility = View.VISIBLE
                        recaptureBirthCertificateButton.visibility = View.VISIBLE
                        recaptureNationalIdButton.visibility = View.VISIBLE
                    }
                }
            }
            else -> {
                editFarmer()
            }
        }
    }
    private fun previewDocuments() {
        imagePreview.visibility = View.VISIBLE
        previewBirthCertificateButton.visibility = View.GONE
        previewNationalIdButton.visibility = View.GONE
        saveFarmerButton.visibility = View.GONE
        preview_retake_button.visibility = View.VISIBLE
        preview_back_button.visibility = View.VISIBLE

        preview_back_button.setOnClickListener {
            imagePreview.visibility = View.GONE
            editNewFarmerTextView.visibility = View.VISIBLE
            previewNationalIdButton.visibility = View.VISIBLE
            previewBirthCertificateButton.visibility = View.VISIBLE
            preview_retake_button.visibility = View.GONE
            preview_back_button.visibility = View.GONE
            saveFarmerButton.visibility = View.VISIBLE
        }
    }

    private fun addFarmer() {
        captureBirthCertificateButton.visibility = View.VISIBLE
        captureNationalIdButton.visibility = View.VISIBLE
        addNewFarmerTextView.visibility = View.VISIBLE
        //Get details of logged in regional manager
        val regionalManager =
            intent.getSerializableExtra("Regional_Manager") as RegionalManager?

        val regionalManagerId = regionalManager?.regional_manager_id
        Log.i("TAG", "Regional Manager Id: $regionalManagerId")

        //Create new farmer
        saveFarmerButton.setOnClickListener {
            when (isValid() && regionalManagerId != null) {
                true -> {
                    val name = farmerNameEditText.text.toString().trim()
                    val phoneNumber = phoneEditText.text.toString().trim()
                    val newFarmer = Farmer(
                        manager_id = regionalManagerId,
                        name = name,
                        phone_number = phoneNumber,
                        gender = mGender,
                        birth_certificate = birthCertificate,
                        national_id = nationalId,
                        createdAt = Date()
                    )
                    Log.i("TAG", "Regional manager for farmer ${newFarmer.manager_id}")
                    Log.i("TAG", "Id in ${newFarmer.farmer_id}")

                    farmerViewModel.insert(newFarmer)

                    val addedFarmer =
                        farmerViewModel.getFarmerByDateAndTimeCreated(newFarmer.createdAt)

                    Log.i("TAG", "Id out ${addedFarmer?.farmer_id}")

                    when (addedFarmer != null) {
                        true -> {
                            Log.i("TAG",
                                "Birth Certificate ${addedFarmer.birth_certificate} \n" +
                                        " National Id: ${addedFarmer.national_id}")
                            Toast.makeText(
                                this,
                                "Farmer successfully added",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
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
                false -> {
                    Toast.makeText(
                        this,
                        "Please enter all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }
        }
    }

    private fun editFarmer() {
        editNewFarmerTextView.visibility = View.VISIBLE
        previewNationalIdButton.visibility = View.VISIBLE
        previewBirthCertificateButton.visibility = View.VISIBLE

        //Preview Birth certificate
        previewBirthCertificateButton.setOnClickListener {
            previewDocuments()
            imagePreview.setImageURI(Uri.parse(farmer?.birth_certificate))

            preview_retake_button.setOnClickListener {
                val intent = Intent(this, CaptureDocumentActivity::class.java)
                intent.putExtra(IS_BIRTH_CERT, true)
                startForResult.launch(intent)
            }
        }
        //Preview National id
        previewNationalIdButton.setOnClickListener {
            previewDocuments()
            imagePreview.setImageURI(Uri.parse(farmer?.national_id))

            preview_retake_button.setOnClickListener {
                val intent = Intent(this, CaptureDocumentActivity::class.java)
                intent.putExtra(IS_NATIONAL_ID, true)
                startForResult.launch(intent)
            }
        }

        val regionalManagerId = farmer?.manager_id
        Log.i("TAG", "Regional Manager Id: $regionalManagerId")

        farmerNameEditText.setText(farmer?.name)
        phoneEditText.setText(farmer?.phone_number)
        farmer?.gender?.let { farmerGenderSpinner.setSelection(it) }

        Log.i("TAG", "Birth certificate: ${farmer?.birth_certificate} " +
                "\n National id ${farmer?.national_id}")

        //Edit farmer
        saveFarmerButton.setOnClickListener {
            when (farmer?.farmer_id != null) {
                true -> {
                    val name = farmerNameEditText.text.toString().trim()
                    val phoneNumber = phoneEditText.text.toString().trim()

                    farmer?.name = name
                    farmer?.gender = mGender
                    farmer?.phone_number = phoneNumber
                    farmer?.birth_certificate = birthCertificate
                    farmer?.national_id = nationalId

                    farmerViewModel.updateFarmer(farmer)

                    val updatedFarmer = farmerViewModel.getSingleFarmer(farmer?.farmer_id)

                    Log.i("TAG", "updated birth certificate ${updatedFarmer.birth_certificate} " +
                                "\n Updated national id ${updatedFarmer.national_id}")

                    when (updatedFarmer != null) {
                        true -> {
                            Toast.makeText(
                                this,
                                "Updated Farmer",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
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
                false -> {
                    Toast.makeText(
                        this,
                        "Please enter valid fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val nameTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            when {
                farmerNameEditText.text.toString().trim().isEmpty() -> {
                    farmerEmptyNameTextView.visibility = View.VISIBLE
                }
                else -> {
                    farmerEmptyNameTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }
    private val phoneNumberTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            when {
                phoneEditText.text.toString().trim().isEmpty() -> {
                    InvalidFarmerPhoneNumberTextView.visibility = View.VISIBLE
                }
                else -> {
                    InvalidFarmerPhoneNumberTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    private fun isValid(): Boolean {
        when {
            mGender == GENDER_UNKOWN && farmerNameEditText.text.toString().trim().isEmpty() &&
                    phoneEditText.text.toString().trim().isEmpty() -> {
                farmerEmptyNameTextView.visibility = View.VISIBLE
                InvalidFarmerPhoneNumberTextView.visibility = View.VISIBLE
                farmerInvalidGenderTextView.visibility = View.VISIBLE
//                emptyBirthCertificateTextView.visibility = View.VISIBLE
//                emptyNationalIdTextView.visibility = View.VISIBLE

                return false
            }
            farmerNameEditText.text.toString().trim().isEmpty() -> {
                farmerEmptyNameTextView.visibility = View.VISIBLE
                return false
            }
            phoneEditText.text.toString().trim().isEmpty() -> {
                InvalidFarmerPhoneNumberTextView.visibility = View.VISIBLE
                return false
            }
            mGender == GENDER_UNKOWN -> {
                farmerInvalidGenderTextView.visibility = View.VISIBLE
                return false
            }

            else -> {
                return true
            }
        }
    }

    private fun setUpGenderSpinner() {
        when {
            farmerGenderSpinner != null -> {
                //Create adapter for spinner
                val adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.array_gender_options,
                    android.R.layout.simple_spinner_item
                )
                // Specify dropdown layout style - simple list view with 1 item per line
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                // Apply the adapter to the spinner
                farmerGenderSpinner.adapter = adapter

                farmerGenderSpinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        val selection =
                            parent.getItemAtPosition(position) as String
                        when {
                            !TextUtils.isEmpty(selection) -> {
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
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                        mGender
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}