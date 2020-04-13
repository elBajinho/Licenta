package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mountainair.Model.User
import com.example.mountainair.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        Register_button.setOnClickListener {
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

        if(Register_password.text.toString().length<9){
            Register_password.error="your password shall have at least 9 characters"
            Register_password.requestFocus()
            return
        }

        if(!Register_password.text.toString().equals(Register_repeat_password.text.toString())){
            Register_repeat_password.error="Entered password are not equal"
            Register_repeat_password.requestFocus()
            return
        }



        auth.createUserWithEmailAndPassword(Register_email.text.toString(), Register_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = User(Register_username.text.toString(), Register_email.text.toString())
                    val userId : String = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()

                    Toast.makeText(baseContext, userId,Toast.LENGTH_SHORT).show()

                    database.child("users").child(userId).child("username").setValue(user.username).addOnFailureListener{
                            Toast.makeText(baseContext, "nu o fost introdus", Toast.LENGTH_SHORT)
                                .show()
                            Log.i("plm",it.toString())

                    }

                    //userRef.child(userId).child("email").setValue(user.email)
                    database.child("users").child(userId).setValue(user)
                    val intent = Intent(this, FeddActivity :: class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed. Fuck you and go to hell",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}