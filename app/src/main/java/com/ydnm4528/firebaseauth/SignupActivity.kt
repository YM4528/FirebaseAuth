package com.ydnm4528.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.edtEmail
import kotlinx.android.synthetic.main.activity_main.edtPassword
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initFirebase()
    }


    private fun initFirebase() {

        auth = Firebase.auth


        btnSignupActivity.setOnClickListener {

            var email: String = edtEmail.text.toString()
            var pasword: String = edtPassword.text.toString()

            val firstname: String = edtFirstname.text.toString()
            val lastname: String = edtLastname.text.toString()
            val phoneNumber: String = edtPhoneno.text.toString()

            createAccount(email, pasword, firstname, lastname, phoneNumber)
        }

    }

    private fun createAccount(email: String, password: String, firstname:String, lastname: String, phoneNumber: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener (this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success signup", Toast.LENGTH_LONG).show()

                    val userId = auth.currentUser?.uid

                    val database = Firebase.database

                    //val ref = database.reference.child("Users")
                    val ref = database.getReference("Users")
                   // ref.setValue(userId)

                    val userDB = ref.child(userId!!)

                    userDB.child("firstName").setValue(firstname)
                    userDB.child("lastName").setValue(lastname)
                    userDB.child("phoneno").setValue(phoneNumber)
                    userDB.child("userId").setValue(userId)

                    finish()

                } else {
                    Toast.makeText(this, "Fail SignUp" + task.exception , Toast.LENGTH_LONG).show()
                    Log.d("Error>>>>", task.exception.toString())
                }
            }

    }

}