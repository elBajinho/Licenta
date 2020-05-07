package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.mountainair.Adapters.DemoFragmentCollectionAdapter
import com.example.mountainair.Model.Filters
import com.example.mountainair.Model.GeographicSelection
import com.example.mountainair.Model.RouteSelection
import com.example.mountainair.Model.WheatherSelection
import com.example.mountainair.R
import java.util.Date


class DoYourJurneyActivity : AppCompatActivity(){

    var viewPager : ViewPager? = null
    var adapter : DemoFragmentCollectionAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_your_journey)

        var viewPager :  ViewPager = findViewById(R.id.pager)
        viewPager.offscreenPageLimit=6
        adapter=DemoFragmentCollectionAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    fun goTo(){
        var activities : ArrayList<String> = adapter!!.getDemo1().getListOfActivities()
        var date : Date = adapter!!.getDemo2().getDate()
        var gs : GeographicSelection = adapter!!.getDemo3().getGeographicSelection()
        var rs : RouteSelection = adapter!!.getDemo4().geRouteSelection()
        var ws : WheatherSelection = adapter!!.getDemo5().getWheatherSelection()
        //Toast.makeText(this,ws.maxT.toString()+ " "+ ws.rain,Toast.LENGTH_SHORT).show()
        Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ResultedRouteActivity :: class.java)
        intent.putExtra("filters", Filters(activities,date,gs,rs,ws))
        startActivity(intent)
    }
}