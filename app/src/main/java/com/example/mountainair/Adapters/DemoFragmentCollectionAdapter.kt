package com.example.mountainair.Adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mountainair.Fragments.*

class DemoFragmentCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var demo1 : ActivitiesFragment = ActivitiesFragment()
    private var demo2 : DateFragment = DateFragment()
    private var demo3 : GeographicFragment = GeographicFragment()
    private var demo4 : RouteFragment = RouteFragment()
    private var demo5 : WeatherFragment = WeatherFragment()
    private var demo6 : FinishFragment = FinishFragment()

    fun getDemo1(): ActivitiesFragment {
        return demo1
    }

    fun getDemo2(): DateFragment {
        return demo2
    }

    fun getDemo3(): GeographicFragment {
        return demo3
    }

    fun getDemo4(): RouteFragment {
        return demo4
    }

    fun getDemo5(): WeatherFragment {
        return demo5
    }

    fun getDemo6(): FinishFragment {
        return demo6
    }

    override fun getCount(): Int {
        return 6
    }

    override fun getItem(position: Int): Fragment {

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
                        if(position==4)
                        return demo5
                        else
                            return demo6
    }
}