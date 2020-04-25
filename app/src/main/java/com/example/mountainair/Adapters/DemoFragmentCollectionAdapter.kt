package com.example.mountainair.Adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mountainair.Fragments.*

class DemoFragmentCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 6
    }

    override fun getItem(position: Int): Fragment {
        var demo1 : ActivitiesFragment = ActivitiesFragment()
        var demo2 : DateFragment = DateFragment()
        var demo3 : GeographicFragment = GeographicFragment()
        var demo4 : RouteFragment = RouteFragment()
        var demo5 : WeatherFragment = WeatherFragment()

        if(position==0)
            return demo1
        else
            if(position==1)
                return demo2
            else
                if(position==2)
                return demo3
                else
                    if(position==3)
                    return demo4
                    else
                        return demo5
    }
}