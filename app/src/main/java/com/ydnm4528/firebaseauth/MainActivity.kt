package com.ydnm4528.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
    }

    private fun initFirebase() {

        auth = Firebase.auth

        btnSignup.setOnClickListener {

            var intent = Intent(this, SignupActivity::class.java )
            startActivity(intent)


        }

        btnSignin.setOnClickListener {

            var email: String = edtEmail.text.toString()
            var pasword: String = edtPassword.text.toString()

            SignIn(email, pasword)
        }

    }



    private fun SignIn (email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success SignIn"+ auth.currentUser?.uid , Toast.LENGTH_LONG).show()

                    //val userId = auth.currentUser?.uid

                    var intent = Intent(this, UserActivity::class.java )
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Fail SignIn", Toast.LENGTH_LONG).show()
                }
            }
    }
}