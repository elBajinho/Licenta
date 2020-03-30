package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mountainair.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity :: class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            val intent = Intent(this, FeddActivity::class.java)
            startActivity(intent)
        }
    }
}