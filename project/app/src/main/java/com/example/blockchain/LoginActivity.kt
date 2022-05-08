package com.example.blockchain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"
        auth= FirebaseAuth.getInstance()
    }

    fun login(view: View){
        val email=editTextEmailAddress.text.toString()
        val password=editTextPassword.text.toString()
        if(!(email.isEmpty() || email == "" || password.isEmpty() || password == "")) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        }else{
            Toast.makeText(applicationContext, "Please fill out all fields ", Toast.LENGTH_LONG)
                .show()
        }
    }
    fun goToRegister(view: View){
        val intent= Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

}