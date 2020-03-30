package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mountainair.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            val intent = Intent(this, FeddActivity::class.java)
            startActivity(intent)
        }

    }
}