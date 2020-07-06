package com.example.santecoffeemerhants

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.santecoffeemerhants.data.Dao.RegionalManagerDao
import com.example.santecoffeemerhants.data.Entity.RegionalManager
import com.example.santecoffeemerhants.data.SanteRoomDatabase
import com.example.santecoffeemerhants.viewmodel.RegionalManagerViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var regionalManagerViewModel: RegionalManagerViewModel
    private  var regionalManagerDao: RegionalManagerDao? = null
    private lateinit var textViewRegister: TextView
    private var dataBase: SanteRoomDatabase? = null

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

        dataBase =
            Room.databaseBuilder<SanteRoomDatabase>(this, SanteRoomDatabase::class.java, "sante_database.db")
                .allowMainThreadQueries()
                .build()
        val db =dataBase?.regionalManagerDao()

        editTextEmail = findViewById(R.id.loginEmailAddressEditText)
        editTextPassword = findViewById(R.id.loginPasswordEditText)
        textViewRegister = findViewById(R.id.signUpTextView)

        textViewRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

//        editTextEmail.addTextChangedListener(emailTextWatcher)
//        editTextPassword.addTextChangedListener(passwordTextWatcher)

        val button = findViewById<Button>(R.id.signInButton)
        button.setOnClickListener{
            val email = editTextEmail.getText().toString().trim()
            val password = editTextPassword.getText().toString().trim()

            val regionalManager: RegionalManager? =
                db?.getRegionalManagerByEmail(email)
            val returnedRegionalManagerEmail = regionalManager?.email

            if (email == returnedRegionalManagerEmail){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Regional_Manager", regionalManager)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(
                    this@LoginActivity,
                    "Unregistered user, or incorrect",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


//        login()
    }
    private val emailTextWatcher = object: TextWatcher{
        override fun afterTextChanged(editable: Editable?) {
            val invalidEmailText =findViewById<View>(R.id.invalidEmailTextView) as TextView
            if(editable != null && !editTextEmail.equals("") ){
                invalidEmailText.visibility = View.GONE
            }
        }


        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    private fun login(){

    }

}