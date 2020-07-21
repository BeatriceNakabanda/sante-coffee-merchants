package com.example.santecoffeemerhants

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.RegionalManagerViewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var textViewRegister: TextView
    private lateinit var regionalManagerViewModel: RegionalManagerViewModel

    private var regionalManager: RegionalManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the title bar
        getSupportActionBar()?.hide();
        //enable full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //inflate layout
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.loginEmailAddressEditText)
        editTextPassword = findViewById(R.id.loginPasswordEditText)

        editTextEmail.addTextChangedListener(emailTextWatcher)
        editTextPassword.addTextChangedListener(passwordTextWatcher)
        textViewRegister = findViewById(R.id.signUpTextView)

        textViewRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        regionalManagerViewModel = ViewModelProvider(this).get(RegionalManagerViewModel::class.java)

        val button = findViewById<Button>(R.id.signInButton)
        button.setOnClickListener{
            val email = editTextEmail.getText().toString().trim()
            val password = editTextPassword.getText().toString().trim()
            val invalidEmailText =
                findViewById<View>(R.id.invalidEmailLoginTextView) as TextView
            val invalidPasswordText =
                findViewById<View>(R.id.invalidPasswordLoginTextView) as TextView

            regionalManager = regionalManagerViewModel.getRegionalManager(email, password)

            when {
                email.isEmpty() || password.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    invalidEmailText.visibility = View.VISIBLE
                    invalidPasswordText.visibility = View.VISIBLE

                }
                regionalManager == null -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Unregistered user or wrong credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                regionalManager != null -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Regional_Manager", regionalManager)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid user login",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            }

        }
    private val emailTextWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val invalidEmailText =
                findViewById<View>(R.id.invalidEmailLoginTextView) as TextView
            val email = editTextEmail.getText().toString().trim()
            val isValid =
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
    private val passwordTextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val invalidPasswordText =
                findViewById<View>(R.id.invalidPasswordLoginTextView) as TextView
            if (editTextPassword.getText().toString().isEmpty() || editTextPassword.getText().length <= 3) {
                invalidPasswordText.visibility = View.VISIBLE
            }
            else {
                invalidPasswordText.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    }


