package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.CooperativeManager
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.utils.GENDER_FEMALE
import com.example.santecoffeemerhants.utils.GENDER_MALE
import com.example.santecoffeemerhants.utils.GENDER_UNKOWN
import com.example.santecoffeemerhants.viewmodel.CoopManagerViewModel
import kotlinx.android.synthetic.main.activity_new_manager.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*


class ManagersActivity: AppCompatActivity() {
    private lateinit var coopManagerViewModel: CoopManagerViewModel
    private var mGender = GENDER_UNKOWN
    private var cooperativeManager: CooperativeManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_manager)

        //set toolbar
        setSupportActionBar(activity_main_toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Cooperative Managers"

        coopManagerViewModel = ViewModelProvider(this).get(CoopManagerViewModel::class.java)

        cooperativeManager = intent?.extras?.get("cooperativeManager") as CooperativeManager?
        setUpGenderSpinner()

        coopManagerNameEditText.addTextChangedListener(nameTextWatcher)
        coopManagerphoneEditText.addTextChangedListener(contactTextWatcher)
        coopManagerEmailEditText.addTextChangedListener(emailTextWatcher)

        when(cooperativeManager){
            null -> {
                addCoopManagerTextView.visibility = View.VISIBLE
                addCooperativeManager()
            }
            else -> {
                editCoopManagerTextView.visibility = View.VISIBLE
            }
        }
    }
    private fun addCooperativeManager(){
        //Get regional manager logged in
        val regionalManager =
            intent.getSerializableExtra("Regional_Manager") as RegionalManager?
        val regionalManagerId = regionalManager?.regional_manager_id

        saveCoopManagerButton.setOnClickListener{
            when (isValid() && regionalManagerId != null) {
               true -> {
                   val name = coopManagerNameEditText.text.toString().trim()
                   val contact = coopManagerphoneEditText.text.toString().trim()
                   val email = coopManagerEmailEditText.text.toString().trim()

                   val newCooperativeManager = CooperativeManager(
                       regional_manager_id = regionalManagerId,
                       name = name,
                       phone_number = contact,
                       email = email,
                       gender = mGender,
                       createdAt = Date()
                   )
                   coopManagerViewModel.insert(newCooperativeManager)
                   val createDate = newCooperativeManager.createdAt

                   val addedCoopManager =
                       coopManagerViewModel.getCooperativeManagerByCreatedAt(createDate)

                   Log.i("TAG", "CREATEDAT: ${addedCoopManager?.createdAt}")

                   when(addedCoopManager?.createdAt == createDate){
                       true -> {
                           Toast.makeText(
                               this,
                               "Cooperative manager successfully added",
                               Toast.LENGTH_SHORT
                           ).show()
                           finish()
                       }
                       false -> {
                           Toast.makeText(
                               this,
                               "Cooperative manager not added",
                               Toast.LENGTH_SHORT
                           ).show()
                       }
                   }
                }
                false -> {
                    return@setOnClickListener
                }
            }
        }
    }
    private fun isValid(): Boolean{
        when{
            mGender == GENDER_UNKOWN && coopManagerNameEditText.text.toString().trim().isEmpty() &&
                    coopManagerphoneEditText.text.toString().trim().isEmpty() &&
                    coopManagerEmailEditText.text.toString().trim().isEmpty() -> {
                coopManagerEmptyNameTextView.visibility = View.VISIBLE
                InvalidCoopManagerPhoneNumberTextView.visibility = View.VISIBLE
                coopManagerInvalidEmailTextView.visibility = View.VISIBLE
                coopManagerInvalidGenderTextView.visibility = View.VISIBLE

                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(coopManagerEmailEditText.text.toString()).matches() -> {
                coopManagerInvalidEmailTextView.visibility = View.VISIBLE
                return false
            }
            else -> {
                return true
            }
        }
    }
    private fun setUpGenderSpinner() {
        when {
            coopManagerGenderSpinner != null -> {
                //Create adapter for spinner
                val adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.array_gender_options,
                    android.R.layout.simple_spinner_item
                )
                // Specify dropdown layout style - simple list view with 1 item per line
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                // Apply the adapter to the spinner
                coopManagerGenderSpinner.adapter = adapter

                coopManagerGenderSpinner.onItemSelectedListener = object :
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
    private val nameTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            when {
                coopManagerNameEditText.text.toString().trim().isEmpty() -> {
                    coopManagerEmptyNameTextView.visibility = View.VISIBLE
                }
                else -> {
                    coopManagerEmptyNameTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }
    private val contactTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            when {
                coopManagerphoneEditText.text.toString().trim().isEmpty() -> {
                    InvalidCoopManagerPhoneNumberTextView.visibility = View.VISIBLE
                }
                else -> {
                    InvalidCoopManagerPhoneNumberTextView.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
    private val emailTextWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            when {
                coopManagerEmailEditText.text.toString().trim().isEmpty() -> {
                    coopManagerInvalidEmailTextView.visibility = View.VISIBLE
                }
                else -> {
                    coopManagerInvalidEmailTextView.visibility = View.GONE
                }
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
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
