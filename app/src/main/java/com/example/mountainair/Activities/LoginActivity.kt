package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mountainair.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity :: class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            auth.signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, FeddActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(baseContext, "something went wrong",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser : FirebaseUser?){

    }

}