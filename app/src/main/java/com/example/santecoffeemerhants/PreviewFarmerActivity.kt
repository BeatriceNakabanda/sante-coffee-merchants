package com.example.santecoffeemerhants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.santecoffeemerhants.utils.BIRTH_CERT_URI
import com.example.santecoffeemerhants.utils.IS_BIRTH_CERT
import com.example.santecoffeemerhants.utils.IS_NATIONAL_ID
import com.example.santecoffeemerhants.utils.NATIONAL_ID_URI
import kotlinx.android.synthetic.main.activity_preview_image.*

class PreviewFarmerActivity: AppCompatActivity() {

    private  var savedUri: String = null.toString()

//    val startForResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            when (result.resultCode) {
//                Activity.RESULT_OK -> {
//                    val intent = result.data
//                    // Handle the Intent
//                    val extras = intent?.extras
//
//                    when(extras != null){
//                        true -> {
//                            when {
//                                extras.containsKey(BIRTH_CERT_URI) -> {
//                                    savedUri = intent.getStringExtra(BIRTH_CERT_URI) as String
//
//                                    Log.i("TAG", "Recaptured Birth Cert: $savedUri")
//
//                                    imagePreview.setImageURI(Uri.parse(savedUri))
//
//                                }
//                                extras.containsKey(NATIONAL_ID_URI) -> {
//                                    savedUri = intent.getStringExtra(NATIONAL_ID_URI) as String
//
//                                    Log.i("TAG", "Recaptured National Id: $savedUri")
//
//                                }
//                            }
//                        }
//                        false -> {
//                            Toast.makeText(
//                                this,
//                                "saved uri null",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_image)

        val extras = intent.extras
        when(extras != null) {
            true -> {
                when{
                    extras.containsKey(IS_BIRTH_CERT) -> {
                        val birthCertificate = intent.getStringExtra(IS_BIRTH_CERT)
                        imagePreview.setImageURI(Uri.parse(birthCertificate))

                        preview_retake_button.setOnClickListener {
//                            val intent = Intent(this, CaptureDocumentActivity::class.java)
//                            intent.putExtra(IS_BIRTH_CERT, true)
//                            startForResult.launch(intent)
                        }
//                        when(savedUri != null){
//                            true -> {
//                                preview_save_button.visibility = View.VISIBLE
//                                preview_save_button.setOnClickListener {
//                                    val intent = Intent(this, FarmerActivity::class.java)
//                                    intent.putExtra(IS_BIRTH_CERT, savedUri)
//                                    startActivity(intent)
//
//                                    Toast.makeText(
//                                        this,
//                                        "Sent uri $savedUri",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//
//                                    finish()
//                                }
//                            }
//                            false -> {
//                                preview_back_button.setOnClickListener {
//                                    finish()
//                                }
//                            }
//                        }
                }
                    extras.containsKey(IS_NATIONAL_ID) -> {
                        val nationalId =  intent.getStringExtra(IS_NATIONAL_ID)
                        imagePreview.setImageURI(Uri.parse(nationalId))
                    }
                }
            }
            false -> {
                Toast.makeText(
                    this,
                    "No Documents",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}