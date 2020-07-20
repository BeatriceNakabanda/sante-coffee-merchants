package com.example.santecoffeemerhants

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
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


    private val genderUnknown = 0
    private val genderMale = 1
    private val genderFemale = 2
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

        editTextName.addTextChangedListener(nameTextWatcher)
        editTextEmail.addTextChangedListener(emailTextWatcher)
        editTextRegion.addTextChangedListener(regionTextWatcher)
        editTextPassword.addTextChangedListener(passwordTextWatcher)
        editTextConfirmPassword.addTextChangedListener(confirmPasswordTextWatcher)


        setUpGenderSpinner()

        regionalManagerViewModel = ViewModelProvider(this).get(RegionalManagerViewModel::class.java)



        val button = findViewById<Button>(R.id.signUpButton)
        button.setOnClickListener {
            val name = editTextName.getText().toString().trim()
            val email = editTextEmail.getText().toString().trim()
            val region = editTextRegion.getText().toString().trim()
            val password = editTextPassword.getText().toString().trim()
            val confirmPassword = editTextConfirmPassword.getText().toString().trim()

            val invalidNameText = findViewById<TextView>(R.id.emptyNameTextView)
            val invalidEmailText = findViewById<TextView>(R.id.invalidEmailTextView)
            val invalidRegionText = findViewById<TextView>(R.id.emptyRegionTextView)
            val emptyPassword = findViewById<TextView>(R.id.emptyPasswordTextView)
            val emptyConfirmPassword =
                findViewById<TextView>(R.id.emptyConfirmPasswordTextView)
            val invalidGenderText =
                findViewById<View>(R.id.invalidGenderTextView) as TextView


            when {
                mGender == genderUnknown || name.isEmpty() || email.isEmpty() || region.isEmpty()
                        || password.isEmpty() || confirmPassword.isEmpty() -> {
                    invalidGenderText.visibility = View.VISIBLE
                    invalidNameText.visibility = View.VISIBLE
                    invalidEmailText.visibility = View.VISIBLE
                    invalidRegionText.visibility = View.VISIBLE
                    emptyPassword.visibility = View.VISIBLE
                    emptyConfirmPassword.visibility = View.VISIBLE
                }
                password != confirmPassword -> {
                    val notMatchingPassword = findViewById<TextView>(R.id.noMatchPasswordTextView)
                    notMatchingPassword.visibility = View.VISIBLE
                }
                else -> {
                    val regionalManager = RegionalManager(
                        name = name,
                        gender = mGender,
                        email = email,
                        region = region,
                        password = password,
                        createdAt = Date()
                    )

                    val regionalManagerEmail = regionalManager.email

                    regionalManagerViewModel.insert(regionalManager)
                    val returnedRegionalManager = regionalManagerViewModel.getRegionalMangerByEmail(regionalManagerEmail)

                    if (returnedRegionalManager != null ){
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

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    mGender
                }
            }
        }
    }

    private val nameTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val invalidNameText = findViewById<View>(R.id.emptyNameTextView) as TextView
            if (editTextName.getText().toString().trim().isEmpty()){
                invalidNameText.visibility = View.VISIBLE
            }else{
                invalidNameText.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
    private val emailTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val invalidEmailText =
                findViewById<TextView>(R.id.invalidEmailTextView)
            val email = editTextEmail.getText().toString().trim()
            if ( email.isEmpty()) {
                invalidEmailText.visibility = View.VISIBLE
            }else{
                invalidEmailText.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
    private val regionTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val invalidRegionText = findViewById<TextView>(R.id.emptyRegionTextView)
            if (editTextRegion.getText().toString().trim().isEmpty()){
                invalidRegionText.visibility = View.VISIBLE
            }else{
                invalidRegionText.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
    private val passwordTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val emptyPassword = findViewById<TextView>(R.id.emptyPasswordTextView)
            if (editTextPassword.getText().toString().trim().isEmpty()){
                emptyPassword.visibility = View.VISIBLE
            }
            else{
                emptyPassword.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
    private val confirmPasswordTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val emptyConfirmPassword = findViewById<TextView>(R.id.emptyConfirmPasswordTextView)
            if (editTextConfirmPassword.getText().toString().trim().isEmpty()){
                emptyConfirmPassword.visibility = View.VISIBLE
            }else{
                emptyConfirmPassword.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }


    }
}