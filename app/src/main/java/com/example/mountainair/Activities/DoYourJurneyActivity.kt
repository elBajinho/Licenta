package com.example.mountainair.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.mountainair.Adapters.DemoFragmentCollectionAdapter
import com.example.mountainair.R

class DoYourJurneyActivity : AppCompatActivity(){

    var viewPager : ViewPager? = null
    var adapter : DemoFragmentCollectionAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_your_journey)

        var viewPager :  ViewPager = findViewById(R.id.pager)
        adapter=DemoFragmentCollectionAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

}