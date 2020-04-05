package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mountainair.R
import kotlinx.android.synthetic.main.activity_update_profile.*

class UpdateProfileActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        update_profile_button.setOnClickListener {
            var intent = Intent(this, FeddActivity::class.java)
            startActivity(intent)
        }
    }
}