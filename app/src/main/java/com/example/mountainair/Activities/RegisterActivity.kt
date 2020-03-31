package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mountainair.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        Register_button.setOnClickListener {
//            val intent = Intent(this, FeddActivity::class.java)
//            startActivity(intent)
              signUpUser()
        }
    }

    private fun signUpUser(){
        if(Register_email.text.toString().isEmpty()){
            Register_email.error="please add your Email"
            Register_email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Register_email.text.toString()).matches()){
            Register_email.error="please add a valid  Email"
            Register_email.requestFocus()
            return
        }

        if(Register_password.text.toString().isEmpty()){
            Register_password.error="please enter your password"
            Register_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(Register_email.text.toString(), Register_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, FeddActivity :: class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Authentication failed. Fuck you and go to hell",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}