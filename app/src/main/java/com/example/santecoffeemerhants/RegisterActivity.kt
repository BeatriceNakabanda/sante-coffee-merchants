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
import com.example.santecoffeemerhants.utils.GENDER_FEMALE
import com.example.santecoffeemerhants.utils.GENDER_MALE
import com.example.santecoffeemerhants.utils.GENDER_UNKOWN
import com.example.santecoffeemerhants.viewmodel.RegionalManagerViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class RegisterActivity : AppCompatActivity(){
    private lateinit  var regionalManagerViewModel: RegionalManagerViewModel
    private var mGender = GENDER_UNKOWN

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

        nameEditText.addTextChangedListener(nameTextWatcher)
        registerEmailAddressEditText.addTextChangedListener(emailTextWatcher)
        regionEditText.addTextChangedListener(regionTextWatcher)
        registerPasswordEditText.addTextChangedListener(passwordTextWatcher)

        setUpGenderSpinner()

        regionalManagerViewModel = ViewModelProvider(this).get(RegionalManagerViewModel::class.java)

        /*
        Set up sign up button
        */
        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = registerEmailAddressEditText.text.toString().trim()
            val region = regionEditText.text.toString().trim()
            val password = registerPasswordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            val regionalManager = RegionalManager(
                name = name,
                gender = mGender,
                email = email,
                region = region,
                password = password,
                createdAt = Date()
            )
            val regionalManager1Email = regionalManager.email


            when(isValid()){
                true -> {
                    regionalManagerViewModel.insert(regionalManager)
                    val returnedRegionalManager = regionalManagerViewModel.getRegionalMangerByEmail(regionalManager1Email)
                    when {
                        returnedRegionalManager != null -> {
                            Toast.makeText(
                                this,
                                "Successfully added regional manager",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this,
                                "Regional manager not added",
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

    private fun isValid(): Boolean{
        when {
            mGender == GENDER_UNKOWN || nameEditText.text.toString().trim().isEmpty() ||
                    registerEmailAddressEditText.text.toString().trim().isEmpty() ||
                    regionEditText.text.toString().trim().isEmpty() ||
                    registerPasswordEditText.text.toString().trim().isEmpty() -> {

                invalidGenderTextView.visibility = View.VISIBLE
                emptyNameTextView.visibility = View.VISIBLE
                invalidEmailTextView.visibility = View.VISIBLE
                emptyRegionTextView.visibility = View.VISIBLE
                emptyPasswordTextView.visibility = View.VISIBLE

                return false

            }
            registerPasswordEditText.text.toString() != confirmPasswordEditText.text.toString() -> {
                noMatchPasswordTextView.visibility = View.VISIBLE

                return false

            }
            !Patterns.EMAIL_ADDRESS.matcher(registerEmailAddressEditText.text.toString()).matches() -> {
                invalidEmailTextView.visibility = View.VISIBLE
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun setUpGenderSpinner(){
        when {
            genderSpinner != null -> {
                //Create adapter for spinner
                val adapter = ArrayAdapter.createFromResource(this,
                    R.array.array_gender_options,
                    android.R.layout.simple_spinner_item
                )
                // Specify dropdown layout style - simple list view with 1 item per line
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                // Apply the adapter to the spinner
                genderSpinner.adapter = adapter

                genderSpinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>,
                                                view: View, position: Int, id: Long) {
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
            else -> {
                Toast.makeText(this, "Gender is null", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    private val nameTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            when {
                nameEditText.text.toString().trim().isEmpty() -> {
                    emptyNameTextView.visibility = View.VISIBLE
                }
                else -> {
                    emptyNameTextView.visibility = View.GONE
                }
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    private val emailTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            when {
                registerEmailAddressEditText.text.toString().trim().isEmpty() -> {
                    invalidEmailTextView.visibility = View.VISIBLE
                }
                else -> {
                    invalidEmailTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    private val regionTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            when {
                regionEditText.text.toString().trim().isEmpty() -> {
                    emptyRegionTextView.visibility = View.VISIBLE
                }
                else -> {
                    emptyRegionTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    private val passwordTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            when {
                registerPasswordEditText.text.toString().trim().isEmpty() ||
                        registerPasswordEditText.text.toString().trim().length <= 3
                -> {
                    emptyPasswordTextView.visibility = View.VISIBLE
                }
                else -> {
                    emptyPasswordTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

}