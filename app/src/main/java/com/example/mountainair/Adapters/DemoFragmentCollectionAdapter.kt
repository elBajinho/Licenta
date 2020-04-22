package com.example.mountainair.Adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mountainair.Fragments.ActivitiesFragment
import com.example.mountainair.Fragments.DateFragment
import com.example.mountainair.Fragments.GeographicFragment

class DemoFragmentCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 6
    }

    override fun getItem(position: Int): Fragment {
        var demo1 : ActivitiesFragment = ActivitiesFragment()
        var demo2 : DateFragment = DateFragment()
        var demo3 : GeographicFragment = GeographicFragment()
        var bundle : Bundle =Bundle()
        bundle.putString("message", "hello from page" +position)
        demo1.arguments=bundle

        if(position==0)
            return demo1
        else
            if(position==1)
                return demo2
            else
                return demo3
    }
}