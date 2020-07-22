package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.viewmodel.RegionalManagerViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
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

        loginEmailAddressEditText.addTextChangedListener(emailTextWatcher)
        loginPasswordEditText.addTextChangedListener(passwordTextWatcher)

        signUpTextView.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        regionalManagerViewModel = ViewModelProvider(this).get(RegionalManagerViewModel::class.java)

        /*
         Set on click listener for sign in button
         */
        signInButton.setOnClickListener{
            val email = loginEmailAddressEditText.text.toString().trim()
            val password = loginPasswordEditText.text.toString().trim()

            regionalManager = regionalManagerViewModel.getRegionalManager(email, password)

            when {
                email.isEmpty() && password.isEmpty() -> {
                    invalidEmailLoginTextView.visibility = View.VISIBLE
                    invalidPasswordLoginTextView.visibility = View.VISIBLE
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches()-> {
                    invalidEmailLoginTextView.visibility = View.VISIBLE
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
            when {
                loginEmailAddressEditText.text.toString().trim().isEmpty() -> {
                    invalidEmailLoginTextView.visibility = View.VISIBLE
                }
                else -> {
                    invalidEmailLoginTextView.visibility = View.GONE
                }
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    private val passwordTextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            when {
                loginPasswordEditText.text.toString().trim().isEmpty() ||
                        loginPasswordEditText.text.toString().trim().length <= 3 -> {
                    invalidPasswordLoginTextView.visibility = View.VISIBLE
                }
                else -> {
                    invalidPasswordLoginTextView.visibility = View.GONE
                }
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }
}