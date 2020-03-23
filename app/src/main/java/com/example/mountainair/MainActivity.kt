package com.example.mountainair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mountainair.Activities.FeddActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
            val intent= Intent(this,FeddActivity::class.java)
            startActivity(intent)
        }

}

